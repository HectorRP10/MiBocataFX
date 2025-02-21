package com.example.mibocatafx.controller;

import com.example.mibocatafx.UsuarioSesion;
import com.example.mibocatafx.dao.PedidoDao;
import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.models.Usuario;
import com.example.mibocatafx.service.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class HistorialController implements Initializable {
    private int paginaActual = 1;
    private int pedidosPorPagina = 8;
    @FXML
    private Label labelHistorial;
    @FXML
    private TextField txtNombre;
    @FXML
    private TableColumn<Pedido, String> columnaFecha;
    @FXML
    private TableColumn<Pedido, String> columnaNombre;
    @FXML
    private TableColumn<Pedido, Double> columnaPrecio;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn tabla_fecha;
    @FXML
    private TableColumn tabla_nombre;
    @FXML
    private TableColumn tabla_precio;
    @FXML
    private ComboBox cboTipo;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private TextField txtPagina;
    private static final int OFFSET=20;

    private HashMap<String, String> filtros = new HashMap<>();
    private long totalPedidos;
    private PedidoService pedidoService;


    public HistorialController() {
        this.pedidoService = new PedidoService();
    }


    public void rellenaTabla(List<Pedido> pedidos) {
        try {
            // Llenar la tabla con la lista de pedidos
            tabla.setItems(FXCollections.observableArrayList(pedidos));

            // Asignar las columnas con los métodos correspondientes de la clase Pedido
            columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            columnaPrecio.setCellValueFactory(new PropertyValueFactory<Pedido, Double>("precio"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Rellena la tabla con la primera página sin filtros
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        //columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Configurar la columna de nombre con un Callback para obtener el nombre del bocadillo
        columnaNombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                return new SimpleStringProperty(param.getValue().getBocadillo().getNombre());
            }
        });



        cargarPedidos();


    }
    private void cargarPedidos() {
        // Obtener los pedidos del alumno logueado
        List<Pedido> pedidos = pedidoService.getPaginatedHistorial(paginaActual, pedidosPorPagina);
        totalPedidos = pedidoService.obtenerTotalPedidosAlumno();  // Obtener el total de pedidos del alumno

        // Rellenar la tabla con los pedidos
        rellenaTabla(pedidos);

        // Actualizar la interfaz de usuario
        lblTotal.setText("Total registros: " + totalPedidos + " - Total páginas: " +
                Math.round(Math.ceil((float) totalPedidos / (float) pedidosPorPagina)));

        // Actualizar el número de la página en el campo de texto
        txtPagina.setText(String.valueOf(paginaActual));

        // Deshabilitar o habilitar los botones de paginación
        btnSiguiente.setDisable(paginaActual >= (int) Math.ceil((float) totalPedidos / pedidosPorPagina));
        btnAnterior.setDisable(paginaActual <= 1);
    }


    @FXML
    public void findBuscador(){
        filtros.clear();

        // Construcción de un HashMap con los filtros
        if (!txtNombre.getText().isEmpty())
            filtros.put("nombre", txtNombre.getText());
        if (!txtPrecio.getText().isEmpty())
            filtros.put("precio", txtPrecio.getText());
        if (cboTipo.getValue() != null)
            filtros.put("tipo", (String) cboTipo.getValue());


        PedidoService pedidoService = new PedidoService();
        totalPedidos = pedidoService.cout(filtros);
        List<Pedido> pedidos = pedidoService.getPaginated(1, OFFSET, filtros);
        rellenaTabla(pedidos);

        txtPagina.setText("1");
        btnSiguiente.setDisable(false);
        lblTotal.setText("Total registros: "+totalPedidos+" - Total páginas: "+Math.round(Math.ceil((float)totalPedidos/(float)OFFSET)));

    }


    @FXML
    void anteriorPagina(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            cargarPedidos();
        }
    }

    @FXML
    void siguientePagina(ActionEvent event) {
        int totalPaginas = (int) Math.ceil((double) totalPedidos / pedidosPorPagina);
        if (paginaActual < totalPaginas) {
            paginaActual++;
            cargarPedidos();
        }
    }


}
