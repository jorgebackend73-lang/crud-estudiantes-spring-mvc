package com.example.services;

import java.util.List;

import com.example.entities.Profesor;

public interface ProfesorService {

    List<Profesor> getAllProfesores();

    // Metodo para obtener un profesor por su id
    Profesor getProfesorById(int id);

    // Metodo para persistir (guardar) un profesor
    Profesor saveProfesor(Profesor profesor);

    // Metodo para eliminar un profesor por su id
	void deleteProfesor(int id);
	
	// Método que elimina un profesor recibiendo el objeto profesor
	void deleteProfesor(Profesor profesor);
	
	// Método para actualizar un profesor
	Profesor updateProfesor(Profesor profesor);

}
