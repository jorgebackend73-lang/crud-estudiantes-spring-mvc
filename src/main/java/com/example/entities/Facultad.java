package com.example.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "facultad")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "empleados")
@Builder
public class Facultad implements Serializable {

    private static final long serialVersionUID = 1L;

    // con las dos anotaciones de Id y GeneratedValue hacemos
    // el id autoincremental, primary key y no admite nulo.
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;

    /*
    Relacionando tablas. Las relaciones entre las entidades en JPA (Java Persistent API)
    son bidireccionales, a diferencia de las relaciones en SQL que son unidireccionales.
    Por tanto en una relación unidireccional una entidad hija sabe quien es su padre, 
    en dicha entidad se crea la relación de clave externa (Foreing Key), pero la entidad 
    padre no sabe que tiene hijos. En la relación bidireccional el padre sabe de sus hijos
    y los hijos del padre.
    */ 

    /*
    El atributo mappedBy apunta a una propiedad en el lado de muchos de la relación,
    pues aunq las relaciones son bidireccionales hay que especificar donde se va a crear
    la relación de clave externa, que al igual que en SQL es en el lado de muchos.
    */

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "departamento")
    private List<Estudiante> empleados;




}
