package Entities;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Livraison {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty depart = new SimpleStringProperty();
    private final StringProperty destination = new SimpleStringProperty();
    private final ObjectProperty<Timestamp> dateLivraison = new SimpleObjectProperty<>();
    private final DoubleProperty prix = new SimpleDoubleProperty();

    // Constructors
    public Livraison() {}

    public Livraison(String depart, String destination, Timestamp dateLivraison, double prix) {
        setDepart(depart);
        setDestination(destination);
        setDateLivraison(dateLivraison);
        setPrix(prix);
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty departProperty() { return depart; }
    public StringProperty destinationProperty() { return destination; }
    public ObjectProperty<Timestamp> dateLivraisonProperty() { return dateLivraison; }
    public DoubleProperty prixProperty() { return prix; }

    // Standard getters/setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public String getDepart() { return depart.get(); }
    public void setDepart(String depart) { this.depart.set(depart); }
    public String getDestination() { return destination.get(); }
    public void setDestination(String destination) { this.destination.set(destination); }
    public Timestamp getDateLivraison() { return dateLivraison.get(); }
    public void setDateLivraison(Timestamp dateLivraison) { this.dateLivraison.set(dateLivraison); }
    public double getPrix() { return prix.get(); }
    public void setPrix(double prix) { this.prix.set(prix); }
}
