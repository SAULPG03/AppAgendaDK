package es.ieslosmontecillos;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;

public class Login {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty clave = new SimpleStringProperty();
    private final BooleanProperty vigencia = new SimpleBooleanProperty();
    //campo id
    @XmlElement(name = "id")
    public Integer getId() {
        return id.get();
    }
    public IntegerProperty idProperty(){
        return id;
    }
    public void setId(Integer id) {
        this.id.set(id);
    }
    //campo clave
    @XmlElement(name = "clave")
    public String getClave() {
        return clave.get();
    }
    public StringProperty claveProperty(){
        return clave;
    }
    public void setClave(String clave) {
        this.clave.set(clave);
    }
    //campo email
    @XmlElement(name = "email")
    public String getEmail() {
        return email.get();
    }
    public StringProperty emailProperty(){
        return email;
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    //campo vigencia
    @XmlElement(name = "vigencia")
    public boolean getVigencia() {
        return vigencia.get();
    }
    public BooleanProperty vigenciaProperty(){
        return vigencia;
    }
    public void setVigencia(Boolean vigencia) {
        this.vigencia.set(vigencia);
    }
}
