package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import es.ieslosmontecillos.DataUtil;
import es.ieslosmontecillos.Persona;
import es.ieslosmontecillos.PersonaDetalleViewController;
import es.ieslosmontecillos.Provincia;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AgendaViewController implements Initializable {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProvincias;
    private ObservableList<Persona> olPersonas;
    private ObservableList<Login> olLogins;


    @FXML
    private TextField textFieldEmail;
    @FXML
    private AnchorPane rootAgendaView;
    @FXML
    private TableView tableViewAgenda;
    @FXML
    private TableColumn<Login, Integer> columnId;
    @FXML
    private TableColumn<Login, String> columnEmail;
    @FXML
    private TableColumn<Login,String> columnPassword;
    @FXML
    private TableColumn<Login,String> columnVigencia;
    @FXML
    private TextField textFieldPassword;

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setOlProvincias(ObservableList<Provincia> olProvincias) {
        this.olProvincias = olProvincias;
    }

    public void setOlPersonas(ObservableList<Persona> olPersonas) {
        this.olPersonas = olPersonas;
    }
    public void setOlLogin(ObservableList<Login> olLogins) {
        this.olLogins = olLogins;
    }
    private String textFieldEmailantiguo;
    private String textFieldPassantiguo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("clave"));

        columnVigencia.setCellValueFactory(new PropertyValueFactory<>("vigencia"));

        columnVigencia.setCellValueFactory(cellData-> {
                    if (cellData.getValue() != null) {
                        SimpleStringProperty string=new SimpleStringProperty();
                        if(cellData.getValue().getVigencia()==true){
                            string.setValue("SI");
                        } else if (cellData.getValue().getVigencia()==false) {
                            string.setValue("NO");

                        }
                    return string;
                    }
            return null;
        });

        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                textFieldEmail.setText(((Login)t1).getEmail());
                textFieldEmailantiguo = ((Login)t1).getEmail();
                textFieldPassword.setText(((Login)t1).getClave());
                textFieldPassantiguo = ((Login)t1).getClave();
            }
        });



    }

    @FXML
    public void onActionButtonSuprimir(ActionEvent actionEvent) {

        dataUtil.eliminarPersona((Persona)tableViewAgenda.getSelectionModel().getSelectedItem());
        olPersonas.remove((Persona)tableViewAgenda.getSelectionModel().getSelectedItem());



    }

    @FXML
    public void onActionButtonEditar(ActionEvent actionEvent) {
        System.out.println("Editar pulsado");

        try{

// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PersonaDetalleView.fxml"));

            Parent rootDetalleView=fxmlLoader.load();
            PersonaDetalleViewController personaDetalleViewController=fxmlLoader.getController();
            personaDetalleViewController.setDataUtil(dataUtil);
            personaDetalleViewController.setRootAgendaView(rootAgendaView);
            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);
            //personaDetalleViewController.setPersona((Persona)tableViewAgenda.getSelectionModel().getSelectedItem());

            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
        } catch (IOException ex){
            ex.printStackTrace();}

    }



    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
        Login actualizar = null;
        ObservableList<Login> logins = tableViewAgenda.getSelectionModel().getSelectedItems();
        for (Login login : logins) {
            System.out.println(login.getEmail());
            if (login.getEmail().equals(textFieldEmailantiguo)&&login.getClave().equals(textFieldPassantiguo)) {
                actualizar = login;
            }
        }
        actualizar.setClave(textFieldPassword.getText());
        actualizar.setEmail(textFieldEmail.getText());

        dataUtil.actualizarUsuario(actualizar);
    }

    @FXML
    public void onActionButtonNuevo(ActionEvent actionEvent) {


        try{


// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PersonaDetalleView.fxml"));

            Parent rootDetalleView=fxmlLoader.load();
            PersonaDetalleViewController personaDetalleViewController=fxmlLoader.getController();
            personaDetalleViewController.setDataUtil(dataUtil);
            personaDetalleViewController.setRootAgendaView(rootAgendaView);

            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);

            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

        } catch (IOException ex){
            ex.printStackTrace();}


    }

    public void cargarTodasPersonas() {

        tableViewAgenda.setItems(olLogins);


    }

    public void onActionButtonSalir(ActionEvent actionEvent) {
        InicioView inicioView = new InicioView();
        InicioController inicioController;
        View inicio = inicioView.getView();
        inicioController = inicioView.getInicioController();
        inicioController.setRootMain(inicioController.getRootMain());

    }
}