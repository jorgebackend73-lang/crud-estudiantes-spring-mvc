package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.models.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "estudiante")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"telefonos", "emails"})
@Builder
public class Estudiante implements Serializable {

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
        
    // para que no guarde ordinal y guarde el nombre hay que anotar:
    @Enumerated(EnumType.STRING)
    private Genero genero;

    // formato canónico de fecha (mm serían minutos en vez de meses)
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private LocalDate fechaAlta;



    // relación de muchos a uno. Aunq es bidireccional seguimos teneiendo que distinguir
    // entre mmuchos a uno y uno a muchos:
    @ManyToOne(fetch = FetchType.LAZY)
    private Facultad facultad;

    // para que se pasen telefonos y correos cuando creemos o grabemos un empleado
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private Set<Telefono> telefonos = new HashSet<>();
    // no debería ser necesario, pero resulta mejor inicializar estas colecciones de teléfonos y correos
    // así evitamos también null pointer exception.

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estudiante")
    @Builder.Default
    private Set<Correo> emails = new HashSet<>();



}
