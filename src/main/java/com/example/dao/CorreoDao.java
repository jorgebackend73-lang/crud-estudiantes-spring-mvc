package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Correo;
import com.example.entities.Estudiante;

public interface CorreoDao extends JpaRepository<Correo, Integer> {

    // Método para construir correo
	
	// Método para primero preguntar y comprobar si para este empleado hay correos
	boolean existsByEstudiante(Estudiante estudiante);
	
	// Método para eliminar todos los correos de un empleado
	void deleteByEstudiante(Estudiante estudiante);
	
	// Método para encontrar todos los correos de un empleado
	List<Correo> findByEstudiante(Estudiante estudiante);

}
