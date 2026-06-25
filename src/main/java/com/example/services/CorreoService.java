package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Estudiante;

public interface CorreoService {

    // Método que persiste los correos
	Correo saveCorreo(Correo correo);
	
	// Método para recuperar todos los telefonos
	List<Correo> getAllCorreos();
	
	// Método para primero preguntar y comprobar si para este Estudiante hay correos
	boolean existsByEstudiante(Estudiante estudiante);
	
	// Método para eliminar todos los correos de un estudiante
	void deleteByEstudiante(Estudiante estudiante);
	
	// Método para encontrar todos los correos de un estudiante
	List<Correo> findByEstudiante(Estudiante estudiante);

}
