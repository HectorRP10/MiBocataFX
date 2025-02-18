package com.example.mibocatafx.controller;

import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.util.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PedidosCocinaControllerPrueba {

    @FXML
    private TableView<Pedido> tabla;
    @FXML
    private TableColumn<Pedido, Integer> tabla_id;
    @FXML
    private TableColumn<Pedido,String> tabla_nombreAlumno;
    @FXML
    private TableColumn<Pedido,String> tabla_bocadillo;
    @FXML
    private TableColumn<Pedido,String> tabla_descuento;
    @FXML
    private TableColumn<Pedido, Double> precio;
    @FXML
    private TableColumn<Pedido,String> fecha;
    @FXML
    private TableColumn<Pedido,String> retirado;
    private ObservableList<Pedido> pedidosList;
    private Bocadillo.Tipo tipoFiltroActual = null;

    private int paginaActual = 1;      // Página actual
    private int totalPedidos = 0;      // Total de pedidos en la base de datos
    private int pedidosPorPagina = 5;  // Número de pedidos por página

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnSiguiente;

    @FXML
    private Button filtrar;

    @FXML
    private ComboBox<Bocadillo.Tipo> tipoBocadillo;

    @FXML
    private TextField txtPagina;
    @FXML
    private Label lblTotalPaginas;

    @FXML
    void anteriorPagina(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            pedidosList.clear();
            cargarDatos();  // Recargar los datos para la página anterior
            txtPagina.setText(String.valueOf(paginaActual));  // Actualizar el campo de página
        }
    }

    @FXML
    void findBuscador(ActionEvent event) {
        tipoFiltroActual = tipoBocadillo.getValue();
        cargarDatos();
    }

    @FXML
    void siguientePagina(ActionEvent event) {
        int totalPaginas = (int) Math.ceil((double) obtenerTotalPedidos() / pedidosPorPagina);

        if (paginaActual < totalPaginas) {
            paginaActual++;
            pedidosList.clear();
            cargarDatos();  // Recargar los datos para la nueva página
            txtPagina.setText(String.valueOf(paginaActual));  // Actualizar el campo de página
        }
    }
    @FXML
    public void initialize() {
        // Configurar las columnas
        tabla_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabla_nombreAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getNombre())
        );
        tabla_bocadillo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBocadillo().getNombre())
        );
        precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tabla_descuento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId_descuento() != null ?
                        cellData.getValue().getId_descuento().getNombre() : "Ninguno")
        );
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        retirado.setCellValueFactory(new PropertyValueFactory<>("retirado"));

        // Configurar la columna 'Retirar' con un botón para cada fila
        TableColumn<Pedido, Void> retirarColumn = new TableColumn<>("Retirar");

        // Definir una celda personalizada para los botones
        retirarColumn.setCellFactory(param -> new TableCell<Pedido, Void>() {
            private final Button btnRetirar = new Button("Retirar");

            {
                // Cuando el botón es presionado
                btnRetirar.setOnAction(event -> {

                    Calendar calendar = Calendar.getInstance();
                    Date hoy = calendar.getTime();

                    // Obtener el pedido de la fila correspondiente
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    pedido.setRetirado(hoy);

                    // Guardar cambio en la base de datos
                    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                        Transaction transaction = session.beginTransaction();
                        session.update(pedido);
                        transaction.commit();
                        //Limpiar la tabla
                        pedidosList.clear();
                        //Recargar tabla
                        cargarDatos();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Implementa la lógica que desees aquí (por ejemplo, cambiar el estado del pedido)
                    System.out.println("Pedido retirado: " + pedido.getId());
                });
            }

            //mostrar boton de retirar
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnRetirar);
                }
            }
        });

        // Agregar la columna 'Retirar' a la tabla
        tabla.getColumns().add(retirarColumn);


        // Inicializar la lista observable
        pedidosList = FXCollections.observableArrayList();
        tabla.setItems(pedidosList);

        txtPagina.setText("1");

        // Cargar los datos
        cargarDatos();
        // Rellenar el ComboBox con los valores del enum Tipo
        cargarTiposDeBocadillos();
    }

    private void cargarDatos() {
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Consulta de pedidos
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date hoy = calendar.getTime();

            // Construir la consulta base
            String consulta = "FROM Pedido p WHERE p.fecha >= :hoy AND p.retirado IS NULL";


            // Si hay un filtro de tipo de bocadillo, agregarlo a la consulta
            if (tipoFiltroActual != null) {
                consulta += " AND p.bocadillo.tipo = :tipo";

                List<Pedido> losPedidos = session.createQuery(consulta  , Pedido.class)
                        .setParameter("hoy", hoy)
                        .setParameter("tipo", tipoFiltroActual)  // Solo si el filtro está aplicado
                        .setFirstResult((paginaActual - 1) * pedidosPorPagina) // Paginación
                        .setMaxResults(pedidosPorPagina) // Paginación
                        .getResultList();

                pedidosList.clear();
                pedidosList.addAll(losPedidos);

            }else {
                List<Pedido> losPedidos = session.createQuery(consulta, Pedido.class)
                        .setParameter("hoy", hoy)
                        .setFirstResult((paginaActual - 1) * pedidosPorPagina) // Paginación
                        .setMaxResults(pedidosPorPagina) // Paginación
                        .getResultList();
                pedidosList.clear();
                pedidosList.addAll(losPedidos);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void cargarTiposDeBocadillos() {
        // Obtener los valores del enum y rellenar el ComboBox
        ObservableList<Bocadillo.Tipo> bocadillosList = FXCollections.observableArrayList(Bocadillo.Tipo.values());
        tipoBocadillo.setItems(bocadillosList);

        // Configurar la forma en que se mostrarán los valores del enum en el ComboBox
        tipoBocadillo.setCellFactory(param -> new javafx.scene.control.ListCell<Bocadillo.Tipo>() {
            @Override
            protected void updateItem(Bocadillo.Tipo item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.name());
                }
            }
        });

        // Configurar el botón de selección para mostrar el nombre del tipo de bocadillo
        tipoBocadillo.setButtonCell(new javafx.scene.control.ListCell<Bocadillo.Tipo>() {
            @Override
            protected void updateItem(Bocadillo.Tipo item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.name());
                }
            }
        });
    }



    public void actualizarTotalPaginas() {
        int totalPaginas = (int) Math.ceil((double) obtenerTotalPedidos() / pedidosPorPagina);
        lblTotalPaginas.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    private int obtenerTotalPedidos() {
        Transaction transaction = null;
        Session session = null;
        int total = 0;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date hoy = calendar.getTime();

            // Consulta para obtener el total de pedidos (sin importar el tipo de bocadillo)
            total = ((Long) session.createQuery("SELECT COUNT(p) FROM Pedido p WHERE p.fecha >= :hoy AND p.retirado IS NULL")
                    .setParameter("hoy", hoy)
                    .uniqueResult()).intValue();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return total;
    }

}
