package com.tp_anual.proyecto_heladeras_solidarias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PremiosController {

    // Para mostrar el formulario de carga de premio
    @GetMapping("/cargar_premio")
    public String mostrarFormularioCargarPremio() {
        return "cargar-premio";  // Muestra cargar-premio.html
    }

    // Para procesar el formulario de carga de premio
    @PostMapping("/cargar-premio")
    public String procesarFormularioCargarPremio(
            @RequestParam("nombrePremio") String nombrePremio,
            @RequestParam("costoPuntos") int costoPuntos,
            @RequestParam("categoria") String categoria,
            @RequestParam("adjuntarImagen") MultipartFile adjuntarImagen,
            Model model) {

        // Lógica de procesamiento del premio aquí
        // Por ejemplo: guardar el premio en la base de datos

        // Agregar un atributo al modelo para pasarlo a la vista
        model.addAttribute("mensaje", "Premio cargado correctamente");

        // Redirigir o mostrar la vista de éxito (otra página HTML)
        return "resultado-premio"; // Muestra resultado-premio.html
    }

    // Para mostrar la lista de premios
    @GetMapping("/premios")
    public String mostrarListaPremios(Model model) {
        // Lógica para obtener la lista de premios desde la base de datos

        // Agregar la lista de premios al modelo
        model.addAttribute("premios"/* Lista de premios */);

        return "lista-premios";  // Muestra lista-premios.html
    }
}
