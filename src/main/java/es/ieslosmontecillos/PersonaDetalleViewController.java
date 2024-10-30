package es.ieslosmontecillos;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PersonaDetalleViewController {
    @javafx.fxml.FXML
    private TextField textFieldNombre;
    @javafx.fxml.FXML
    private TextField textFieldTelefono;
    @javafx.fxml.FXML
    private RadioButton radioButtonViudo;
    @javafx.fxml.FXML
    private DatePicker datePickerFechaNacimiento;
    @javafx.fxml.FXML
    private TextField textFieldNumHijos;
    @javafx.fxml.FXML
    private CheckBox checkBoxJubilado;
    @javafx.fxml.FXML
    private RadioButton radioButtonSoltero;
    @javafx.fxml.FXML
    private TextField textFieldSalario;
    @javafx.fxml.FXML
    private RadioButton radioButtonCasado;
    @javafx.fxml.FXML
    private ImageView imageViewFoto;
    @javafx.fxml.FXML
    private TextField textFieldEmail;
    @javafx.fxml.FXML
    private ComboBox comboBoxProvincia;
    @javafx.fxml.FXML
    private TextField textFieldApellidos;
    private Pane rootAgendaView;
    private Pane rootPersonaDetalleView;


    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void onActionButtonCancelar(ActionEvent actionEvent) {
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);
    }

    @javafx.fxml.FXML
    public void onActionButtonExaminar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);
    }

    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }
    
}
