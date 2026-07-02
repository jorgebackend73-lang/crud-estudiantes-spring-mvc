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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.entities.Profesor;

import com.example.services.FacultadService;
import com.example.services.ProfesorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Anotación para convertir nuestra clase en un controller:
@Controller

// Las peticiones que en la URL tengan profesor vendran a 
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

    // Método que muestra el formulario de creación de profesor
	@GetMapping("/alta")
	public String mostrarFormularioAltaProfesor(Model model,
			@ModelAttribute Profesor profesor) {
			// esto pasa ya el profesor vacio a ModelAtribute
		
		// Se necesitan los departamentos desde la capa de servicios
		model.addAttribute("facultades", 
				facultadService.getAllFacultades());
		
		// Es necesario enviar un objeto profesor vacio para que se vinculen sus propiedades
		// con cada control (elemento input, select, etc.) del formulario:
		// Al final lo comentamos pues con la anotación ModelAtribute ya se recibe
		// como un parametro de ese método.
		// model.addAttribute("profesor", new Profesor ());
		
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

		// Pregunta si se ha enviado foto para el profesor y si es así,
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

		// Método que muestra los detalles de un empleado cuyo id se recibe como
		// parametro
		@GetMapping("/details/{id}")
		public String mostrarDetalles(Model model,
			@PathVariable(name = "id", required = true) int profesor_id) {

			// Recuperar el empleado cuyo id se recibe como parametro
			model.addAttribute("profesor", 
				profesorService.getProfesorById(profesor_id));

				return "detailsProfesor";
			}

		// Metodo para actualizar un profesor.
		// Muestra en el formulario Alta/Mod la info del profesor
		// que se va a utilizar.
		@GetMapping("/update/{id}")
		public String updateProfesor(Model model,
			@PathVariable(name = "id", required = true) int idProfesor) {

				Profesor profesor = profesorService.getProfesorById(idProfesor);

				model.addAttribute("profesor", profesor);

				model.addAttribute("facultades", 
					facultadService.getAllFacultades());


				// Procesamos aquí telefonos y correos. Ibamos a hacerlo en la vista 
				// (formularioAltaModificacion.html) pero lo suyo es hacerlo aquí.
				// Set<Telefono> telefonos = profesor.getTelefonos();

				// if (telefonos.size() > 0) {

				// 	String numerosTelefono = telefonos.stream()
				// 		.map(telefono -> telefono.getNumero())
				// 		.collect(Collectors.joining(";"));

				// 	model.addAttribute("numerosTelefono", numerosTelefono);
				// }


				// Set<Correo> correos = profesor.getEmails();

				// 	if(correos.size() > 0) {

				// 		String direccionesCorreos = correos.stream()
				// 			.map(correo -> correo.getEmail())
				// 			.collect(Collectors.joining(";"));

				// 		model.addAttribute("direccionesCorreos", direccionesCorreos);
									
				// 	}				

				return "formularioAltaModificacionProfesor";
			}

			// Metodo para eliminar un profesor.
			// hay que eliminar también el archivo foto del profesor, en caso de que exista
			@GetMapping("/delete/{idProfesor}")
			public String deleteProfesor(Model model, @PathVariable int idProfesor) {

				// primero comprobar si el profesor tiene foto para eliminarla
				Profesor profesorEliminar = profesorService.getProfesorById(idProfesor);

				if (profesorEliminar.getFoto() != null) {

					// Hay que decir donde, ruta relativa, del fichero a eliminar
					Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/"
							+ profesorEliminar.getFoto());

					if (Files.exists(rutaRelativa)) {

						try {
							Files.delete(rutaRelativa);
						} catch (IOException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					
				}

				// Eliminar al dichoso profesor
				profesorService.deleteProfesor(profesorEliminar);

				return "redirect:/profesores/listar";

			}
    
        


}
