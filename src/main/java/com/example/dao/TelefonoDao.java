package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;

public interface TelefonoDao extends JpaRepository<Telefono, Integer> {

    // Método para ver si el estudiante tiene telefonos
	boolean existsByEstudiante(Estudiante estudiante);
	
	// Método para eliminar telefonos
	void deleteByEstudiante(Estudiante estudiante);
	
	// Metodo para encontrar todos los telefonos del estudiante
	List<Telefono> findByEstudiante(Estudiante estudiante);
	// casi lo genera solo eclipse solo recordar exists, delete, find By estudiante

}
