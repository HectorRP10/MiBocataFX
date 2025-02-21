package com.example.mibocatafx.controller;

import com.example.mibocatafx.MainApplication;
import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.service.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PedidosCocinaController {

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
    private TableColumn<Pedido, Void> tabla_retirar;
    private ObservableList<Pedido> pedidosList;
    private Bocadillo.Tipo tipoFiltroActual = null;
    @FXML
    private HBox bocadilloContainerFrios;
    @FXML
    private HBox bocadilloContainerCalientes;
    private int paginaActual = 1;
    private int pedidosPorPagina = 5;

    private PedidoService pedidoService;

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
    private Label lblTotalPedidos;

    public PedidosCocinaController() {
        this.pedidoService = new PedidoService();
    }

    @FXML
    void initialize() {
        // Configurar las columnas
        tabla_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabla_nombreAlumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlumno().getNombre()));
        tabla_bocadillo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBocadillo().getNombre()));
        precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tabla_descuento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_descuento() != null ?
                cellData.getValue().getId_descuento().getNombre() : "Ninguno"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tabla_retirar.setCellFactory(param -> {
            TableCell<Pedido, Void> cell = new TableCell<Pedido, Void>() {
                private final Button btnRetirar = new Button("Retirar");

                {
                    btnRetirar.setOnAction(event -> retirarPedido(getTableRow().getItem()));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnRetirar);
                    }
                }
            };
            return cell;
        });

        // Inicializar la lista observable
        pedidosList = FXCollections.observableArrayList();
        tabla.setItems(pedidosList);

        // Configurar el ComboBox
        cargarTiposDeBocadillos();

        // Cargar los datos
        cargarDatos();
        mostrarPedidosFrios();
        mostrarPedidosCalientes();
    }

    @FXML
    void anteriorPagina(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            cargarDatos();
        }
    }

    @FXML
    void siguientePagina(ActionEvent event) {
        int totalPaginas = (int) Math.ceil((double) pedidoService.obtenerTotalPedidos(obtenerFechaHoy(), tipoFiltroActual) / pedidosPorPagina);

        if (paginaActual < totalPaginas) {
            paginaActual++;
            cargarDatos();
        }
    }

    @FXML
    void findBuscador(ActionEvent event) {
        tipoFiltroActual = tipoBocadillo.getValue();
        cargarDatos();
    }

    private void cargarDatos() {
        Date fechaHoy = obtenerFechaHoy();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorFecha(fechaHoy, paginaActual, pedidosPorPagina, tipoFiltroActual);
        pedidosList.clear();
        pedidosList.addAll(pedidos);
        actualizarTotalPaginas();

        txtPagina.setText(String.valueOf(paginaActual));
    }

    private Date obtenerFechaHoy() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void cargarTiposDeBocadillos() {
        tipoBocadillo.setItems(FXCollections.observableArrayList(Bocadillo.Tipo.values()));
    }

    private void actualizarTotalPaginas() {
        int totalPedidos = pedidoService.obtenerTotalPedidos(obtenerFechaHoy(), tipoFiltroActual);

        int totalPaginas = (int) Math.ceil((double) totalPedidos / pedidosPorPagina);
        lblTotalPaginas.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    private void retirarPedido(Pedido pedido) {
        if (pedido != null) {

            pedido.setRetirado(obtenerFechaHoy());

            // Llamar al servicio para actualizar el pedido
            pedidoService.actualizarPedido(pedido);

            // Recargar los datos de la tabla después de actualizar
            cargarDatos();
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event)throws IOException {
        UsuarioSesion.cerrarSesion();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        // Cerrar la ventana de login
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void mostrarPedidosFrios() {
        // Obtener el total de pedidos calientes de hoy
        long totalPedidosFrios = pedidoService.obtenerPedidosFriosDeHoy();

        // Limpiar el contenedor antes de agregar el nuevo elemento
        bocadilloContainerFrios.getChildren().clear();

        // Crear una etiqueta con el total de pedidos calientes
        Label labelTotalPedidos = new Label("Total pedidos frios: " + totalPedidosFrios);
        labelTotalPedidos.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; ");

        // Crear un HBox para encerrar la etiqueta y darle fondo
        HBox contenedorPedidosFrios = new HBox(labelTotalPedidos);
        contenedorPedidosFrios.setStyle("-fx-background-color: #89E9A8; " + "-fx-padding: 15px; " + "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-alignment: center;");


                // Agregar el contenedor al VBox principal
        bocadilloContainerFrios.getChildren().add(contenedorPedidosFrios);
    }


    public void mostrarPedidosCalientes() {
        // Obtener el total de pedidos calientes de hoy
        long totalPedidosCalientes = pedidoService.obtenerPedidosCalientesDeHoy();

        // Limpiar el contenedor antes de agregar el nuevo elemento
        bocadilloContainerCalientes.getChildren().clear();

        //etiqueta con el total de pedidos calientes
        Label labelTotalPedidos = new Label("Total pedidos calientes: " + totalPedidosCalientes);
        labelTotalPedidos.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // HBox para encerrar la etiqueta y darle fondo
        HBox contenedorPedidos = new HBox(labelTotalPedidos);
        contenedorPedidos.setStyle("-fx-background-color: #F25F5F; " + "-fx-padding: 15px; " + "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-alignment: center;"
        );

        // Agregar el contenedor al VBox principal
        bocadilloContainerCalientes.getChildren().add(contenedorPedidos);
    }

}
