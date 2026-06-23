package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Estudiante;

public interface TelefonoDao extends JpaRepository<TelefonoDao, Integer> {

    // Método para ver si el empleado tiene telefonos
	boolean existsByEstudiante(Estudiante estudiante);
	
	// Método para eliminar telefonos
	void deleteByEstudiante(Estudiante estudiante);
	
	// Metodo para encontrar todos los telefonos del empleado
	List<TelefonoDao> findByEstudiante(Estudiante estudiante);
	// casi lo genera solo eclipse solo recordar exists, delete, find By empleado

}
