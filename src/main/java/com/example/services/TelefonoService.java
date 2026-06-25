package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;

public interface TelefonoService {

    // Método para recuperar todos los telefonos
	List<Telefono> getAllTelefonos();
	
	// Método para persistir un telefono
	Telefono saveTelefono(Telefono telefono);
	
	// Método para ver si el estudiante tiene telefonos
	boolean existsByEstudiante(Estudiante estudiante);
	
	// Método para eliminar todos los telefonos de un estudiante
	void deleteByEstudiante(Estudiante estudiante);
	
	// Método para encontrar todos los telefonos de un estudiante
	List<Telefono> findByEstudiante(Estudiante estudiante);


}
