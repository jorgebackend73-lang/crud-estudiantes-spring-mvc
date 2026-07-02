package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// import com.example.entities.Estudiante;
import com.example.entities.Profesor;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Anotación para convertir nuestra clase en un controller:
@Controller

// Las peticiones que en la URL tengan estudiante vendran a 
// este controlador gracias a esta anotación:
@RequestMapping("/profesores")

//Para ProfesorService. Inyeccion de dependencias por constructor.
@RequiredArgsConstructor
public class ProfesorController {

    private static final Logger LOG = Logger.getLogger("ProfesorController");

    // Esto es el Handle Request en el patron MVC.
	// Método responsable de hablar con la capa de servicios.
	// Tenemos que hacer que se comunique con la capa de servicios.

    private final ProfesorService profesorService;
	private final FacultadService facultadService;
    
    // y traer el listado de profesores y su vista.
	// Las vistas estan en src/main/templates.
	@GetMapping("/listar")
	public String listarProfesores(Model model) {
		
		// Usamos model y le agregamos como atributo todos los profesores:
		model.addAttribute("profesores",
			profesorService.getAllProfesores());
		
		// Retorna la vista para listar profesores.
		// Endpoint lugar donde se muestra alguna capacidad de nuestro código.
		// Devolvemos listadoProfesores el nombre de nuestro template.
				
		return "listadoProfesores"; // Retorna el nombre de la vista para listar profesores.		
		
	}

    // Método que muestra el formulario de creación de estudiante
	@GetMapping("/alta")
	public String mostrarFormularioAltaProfesor(Model model,
			@ModelAttribute Profesor profesor) {
			// esto pasa ya el estudiante vacio a ModelAtribute
		
		// Se necesitan los departamentos desde la capa de servicios
		model.addAttribute("facultades", 
				facultadService.getAllFacultades());
		
		// Es necesario enviar un objeto estudiante vacio para que se vinculen sus propiedades
		// con cada control (elemento input, select, etc.) del formulario:
		// Al final lo comentamos pues con la anotación ModelAtribute ya se recibe
		// como un parametro de ese método.
		// model.addAttribute("estudiante", new Estudiante ());
		
		return "formularioAltaModificacionProfesor"; // Creamos vista en template

	}

    // Método para recibir los datos del formulario de creación de profesor
	// Alta/Modificación profesor
	// Model Atribute coge al profesor
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacionProfesor (
			@Valid
			@ModelAttribute Profesor profesor,
			BindingResult result,
			// @RequestParam String numerosTelefono,
			// @RequestParam String direccionesCorreo,
			Model model,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		
		
		// Comprobar si hay errores en la información procedente del formulario
		if (result.hasErrors()) {
			
			model.addAttribute("facultades", facultadService.getAllFacultades());
			
			return "formularioAltaModificacionProfesor";
		}

		// Pregunta si se ha enviado foto para el estudiante y si es así,
		// se guarda el nombre de la foto en la propiedad, atributo o variable,
		// mienbro de la clase foto. Y se guarda el contenido de la foto en un
		// archivo en el sistema de archivos del servidor.

		if (file != null && !file.isEmpty()) {

			Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
			String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
			Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

			try {
				byte[] bytesFotoRecibida = file.getBytes();
				Files.write(rutaCompleta, bytesFotoRecibida);
				profesor.setFoto(file.getOriginalFilename());
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

        LOG.info("Objeto profesor recibido");
		LOG.info(profesor.toString());

        profesorService.saveProfesor(profesor);
		
		return "redirect:/profesores/listar"; // Redirige a la lista de profesores. 
		
	}
    
        


}
