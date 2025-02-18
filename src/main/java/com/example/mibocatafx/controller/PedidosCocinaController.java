/*package com.example.mibocatafx.controller;

import com.example.mibocatafx.models.Pedido;
import com.example.mibocatafx.service.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosCocinaController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido1;
    @FXML
    private ComboBox cboEspecialidad;

    @FXML
    private TableView tabla;
    @FXML
    private TableColumn tabla_id;
    @FXML
    private TableColumn tabla_nombreAlumno;
    @FXML
    private TableColumn tabla_bocadillo;
    @FXML
    private TableColumn tabla_descuento;
    @FXML
    private TableColumn precio;
    @FXML
    private TableColumn fecha;
    @FXML
    private TableColumn retirado;
    @FXML
    private TableColumn retirar;

    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private TextField txtPagina;
    @FXML
    private Label lblTotal;
    private static final int OFFSET=20;

    private HashMap<String, String> filtros = new HashMap<>();
    private long totalPedidos;


    @FXML
    protected void mostrarPedidos() throws IOException {

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated();
        for(Pedido pedido : pedidos)
        {
            //imprimimos el objeto pivote
            System.out.println(pedido.toString());
        }
    }

    public void rellenaTabla(List<Pedido> pedidos){
        try {
            tabla.setItems(FXCollections.observableArrayList(pedidos));

            tabla_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tabla_nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("id_alumno"));
            tabla_bocadillo.setCellValueFactory(new PropertyValueFactory<Pedido, String>("id_bocadillo"));

            // Opción más sencilla para mostrar el valor de un atributo de tipo objeto
            //tabla_especialidad.setCellValueFactory(data-> new SimpleStringProperty(data.getValue()));

            // Similar a la anterior pero más detallada
            /*tabla_especialidad.setCellValueFactory(cellData -> {
                // Obtener el objeto Medico
                Medico medico = (Medico) cellData.getValue();

                // Si la especialidad no es nula, devolver su nombre, si no, devolver una cadena vacía
                return new SimpleStringProperty(
                        medico != null && medico.getEspecialidad() != null ? medico.getEspecialidad().getNombre() : ""
                );
            });
             */
/*
            // Opción por si no va el getValue() del Object
            tabla_especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                    Pedido pedido = cellData.getValue(); // Obtener el objeto Medico
                    return new SimpleStringProperty(
                            (pedido != null && pedido.getEspecialidad() != null) ? pedido.getEspecialidad().getNombre() : ""
                    );
                }
            });

        } catch (Exception e) {
            // TODO Mettre une popup erreur base de données
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Rellenar el combo de Especialidades
        EspecialidadService especialidadService = new EspecialidadService();
        List<Especialidad> especialidades = especialidadService.getAll();
        for (Especialidad especialidad: especialidades){
            cboEspecialidad.getItems().add(especialidad.getNombre());
        }

        // Rellena la tabla con la primera página sin filtros
        PedidoService medicoService = new PedidoService();
        List<Pedido> medicos = medicoService.getPaginated(1, OFFSET, null);
        rellenaTabla(medicos);
        totalPedidos = medicoService.cout(null);
        lblTotal.setText("Total registros: "+ totalPedidos +" - Total páginas: "+Math.round(Math.ceil((float) totalPedidos /(float)OFFSET)));
    }

    @FXML
    public void findBuscador(){
        filtros.clear();

        // Construcción de un HashMap con los filtros
        if (!txtNombre.getText().isEmpty())
            filtros.put("nombre", txtNombre.getText());
        if (!txtApellido1.getText().isEmpty())
            filtros.put("apellidos", txtApellido1.getText());
        if (cboEspecialidad.getValue() != null)
            filtros.put("especialidad", (String) cboEspecialidad.getValue());


        PedidoService pedidoService = new PedidoService();
        totalPedidos = pedidoService.cout(filtros);
        List<Pedido> medicos = pedidoService.getPaginated(1, OFFSET, filtros);
        rellenaTabla(medicos);

        txtPagina.setText("1");
        btnSiguiente.setDisable(false);
        lblTotal.setText("Total registros: "+ totalPedidos +" - Total páginas: "+Math.round(Math.ceil((float) totalPedidos /(float)OFFSET)));

    }

    @FXML
    public void siguientePagina(){
        int page = Integer.parseInt(txtPagina.getText());
        page++;

        PedidoService medicoService = new PedidoService();
        List<Pedido> medicos = medicoService.getPaginated(page, OFFSET, filtros);
        if (!medicos.isEmpty()) {
            rellenaTabla(medicos);

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
            List<Pedido> pedidos = PedidoService.getPaginated(page, OFFSET, filtros);
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


}*/