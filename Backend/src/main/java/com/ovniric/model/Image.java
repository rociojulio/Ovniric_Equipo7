package com.ovniric.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Imagen")
public class Image {

    //COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImage;

    @Column(name = "url_imagen", unique = true)
    private String imageUrl;

    @Column(name = "titulo_imagen")
    private String imageTitle;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id_producto")
    @JsonIgnore
    private Product product;

    //CONSTRUCTORS

}
