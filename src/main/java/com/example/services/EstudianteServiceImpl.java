package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.EstudianteDao;
import com.example.entities.Estudiante;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // se trae de lombok las propiedades final de los objetos mediante constructor
@Service // crear beans objetos administrados por spring y este los incustre donde haga falta sin esto 
		 // lo anterior no funciona
public class EstudianteServiceImpl implements EstudianteService {

/* 
* Esta clase necesita del DAO para implementar todos sus metodos.
* Anteriormente la inyeccion de dependencias tenia lugar a traves
* de la anotacion @Autowire de Spring, pero desde un tiempo
* se ha llegado a la conclusion que la inyeccion de dependencia 
* por constructor es mas eficiente.
* 
* Y si utilizamos el lombok, para que se inyecte una dependencia por constructor
* solamente hay que agregarle el modificador final
* 
* */
	
	private final EstudianteDao estudianteDao;

	@Override
	public List<Estudiante> getAllEstudiantes() {
		// TODO Auto-generated method stub
		return estudianteDao.findAll(); 
		// ya no es null gracias a spring e hibernate nos ahorramos mucho code
	}

	@Override
	public Estudiante getEstudianteById(int id) {
		// TODO Auto-generated method stub
		return estudianteDao.findById(id).orElseThrow(() 
				-> new RuntimeException("Estudiante no encontrado con id: " + id));
		// si hay un error nos saldrá ese mensaje. Todo a traves de una lambda.
	}

	@Override
	public Estudiante saveEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteDao.save(estudiante);
		
	}

	@Override
	public void deleteEstudiante(int id) {
		// TODO Auto-generated method stub
		estudianteDao.deleteById(id);
		
	}

	@Override
	public void deleteEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		estudianteDao.delete(estudiante);
		
	}

	@Override
	public Estudiante updateEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteDao.save(estudiante);
	}

   



}
