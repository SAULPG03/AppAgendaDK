package es.ieslosmontecillos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField textFieldUsuario;
    @FXML
    private TextField textFieldContraseña;

    public void OnActiionGuardar(ActionEvent actionEvent) {
        String usuario = textFieldUsuario.getText();
        String contrasena=textFieldContraseña.getText();

    }
}
