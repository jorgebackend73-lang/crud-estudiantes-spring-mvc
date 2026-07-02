package com.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.models.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="profesores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Profesor implements Serializable {

    // hay que serializar esta entidad para que extraiga correctamente los datos de la tabla
    private static final long serialVersionUID = 1L;

    // con las dos anotaciones de Id y GeneratedValue hacemos
    // el id autoincremental, primary key y no admite nulo. 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String nombre;

    private String primerApellido;

    private String segundoApellido;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de alta tiene que ser igual o anterior a la fecha actual")
    private LocalDate fechaAlta;

    private BigDecimal salario;

    // relación de muchos a uno. Aunq es bidireccional seguimos teneiendo que distinguir
    // entre mmuchos a uno y uno a muchos:
    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;
    
    private String foto;


}
