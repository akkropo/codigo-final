package com.ecomarket.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "precios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Precio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private Double precioBase;
    private Double precioPromocion;
    private String descripcionPromocion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
