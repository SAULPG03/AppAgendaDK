package es.ieslosmontecillos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PersonaDetalleViewController implements Initializable {
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private RadioButton radioButtonViudo;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private TextField textFieldNumHijos;
    @FXML
    private CheckBox checkBoxJubilado;
    @FXML
    private RadioButton radioButtonSoltero;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private RadioButton radioButtonCasado;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private TextField textFieldApellidos;
    private Pane rootAgendaView;
    private Pane rootPersonaDetalleView;

    private TableView<Persona> tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;

    char estadoCivil;
    public static final char CASADO = 'C';
    public static final char SOLTERO = 'S';
    public static final char VIUDO = 'V';

    public static final String CARPETA_FOTOS = "Fotos";

    boolean errorFormato = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String error = "Algo esta pasando";
    }

    @FXML
    public void onActionButtonCancelar(ActionEvent actionEvent) {
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);
        int numFilaSeleccionada =
                tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio,
                numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();

    }

    @FXML
    public void onActionButtonExaminar(ActionEvent actionEvent) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg",
                        "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
        File file = fileChooser.showOpenDialog(
                rootPersonaDetalleView.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS +
                        "/" + file.getName()).toPath());
                persona.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
        StackPane rootMain =
                (StackPane) rootPersonaDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootPersonaDetalleView);
        rootAgendaView.setVisible(true);
        int numFilaSeleccionada;
        if (nuevaPersona) {
            tableViewPrevio.getItems().add(persona);
            numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada =
                    tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada, persona);
        }
        TablePosition pos = new TablePosition(tableViewPrevio,
                numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    public void setRootAgendaView(Pane rootAgendaView) {
        this.rootAgendaView = rootAgendaView;
    }

    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }

    public void setPersona(Persona persona, Boolean nuevaPersona) {
        if (!nuevaPersona) {
            this.persona = persona;
        } else {
            this.persona = new Persona();
        }
        this.nuevaPersona = nuevaPersona;
    }

    public void setDataUtil(DataUtil dataUtil) {
    }

    public void mostrarDatos() {

        textFieldNombre.setText(persona.getNombre());
        textFieldTelefono.setText(persona.getTelefono());
        //radioButtonViudo.setSelected(true);
        //datePickerFechaNacimiento.set
        if (persona.getNumHijos() != null) {
            textFieldNumHijos.setText(persona.getNumHijos().toString());
        }
        if (persona.getJubilado() != null && persona.getJubilado() == 1) {
            checkBoxJubilado.setSelected(true);
        } else {
            checkBoxJubilado.setSelected(false);
        }
        //radioButtonSoltero
        if (persona.getSalario() != null) {
            textFieldSalario.setText(persona.getSalario().toString());
        }
        //radioButtonCasado
        //imageViewFoto
        textFieldEmail.setText(persona.getEmail());
        //comboBoxProvincia
        textFieldApellidos.setText(persona.getApellidos());

        if (nuevaPersona) {
            dataUtil.addPersona(persona);
        } else {
            dataUtil.actualizarPersona(persona);
        }

        if (persona.getEstadoCivil() != null) {
            switch (persona.getEstadoCivil().charAt(0)) {
                case CASADO:
                    radioButtonCasado.setSelected(true);
                    break;
                case SOLTERO:
                    radioButtonSoltero.setSelected(true);
                    break;
                case VIUDO:
                    radioButtonViudo.setSelected(true);
                    break;
            }
        }

        if (persona.getFechaNacimiento() != null) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fecNac = null;
            try {
                fecNac = formato.parse(persona.getFechaNacimiento());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            LocalDate fechaNac =
                    fecNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePickerFechaNacimiento.setValue(fechaNac);
        }

        comboBoxProvincia.setItems(dataUtil.getOlProvincias());

        if (persona.getProvincia() != null) {
            comboBoxProvincia.setValue(persona.getProvincia());

            comboBoxProvincia.setCellFactory(
                    (ListView<Provincia> l) -> new ListCell<Provincia>() {
                        @Override
                        protected void updateItem(Provincia provincia, boolean empty) {
                            super.updateItem(provincia, empty);
                            if (provincia == null || empty) {
                                setText("");
                            } else {
                                setText(provincia.getCodigo() + "-" + provincia.getNombre());
                            }
                        }
                    });

            comboBoxProvincia.setConverter(new StringConverter<Provincia>() {
                @Override
                public String toString(Provincia provincia) {
                    if (provincia == null) {
                        return null;
                    } else {
                        return provincia.getCodigo() + "-" + provincia.getNombre();
                    }
                }

                @Override
                public Provincia fromString(String userId) {
                    return null;
                }
            });

            if (persona.getFoto() != null) {
                String imageFileName = persona.getFoto();
                File file = new File(CARPETA_FOTOS + "/" + imageFileName);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageViewFoto.setImage(image);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen en " +
                            file.toURI().toString());
                    alert.showAndWait();
                }
            }

            persona.setNombre(textFieldNombre.getText());

            // Recoger datos de pantalla
            if (!errorFormato) { // Los datos introducidos son correctos
                try {
                    // Aquí va el código para guardar el objeto en BD, enviar al servidor
                    // y ocultar la vista actual
                } catch (Exception ex) { //Los datos introducidos no cumplen requisitos
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("No se han podido guardar los cambios. "
                            + "Compruebe que los datos cumplen los requisitos");
                    alert.setContentText(ex.getLocalizedMessage());
                    alert.showAndWait();
                }
            }

            if (!textFieldNumHijos.getText().isEmpty()) {
                try {
                    persona.setNumHijos(Integer.valueOf(textFieldNumHijos.getText()));
                } catch (NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de hijos no válido");
                    alert.showAndWait();
                    textFieldNumHijos.requestFocus();
                }
            }

            if (!textFieldSalario.getText().isEmpty()) {
                try {
                    Double dSalario = Double.valueOf(textFieldSalario.getText());

                    persona.setSalario(dSalario);
                } catch (NumberFormatException ex) {
                    errorFormato = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salario no válido");
                    alert.showAndWait();
                    textFieldSalario.requestFocus();
                }
            }

            if (checkBoxJubilado.isSelected()) {
                Integer jubilado = 1;
                persona.setJubilado(jubilado);
            }
            ;

            if (radioButtonCasado.isSelected()) {
                persona.setEstadoCivil(String.valueOf(CASADO));
            } else if (radioButtonSoltero.isSelected()) {
                persona.setEstadoCivil(String.valueOf(SOLTERO));
            } else if (radioButtonViudo.isSelected()) {
                persona.setEstadoCivil(String.valueOf(VIUDO));
            }

            if (comboBoxProvincia.getValue() != null) {
                persona.setProvincia(comboBoxProvincia.getValue());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una provincia");
                alert.showAndWait();
                errorFormato = true;
            }


        }
    }

    public void onActionSuprimirFoto(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen,\n" + "quitar la foto pero MANTENER el arc" +
                "hivo, \no CANCELAR la operación?");
        alert.setContentText("Elija la opción deseada:");
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener,
                buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar) {
            String imageFileName = persona.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                file.delete();
            }
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
}

