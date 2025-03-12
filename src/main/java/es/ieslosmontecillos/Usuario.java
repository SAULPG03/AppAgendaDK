package es.ieslosmontecillos;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;

public class Usuario {

    private final SimpleStringProperty type=new SimpleStringProperty();
    private final SimpleIntegerProperty rpta=new SimpleIntegerProperty();
    private final SimpleStringProperty message=new SimpleStringProperty();
    private final ObjectProperty body = new SimpleObjectProperty();
    //campo id
    @XmlElement(name = "rpta")
    public Integer getRpta() {
        return rpta.get();
    }
    public IntegerProperty rptaProperty(){
        return rpta;
    }
    public void setRpta(Integer rpta) {
        this.rpta.set(rpta);
    }
    //campo clave
    @XmlElement(name = "type")
    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty(){
        return type;
    }
    public void setType(String type) {
        this.type.set(type);
    }
    //campo email
    @XmlElement(name = "message")
    public String getMessage() {
        return message.get();
    }
    public StringProperty messageProperty(){
        return message;
    }
    public void setMesagge(String message) {
        this.message.set(message);
    }
    //campo vigencia
    @XmlElement(name = "body")
    public Login getBody() {
        return (Login) body.get();
    }
    public ObjectProperty bodyProperty() {
        return body;
    }
    public void setBody(Login body) {
        this.body.set(body);
    }
}

