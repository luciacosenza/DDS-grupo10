package com.tp_anual.proyecto_heladeras_solidarias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

//    @GetMapping("/cargar-premio")
//    public String mostrarCargarPremio() {
//        return "cargar-premio";
//    }

    @GetMapping("/colaborar-personas-fisicas")
    public String mostrarColaborarPersonasFisicas() {
        return "colaborar-personas-fisicas";
    }

    @GetMapping("/colaborar-personas-juridicas")
    public String mostrarColaborarPersonasJuridicas() {
        return "colaborar-personas-juridicas";
    }

    @GetMapping("/colocar-heladera")
    public String mostrarColocarHeladera() {
        return "colocar-heladera";
    }

    @GetMapping("/como-participar")
    public String mostrarComoParticipar() {
        return "como-participar";
    }

    @GetMapping("/crear-usuario")
    public String mostrarCrearUsuario() {
        return "crear-usuario";
    }

    @GetMapping("/donar-dinero")
    public String mostrarDonarDinero() {
        return "donar-dinero";
    }

    @GetMapping("/iniciar-sesion")
    public String mostrarIniciarSesion() {
        return "iniciar-sesion";
    }

    @GetMapping("/mapa-heladeras")
    public String mostrarMapaHeladeras() {
        return "mapa-heladeras";
    }

    @GetMapping("/quienes-somos")
    public String mostrarQuienesSomos() {
        return "quienes-somos";
    }

    @GetMapping("/registrar-persona-vulnerable")
    public String mostrarRegistrarPersonaVulnerable() {
        return "registrar-persona-vulnerable";
    }



    @GetMapping("/suscribirse")
    public String mostrarSuscribirse() {
        return "suscribirse";
    }
}
