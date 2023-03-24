package com.ovniric.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Caracteristica")
public class Feature {

    //COLUMNS
    @Id
    @Column(name = "id_caracteristica")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeature;

    @Column(name = "titulo")
    private String title;

    @ManyToMany(mappedBy = "features")
    @JsonIgnore
    private Set<Product> products = new HashSet();


    //CONSTRUCTORS


    public Feature(Long idFeature, String title) {
        this.idFeature = idFeature;
        this.title = title;
    }

    public Feature(String title) {
        this.title = title;

    }

    public Feature() {
    }


    //GETTERS AND SETTERS


    public Long getIdFeature() {
        return idFeature;
    }

    public void setIdFeature(Long idFeature) {
        this.idFeature = idFeature;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
