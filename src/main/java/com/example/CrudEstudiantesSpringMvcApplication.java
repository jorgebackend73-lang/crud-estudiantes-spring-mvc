package com.example;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.models.Genero;
import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CrudEstudiantesSpringMvcApplication implements CommandLineRunner {

	private final EstudianteService estudianteService;
	private final FacultadService facultadService;
	private final CorreoService correoService;
	private final TelefonoService telefonoService;

	public static void main(String[] args) {
		SpringApplication.run(CrudEstudiantesSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// crear registros de ejemplo para la base de datos.
		// Así comprobamos si la aplicación y su capa de servicios funciona correctamente
		// y también si funciona la capa de persistencia.
		
		/*
		 * Crear Facultades hay que crear método
		 * */
		
		Facultad facultad1 = Facultad.builder()
				.nombre("Matematicas")
				.build();
		
		Facultad facultad2 = Facultad.builder()
				.nombre("Informatica")
				.build();
		
		Facultad facultad3 = Facultad.builder()
				.nombre("Diseño")
				.build();
		
		Facultad facultad4 = Facultad.builder()
				.nombre("Filosofia")
				.build();
		
		Facultad facultad5 = Facultad.builder()
				.nombre("Literatura")
				.build();
		
		// Persistir los facultads en la base de datos
		facultadService.saveFacultad(facultad1);
		facultadService.saveFacultad(facultad2);
		facultadService.saveFacultad(facultad3);
		facultadService.saveFacultad(facultad4);
		facultadService.saveFacultad(facultad5);
		
		// Crear Estudiantes
		Estudiante estudiante1 = Estudiante.builder()
				.nombre("Juan")
				.primerApellido("Perez")
				.segundoApellido("Garcia")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2026, 1, 15))
				.facultad(facultad1)
				
				.telefonos(Set.of(Telefono.builder().numero("456784329").build(),
						Telefono.builder().numero("678954453").build()))
				.emails(Set.of(Correo.builder().email("mp@g.com").build(),
						Correo.builder().email("emp2@g.com").build()))
				.build();
		
		Estudiante estudiante2 = Estudiante.builder()
				.nombre("Juani")
				.primerApellido("Pereza")
				.segundoApellido("Garcias")
				.genero(Genero.MUJER)
				.fechaAlta(LocalDate.of(2021, 3, 17))
				.facultad(facultad3)
				
				.telefonos(Set.of(Telefono.builder().numero("756784329").build(),
						Telefono.builder().numero("986954453").build()))
				.emails(Set.of(Correo.builder().email("mpz@g.com").build(),
						Correo.builder().email("emp25@g.com").build()))
				.build();
		
		Estudiante estudiante3 = Estudiante.builder()
				.nombre("Huan")
				.primerApellido("Ali")
				.segundoApellido("Soto")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2020, 8, 11))
				.facultad(facultad2)
				
				.telefonos(Set.of(Telefono.builder().numero("456563629").build(),
						Telefono.builder().numero("678578452").build()))
				.emails(Set.of(Correo.builder().email("hpp@g.com").build(),
						Correo.builder().email("ash@g.com").build()))
				.build();
		
		// Antes de persistir el estudiante, para que en las tablas de correos y teléfonos
		// el campo estudiante_id no sea nulo, hay que establecer la relación entre
		// el estudiante y sus correos y teléfonos.
		
		estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
		estudiante1.getEmails().forEach(correo -> correo.setEstudiante(estudiante1));
		
		estudiante2.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante2));
		estudiante2.getEmails().forEach(correo -> correo.setEstudiante(estudiante2));
		
		estudiante3.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante3));
		estudiante3.getEmails().forEach(correo -> correo.setEstudiante(estudiante3));
		
		// una vez bien construido el estudiante entonces lo persistimos. Lo guardamos, vamos.
		estudianteService.saveEstudiante(estudiante1);
		estudianteService.saveEstudiante(estudiante2);
		estudianteService.saveEstudiante(estudiante3);
	}

}
