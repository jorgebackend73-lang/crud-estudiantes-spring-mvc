package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Profesor;
import com.example.entities.Telefono;
import com.example.models.Genero;
// import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;
// import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CrudEstudiantesSpringMvcApplication implements CommandLineRunner {

	private final EstudianteService estudianteService;
	private final FacultadService facultadService;
	private final ProfesorService profesorService;
	// private final CorreoService correoService;
	// private final TelefonoService telefonoService;

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
		
		// Persistir las facultads en la base de datos
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

		//Crear Profesores
		Profesor profesor1 = Profesor.builder()
				.nombre("Juan")
				.primerApellido("Perez")
				.segundoApellido("Garcia")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2026, 1, 15))
				.salario(new BigDecimal(3500.50))
				.facultad(facultad1)
				.foto("profe1.jpg")				
				.build();

		Profesor profesor2 = Profesor.builder()
				.nombre("Jochu")
				.primerApellido("Pelón")
				.segundoApellido("Zarro")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2025, 3, 25))
				.salario(new BigDecimal(3700.55))
				.facultad(facultad1)
				.foto("profe2.jpg")				
				.build();

		Profesor profesor3 = Profesor.builder()
				.nombre("Myrna")
				.primerApellido("Perez")
				.segundoApellido("Corral")
				.genero(Genero.MUJER)
				.fechaAlta(LocalDate.of(2024, 4, 11))
				.salario(new BigDecimal(4500.50))
				.facultad(facultad2)
				.foto("profe3.jpg")				
				.build();

		Profesor profesor4 = Profesor.builder()
				.nombre("Lorena")
				.primerApellido("Chuzo")
				.segundoApellido("Garcia")
				.genero(Genero.MUJER)
				.fechaAlta(LocalDate.of(2025, 11, 5))
				.salario(new BigDecimal(3300.50))
				.facultad(facultad2)
				.foto("profe4.jpg")				
				.build();

		Profesor profesor5 = Profesor.builder()
				.nombre("Leontus")
				.primerApellido("Cabrera")
				.segundoApellido("López")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2025, 12, 5))
				.salario(new BigDecimal(3100.70))
				.facultad(facultad3)
				.foto("profe5.jpg")				
				.build();

			Profesor profesor6 = Profesor.builder()
				.nombre("Mariana")
				.primerApellido("Ortega")
				.segundoApellido("Gónzalez")
				.genero(Genero.MUJER)
				.fechaAlta(LocalDate.of(2024, 11, 12))
				.salario(new BigDecimal(4700.50))
				.facultad(facultad3)
				.foto("profe6.jpg")				
				.build();

		// una vez bien construido el profesor entonces lo persistimos.
		profesorService.saveProfesor(profesor1);
		profesorService.saveProfesor(profesor2);
		profesorService.saveProfesor(profesor3);
		profesorService.saveProfesor(profesor4);
		profesorService.saveProfesor(profesor5);
		profesorService.saveProfesor(profesor6);
		
	}

}
