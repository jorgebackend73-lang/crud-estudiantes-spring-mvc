package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.ProfesorDao;
import com.example.entities.Profesor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // se trae de lombok las propiedades final de los objetos mediante constructor <- mira que fallar esto en el teórico
// Para crear, beans objetos administrados por spring y que este los incustre donde haga falta, sin esto lo anterior no funciona.
@Service
public class ProfesorServiceImpl implements ProfesorService {

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

private final ProfesorDao profesorDao;

@Override
public List<Profesor> getAllProfesores() {
    // TODO Auto-generated method stub
    return profesorDao.findAll();
}

@Override
public Profesor getProfesorById(int id) {
    // TODO Auto-generated method stub
    return profesorDao.findById(id).orElseThrow(() 
				-> new RuntimeException("profesor no encontrado con id: " + id));
}

@Override
public Profesor saveProfesor(Profesor profesor) {
    // TODO Auto-generated method stub
    return profesorDao.save(profesor);
}

@Override
public void deleteProfesor(int id) {
    // TODO Auto-generated method stub
    profesorDao.deleteById(id);
}

@Override
public void deleteProfesor(Profesor profesor) {
    // TODO Auto-generated method stub
    profesorDao.delete(profesor);
}

@Override
public Profesor updateProfesor(Profesor profesor) {
    // TODO Auto-generated method stub
    return profesorDao.save(profesor);
}

}
