package com.example.mibocatafx.controller;

import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.service.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

    @FXML
    private Label labelHistorial;
    @FXML
    private TextField txtNombre;
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

    @FXML
    protected void mostrarPedidos() throws IOException {

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, 50, null);
        for(Pedido pedido : pedidos)
        {
            //imprimimos el objeto pivote
            System.out.println(pedido.toString());
        }
    }




    public void rellenaTabla(List<Pedido> pedidos){
        try {
            tabla.setItems(FXCollections.observableArrayList(pedidos));

            tabla_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            tabla_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tabla_precio.setCellValueFactory(new PropertyValueFactory<Pedido, String>("precio"));


            /*
            // Opción por si no va el getValue() del Object
            tabla_especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medico, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Medico, String> cellData) {
                    Medico medico = cellData.getValue(); // Obtener el objeto Medico
                    return new SimpleStringProperty(
                            (medico != null && medico.getEspecialidad() != null) ? medico.getEspecialidad().getNombre() : ""
                    );
                }
            });

            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Rellena la tabla con la primera página sin filtros
        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, OFFSET, null);
        rellenaTabla(pedidos);
        totalPedidos = pedidoService.cout(null);
        lblTotal.setText("Total registros: "+totalPedidos+" - Total páginas: "+Math.round(Math.ceil((float)totalPedidos/(float)OFFSET)));



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
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, filtros);
        if (!pedidos.isEmpty()) {
            rellenaTabla(pedidos);

            txtPagina.setText(page + "");
            btnAnterior.setDisable(false);
        } else {
            btnSiguiente.setDisable(true);
        }
    }

    @FXML
    public void anteriorPagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page--;

        if (page>0) {
            PedidoService pedidoService = new PedidoService();
            List<Pedido> pedidos = pedidoService.getPaginated(page, OFFSET, filtros);
            if (!pedidos.isEmpty()) {
                rellenaTabla(pedidos);
                txtPagina.setText(page + "");
                btnSiguiente.setDisable(false);
            } else {
                btnAnterior.setDisable(true);
            }
        } else {
            btnAnterior.setDisable(true);
        }
    }


}
