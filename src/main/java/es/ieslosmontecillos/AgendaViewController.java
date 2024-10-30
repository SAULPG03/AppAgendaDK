package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AgendaViewController implements Initializable {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProvincias;
    private ObservableList<Persona> olPersonas;
    @FXML
    private TableView<Persona> tableViewAgenda;
    @FXML
    private TableColumn<Persona, String> columnNombre;
    @FXML
    private TableColumn<Persona, String> columnApellidos;
    @FXML
    private TableColumn<Persona, String> columnEmail;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidos;

    private Persona personaSeleccionada;


    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setOlProvincias(ObservableList<Provincia> olProvincias) {
        this.olProvincias = olProvincias;
    }

    public void setOlPersonas(ObservableList<Persona> olPersonas) {
        this.olPersonas = olPersonas;
    }

    @FXML
    private TableColumn<Persona, String> columnProvincia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new
                PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new
                PropertyValueFactory<>("email"));
        columnProvincia.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getProvincia() != null) {
                        property.setValue(cellData.getValue().getProvincia().getNombre());
                    }
                    return property;
                });
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    personaSeleccionada = newValue;
                    if (personaSeleccionada != null) {
                        textFieldNombre.setText(personaSeleccionada.getNombre());
                        textFieldApellidos.setText(personaSeleccionada.getApellidos());
                    } else {
                        textFieldNombre.setText("");
                        textFieldApellidos.setText("");
                    }
                });

    }

    public void cargarTodasPersonas() {

        tableViewAgenda.setItems(FXCollections.observableArrayList(olPersonas));
    }


    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
        if (personaSeleccionada != null) {
            personaSeleccionada.setNombre(textFieldNombre.getText());
            personaSeleccionada.setApellidos(textFieldApellidos.getText());
            dataUtil.actualizarPersona(personaSeleccionada);
            int numFilaSeleccionada =
                    tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada, personaSeleccionada);
            TablePosition pos = new
                    TablePosition(tableViewAgenda, numFilaSeleccionada, null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }


    }
    private Pane rootAgendaView;
    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event){
        try{
// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
            PersonaDetalleViewController personaDetalleViewController =
                    (PersonaDetalleViewController) fxmlLoader.getController();
            personaDetalleViewController.setRootAgendaView(rootAgendaView);
            Parent rootDetalleView=fxmlLoader.load();
// Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            //Añadir la vista detalle al StackPane principal para que se muestre
            StackPane rootMain =
                    (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
        } catch (IOException ex){
            System.out.println("Error volcado"+ex);}
    }





    public void onActionButtonEditar(ActionEvent actionEvent) {
        try{
// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
            PersonaDetalleViewController personaDetalleViewController =
                    (PersonaDetalleViewController) fxmlLoader.getController();
            personaDetalleViewController.setRootAgendaView(rootAgendaView);
            Parent rootDetalleView=fxmlLoader.load();
// Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            //Añadir la vista detalle al StackPane principal para que se muestre
            StackPane rootMain =
                    (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
        } catch (IOException ex){
            System.out.println("Error volcado"+ex);}


    }

    public void onActionButtonSuprimir(ActionEvent actionEvent) {
    }

}

