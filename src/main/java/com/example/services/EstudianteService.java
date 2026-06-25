package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;

public interface EstudianteService {

    List<Estudiante> getAllEstudiantes();

    // Metodo para obtener un estudiante por su id
    Estudiante getEstudianteById(int id);

    // Metodo para persistir (guardar) un estudiante
    Estudiante saveEstudiante(Estudiante estudiante);

    // Metodo para eliminar un estudiante por su id
	void deleteEstudiante(int id);
	
	// Método que elimina un estudiante recibiendo el objeto estudiante
	void deleteEstudiante(Estudiante estudiante);
	
	// Método para actualizar un estudiante
	Estudiante updateEstudiante(Estudiante estudiante);
}


