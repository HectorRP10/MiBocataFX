package com.example.mibocatafx.controller;

import com.example.mibocatafx.models.Bocadillo;
import com.example.mibocatafx.service.BocadilloService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminBocadilloController {

    @FXML
    private TableView<Bocadillo> tablaBocadillos;
    @FXML
    private TableColumn<Bocadillo, Integer> idBocadillo;
    @FXML
    private TableColumn<Bocadillo,String> nombreBocadillo;
    @FXML
    private TableColumn<Bocadillo, Double> precioBocadillo;
    @FXML
    private TableColumn<Bocadillo,String> ingredientesBocadillo;
    @FXML
    private TableColumn<Bocadillo,String> tipoBocadillo;
    @FXML
    private TableColumn<Bocadillo,String> diaBocadillo;
    @FXML
    private TableColumn<Bocadillo,String> fechaBajaBocadillo;

    @FXML
    private TableColumn<Bocadillo, Void> editarBocadillo;
    @FXML
    private TableColumn<Bocadillo, Void> eliminarBocadillo;

    private ObservableList<Bocadillo> bocadillosList;

    private int paginaActual = 1;
    private int bocadillosPorPagina = 5;

    private BocadilloService bocadilloService;
    @FXML
    private TextField txtPagina;
    @FXML
    private Label totalPaginas;

    public AdminBocadilloController() {
        this.bocadilloService = new BocadilloService();
    }

    @FXML
    void initialize() {
        idBocadillo.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreBocadillo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioBocadillo.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ingredientesBocadillo.setCellValueFactory(new PropertyValueFactory<>("ingredientes"));
        tipoBocadillo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        diaBocadillo.setCellValueFactory(cellData -> {
            Bocadillo.DiaSemana dia = cellData.getValue().getDiaSemana();
            return new SimpleStringProperty(dia != null ? dia.name() : ""); // Evita NullPointerException
        });
        fechaBajaBocadillo.setCellValueFactory(new PropertyValueFactory<>("fecha_baja"));

        editarBocadillo.setCellFactory(param -> {
            TableCell<Bocadillo, Void> cell = new TableCell<Bocadillo, Void>() {
                private final Button btnEditar = new Button("Editar");

                {
                    btnEditar.setOnAction(event -> {
                        Bocadillo bocadillo = getTableRow().getItem();
                        if (bocadillo != null) {
                            modificarBocadillo(bocadillo);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnEditar);
                    }
                }
            };
            return cell;
        });

        eliminarBocadillo.setCellFactory(param -> {
            TableCell<Bocadillo, Void> cell = new TableCell<Bocadillo, Void>() {
                private final Button btnEliminar = new Button("Eliminar");

                {
                    btnEliminar.setOnAction(event -> {
                        Bocadillo bocadillo = getTableRow().getItem();
                        if (bocadillo != null) {
                            confirmarEliminarBocadillo(bocadillo);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnEliminar);
                    }
                }
            };
            return cell;
        });
        cargarDatos();
    }

    private void cargarDatos() {
        bocadillosList = FXCollections.observableArrayList(bocadilloService.getPaginated(paginaActual, bocadillosPorPagina));
        tablaBocadillos.setItems(bocadillosList);
        actualizarTotalPaginas();
        txtPagina.setText(String.valueOf(paginaActual));
    }
    private void modificarBocadillo(Bocadillo bocadillo){
        bocadilloService.modificar(bocadillo);
        cargarDatos();
    }

    private void eliminarBocadillo(Bocadillo bocadillo){
        bocadilloService.eliminar(bocadillo);
        if (bocadillosList.size() == 1 && paginaActual > 1) {
            paginaActual--;
        }
        cargarDatos();
        actualizarTotalPaginas();
    }

    private void confirmarEliminarBocadillo(Bocadillo bocadillo) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Estás seguro de que quieres eliminar este bocadillo?");
        alerta.setContentText("Esta acción no se puede deshacer.");

        // Mostrar alerta y esperar respuesta del usuario
        alerta.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                eliminarBocadillo(bocadillo);
            }
        });
    }
    private void actualizarTotalPaginas() {
        int totalPedidos = bocadilloService.obtenerTotalBocadillos();

        int totalPaginas = (int) Math.ceil((double) totalPedidos / bocadillosPorPagina);
        this.totalPaginas.setText("Página " + paginaActual + " de " + totalPaginas);
    }

    @FXML
    private void paginaAnterior() {
        if (paginaActual > 1) {
            paginaActual--;
            cargarDatos();
            actualizarTotalPaginas();
        }
    }

    @FXML
    private void paginaSiguiente() {
        int totalBocadillos = bocadilloService.obtenerTotalBocadillos();
        int totalPaginas = (int) Math.ceil((double) totalBocadillos / bocadillosPorPagina);

        if (paginaActual < totalPaginas) {
            paginaActual++;
            cargarDatos();
            if (bocadillosList.isEmpty()) {
                paginaActual--;
                cargarDatos();
            }
            actualizarTotalPaginas();
        }
    }

    @FXML
    private void crearNuevoBocadillo() throws IOException {
        cargarVista("/com/example/mibocatafx/fxml/CrearBocadilloForm.fxml", "RIGHT");
    }
    private void cargarVista(String rutaFXML, String posicion) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource(rutaFXML));

        if ("RIGHT".equals(posicion)) {
            BorderPane borderPane = (BorderPane) tablaBocadillos.getScene().getRoot();
            borderPane.setRight(view);
        }
    }

}
