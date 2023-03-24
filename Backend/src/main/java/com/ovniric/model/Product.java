package com.ovniric.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Producto")
public class Product {

    //COLUMNS
    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name = "titulo")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria" )
    private Category category;

    @ManyToOne
    @JoinColumn(name = "localizacion_id", referencedColumnName = "id_localizacion")
    private Location locations;

    @Column(name = "altitud")
    private Integer altitude;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "CaracteristicaProducto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name="id_caracteristica")
    )
    private Set<Feature> features = new HashSet();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Column(name = "descripcion")
    private String description;

    @Column(name = "politica", length = 500)
    private String policy;

    @Column(name = "reservaciones_maximas")
    private Integer maxReservations;




    //CONSTRUCTORS


    public Product(Long idProduct, String title, Location locations, Integer altitude, String description, String policy, Integer maxReservations) {
        this.idProduct = idProduct;
        this.title = title;
        this.locations = locations;
        this.altitude = altitude;
        this.description = description;
        this.policy = policy;
        this.maxReservations = maxReservations;
    }

    public Product(String title, Location locations, Integer altitude, String description, String policy) {
        this.title = title;
        this.locations = locations;
        this.altitude = altitude;
        this.description = description;
        this.policy = policy;
    }

    public Product() {
    }


    //GETTERS AND SETTERS


    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxReservations() {
        return maxReservations;
    }

    public void setMaxReservations(Integer maxReservations) {
        this.maxReservations = maxReservations;
    }


    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocations() {
        return locations;
    }

    public void setLocations(Location locations) {
        this.locations = locations;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }
}
