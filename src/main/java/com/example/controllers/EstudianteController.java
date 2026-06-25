package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Anotación para convertir nuestra clase en un controller:
@Controller

// Las peticiones que en la URL tengan estudiante vendran a 
// este controlador gracias a esta anotación:
@RequestMapping("/estudiantes")

//Para EstudianteService. Inyeccion de dependencias por constructor.
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger LOG = Logger.getLogger("EstudianteController");
	
	// Esto es el Handle Request en el patron MVC.
	// Método responsable de hablar con la capa de servicios.
	// Tenemos que hacer que se comunique con la capa de servicios.
	
	// private final EstudianteServiceImpl estudianteService;
	private final EstudianteService estudianteService;
	private final FacultadService facultadService;
	private final CorreoService correoService;
	private final TelefonoService telefonoService;



//	 EstudianteController(EstudianteServiceImpl estudianteServiceImpl) {
//		 this.estudianteServiceImpl = estudianteServiceImpl;
//	}
	
	
	// y traer el listado de estudiantes y su vista.
	// Las vistas estan en src/main/templates.
	@GetMapping("/listar")
	public String listarEstudiantes(Model model) {
		
		// Usamos model y le agregamos como atributo todos los estudiantes:
		model.addAttribute("estudiantes",
			estudianteService.getAllEstudiantes());
		
		// Retorna la vista para listar estudiantes.
		// Endpoint lugar donde se muestra alguna capacidad de nuestro código.
		// Devolvemos listadoEstudiantes el nombre de nuestro template.
				
		return "listadoEstudiantes"; // Retorna el nombre de la vista para listar empleados
		
		
	}
	
	// Método que muestra el formulario de creación de estudiante
	@GetMapping("/alta")
	public String mostrarFormularioAlta(Model model,
			@ModelAttribute Estudiante estudiante) {
			// esto pasa ya el estudiante vacio a ModelAtribute
		
		// Se necesitan los departamentos desde la capa de servicios
		model.addAttribute("facultades", 
				facultadService.getAllFacultades());
		
		// Es necesario enviar un objeto estudiante vacio para que se vinculen sus propiedades
		// con cada control (elemento input, select, etc.) del formulario:
		// Al final lo comentamos pues con la anotación ModelAtribute ya se recibe
		// como un parametro de ese método.
		// model.addAttribute("estudiante", new Estudiante ());
		
		return "formularioAltaModificacion"; // Creamos vista en template
	}
	
	// Método para recibir los datos del formulario de creación de estudiante
	// Alta/Modificación Estudiante
	// Model Atribute coge al estudiante
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacion (
			@Valid
			@ModelAttribute Estudiante estudiante,
			BindingResult result,
			@RequestParam String numerosTelefono,
			@RequestParam String direccionesCorreo,
			Model model,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		// queremos ademas del estudiante otro parametro bajo name para numerosTelefono
		// y convertirlo en string. Al convertir a una variable con el mismo nombre que el
		// parametro no hace falta volver a escribirlo.
		
		// Comprobar si hay errores en la información procedente del formulario
		if (result.hasErrors()) {
			
			model.addAttribute("facultades", facultadService.getAllFacultades());
			
			return "formularioAltaModificacion";
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
				estudiante.setFoto(file.getOriginalFilename());
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		
		LOG.info("Objeto estudiante recibido");
		LOG.info(estudiante.toString());
		LOG.info("Números de teléfono recibidos: " + numerosTelefono);
		LOG.info("Direcciones de correo recibidas: " + direccionesCorreo);
		
		// Aquí para que se reciban adecuadamente telefonos y correos, 
		// que vienen en un string separdos por ; 
		// hay que convertirlos en una lista de objetos telefono y correo
		// para luego agregarlos al objeto Estudiante antes de persistirlo.
		
		// Declaramos Set de java util donde alojar nuestra lista de telefonos:
		// Set<Telefono> telefonos = new HashSet<Telefono>();
		// comentado lo de arriba pq y lo tenemos en la entidad estudiante
		// y lo estamos repitiendo aquí. Ya tenemos el set.
		
		// Pero primero preguntar si nos llegan tenlefonos:
		if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {
			
			// metemos los elementos de la cadena en un array:
			String[] arrayNumerosTelefono = numerosTelefono.split(";");
			
			// El array lo convertimos en una lista que podremos recorrer
			// Cada elemto es un string y la lista se llama listadoNumeros del tipo Array
			// de java.util lo convetimos en esa lista con .asList al que le pasamos como hemos
			// dicho el arrayNumerosTelefono:
			List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);
			
			// le pasamos la lista que recorremos con un forEach y a traves de una lambda
			// los recibe y asigna adecuadamente a cada estudiante recibido como parametro:
			// Así se crea la lista de telefonos del estudiante:
			listadoNumeros.forEach(numero -> {
				estudiante.getTelefonos().add(Telefono
						.builder()
						.numero(numero)
						.estudiante(estudiante)
						.build());
			});
			
			// nos sobra pq ya tenemos los telefonos del estudiante en la sentencia de arriba
			// estudiante.setTelefonos(telefonos);
			
		}
	
		// Declaramos Set de java util donde alojar nuestra lista de telefonos:
		// Lo comentamos aquí tambien pq ya tenemos el set creado en la entidad Estudiante.
				// Set<Correo> correos = new HashSet<Correo>();
				
				// Pero primero preguntar si nos llegan correos:
				if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
					
					// metemos los elementos de la cadena en un array:
					String[] arrayDireccionesCorreo = direccionesCorreo.split(";");
					
					// El array lo convertimos en una lista que podremos recorrer
					// Cada elemto es un string y la lista se llama listadoCorreos el tipo Array
					// de java.util lo convertimos en esa lista con .asList al que le pasamos como hemos
					// dicho el arrayDireccionesCorreo:
					List<String> listadoCorreos = Arrays.asList(arrayDireccionesCorreo);
					
					// le pasamos la lista que recorremos con un forEach y a traves de una lambda
					// los recibe y asigna adecuadamente a cada estudiante recibido como parametro:
					// Así se crea la lista de correos del estudiante:
					listadoCorreos.forEach(dirCorr -> {
						estudiante.getEmails().add(Correo
								.builder()
								.email(dirCorr)
								.estudiante(estudiante)
								.build());
					});
					
					// estudiante.setEmails(correos);
					
				}

				// Antes de persistir al estudiante hay que eliminar los teléfonos y correos
				// que tenga.
				if (estudiante.getId() != 0) {
					if (telefonoService.existsByEstudiante(estudiante)) 
						telefonoService.deleteByEstudiante(estudiante);

					if(correoService.existsByEstudiante(estudiante))
						correoService.deleteByEstudiante(estudiante);
				}
		
		
		// Se recibe un objeto Estudiante con los datos del formulario
		// Se envía a la capa de servicios para que sea persistido
		estudianteService.saveEstudiante(estudiante);
		
		return "redirect:/estudiantes/listar"; // Redirige a la lista de estudiantes 
		
	}

		// Método que muestra los detalles de un empleado cuyo id se recibe como
		// parametro
		@GetMapping("/details/{id}")
		public String mostrarDetalles(Model model,
			@PathVariable(name = "id", required = true) int estudiante_id) {

			// Recuperar el empleado cuyo id se recibe como parametro
			model.addAttribute("estudiante", 
				estudianteService.getEstudianteById(estudiante_id));

				return "details";
			}
	
		// Metodo para actualizar un estudiante.
		// Muestra en el formulario Alta/Mod la info del estudiante
		// que se va a utilizar.
		@GetMapping("/update/{id}")
		public String updateEstudiante(Model model,
			@PathVariable(name = "id", required = true) int idEstudiante) {

				Estudiante estudiante = estudianteService.getEstudianteById(idEstudiante);

				model.addAttribute("estudiante", estudiante);

				model.addAttribute("facultades", 
					facultadService.getAllFacultades());


				// Procesamos aquí telefonos y correos. Ibamos a hacerlo en la vista 
				// (formularioAltaModificacion.html) pero lo suyo es hacerlo aquí.
				Set<Telefono> telefonos = estudiante.getTelefonos();

				if (telefonos.size() > 0) {

					String numerosTelefono = telefonos.stream()
						.map(telefono -> telefono.getNumero())
						.collect(Collectors.joining(";"));

					model.addAttribute("numerosTelefono", numerosTelefono);
				}


				Set<Correo> correos = estudiante.getEmails();

					if(correos.size() > 0) {

						String direccionesCorreos = correos.stream()
							.map(correo -> correo.getEmail())
							.collect(Collectors.joining(";"));

						model.addAttribute("direccionesCorreos", direccionesCorreos);
									
					}				

				return "formularioAltaModificacion";
			}

			// Metodo para eliminar un estudiante, con sus correos y sus telefs correspondienes
			// hay que eliminar también el archivo foto del estudiante, en caso de que exista
			@GetMapping("/delete/{idEstudiante}")
			public String deleteEstudiante(Model model, @PathVariable int idEstudiante) {

				// primero comprobar si el estudiante tiene foto para eliminarla
				Estudiante estudianteEliminar = estudianteService.getEstudianteById(idEstudiante);

				if (estudianteEliminar.getFoto() != null) {

					// Hay que decir donde, ruta relativa, del fichero a eliminar
					Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/"
							+ estudianteEliminar.getFoto());

					if (Files.exists(rutaRelativa)) {

						try {
							Files.delete(rutaRelativa);
						} catch (IOException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					
				}

				// Eliminar al dichoso estudiante
				estudianteService.deleteEstudiante(estudianteEliminar);

				return "redirect:/estudiante/listar";

			}



}
