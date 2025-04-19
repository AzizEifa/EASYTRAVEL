package Entities;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Trajet {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty depart = new SimpleStringProperty();
    private final StringProperty destination = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDateTime> dateDepart = new SimpleObjectProperty<>();
    private final DoubleProperty prix = new SimpleDoubleProperty();

    // Constructors
    public Trajet() {}

    public Trajet(String depart, String destination, double distance, LocalDateTime dateDepart, double prix) {
        setDepart(depart);
        setDestination(destination);
        setDistance(distance);
        setDateDepart(dateDepart);
        setPrix(prix);
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty departProperty() { return depart; }
    public StringProperty destinationProperty() { return destination; }
    public DoubleProperty distanceProperty() { return distance; }
    public ObjectProperty<LocalDateTime> dateDepartProperty() { return dateDepart; }
    public DoubleProperty prixProperty() { return prix; }

    // Standard getters/setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public String getDepart() { return depart.get(); }
    public void setDepart(String depart) { this.depart.set(depart); }
    public String getDestination() { return destination.get(); }
    public void setDestination(String destination) { this.destination.set(destination); }
    public double getDistance() { return distance.get(); }
    public void setDistance(double distance) { this.distance.set(distance); }
    public LocalDateTime getDateDepart() { return dateDepart.get(); }
    public void setDateDepart(LocalDateTime dateDepart) { this.dateDepart.set(dateDepart); }
    public double getPrix() { return prix.get(); }
    public void setPrix(double prix) { this.prix.set(prix); }
}