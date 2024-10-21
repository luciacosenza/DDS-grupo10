package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.exception.PasswordNoValidaException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telefono;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.NoUsuario;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class RegistroViewController {

    ColaboradorService colaboradorService;
    UsuarioService usuarioService;

    public RegistroViewController(ColaboradorService vColaboradorService, UsuarioService vUsuarioService) {
        colaboradorService = vColaboradorService;
        usuarioService = vUsuarioService;
    }

    @GetMapping("/seleccion-persona")
    public String mostrarSeleccionPersona() {
        return "seleccion-persona";
    }

    @GetMapping("/registro-persona-humana")
    public String mostrarRegistroPersonaHumana(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro-persona-humana";
    }

    @GetMapping("/registro-persona-juridica")
    public String mostrarRegistroPersonaJuridica() {
        return "registro-persona-juridica";
    }

    @PostMapping("/registro-persona-humana/guardar")
    public String guardarPersonaHumana(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("fecha-nacimiento") LocalDate fechaNacimiento,
            @RequestParam("tipo-documento") Documento.TipoDocumento tipoDocumento,
            @RequestParam("numero-documento") String numeroDocumento,
            @RequestParam("sexo-documento") Documento.Sexo sexoDocumento,
            @RequestParam("pais") String pais,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("calle") String calle,
            @RequestParam("altura") String altura,
            @RequestParam("codigo-postal") String codigoPostal,
            @RequestParam("prefijo") String prefijo,
            @RequestParam("codigo-area") String codigoArea,
            @RequestParam("numero-telefono") String numeroTelefono,
            @RequestParam("correo") String correo,
            @RequestParam("password") String password,
            @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            Model model)
    {
        String username = usuarioService.generarUsername(nombre, apellido);

        try {
            usuarioService.validarUsuario(username, password);
        } catch (PasswordNoValidaException e) {
            result.rejectValue("password", "error.password", e.getMessage()); //TODO: en el mensaje de error el usuario y la contraseña estan invertidos
            return "/registro-persona-humana";
        }

        if (result.hasErrors()) {
            return "registro-persona-humana";
        }

        Documento documento = new Documento(tipoDocumento, numeroDocumento, sexoDocumento);
        PersonaFisica personaFisica = new PersonaFisica(nombre, apellido, documento, fechaNacimiento);
        Ubicacion domicilio = new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, pais);
        ColaboradorHumano colaborador = new ColaboradorHumano(new NoUsuario(), personaFisica, domicilio, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Telefono telefono = new Telefono(prefijo, codigoArea, numeroTelefono);
        EMail eMail = new EMail(correo);
        usuario = usuarioService.crearUsuario(username, password, Usuario.TipoUsuario.COLABORADOR_HUMANO);

        colaborador.agregarMedioDeContacto(telefono);
        colaborador.agregarMedioDeContacto(eMail);

        Long colaboradorId = colaboradorService.guardarColaborador(colaborador).getId();

        colaboradorService.asignarUsuario(colaboradorId, usuario); // TODO: por algun motivo el ususario y la contraseña se guardan como null
        //colaboradorService.contactar(colaboradorId, eMail, "Hola puto", "Tu usuario es: " + username);

        return "redirect:/index";
    }

    @PostMapping("/registro-persona-juridica/guardar")
    public String guardarPersonaJuridica(
            @RequestParam("razon-social") String razonSocial,
            @RequestParam("tipo-persona-juridica") PersonaJuridica.TipoPersonaJuridica tipoPersonaJuridica,
            @RequestParam("rubro") String rubro,
            @RequestParam("pais") String pais,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("calle") String calle,
            @RequestParam("altura") String altura,
            @RequestParam("codigo-postal") String codigoPostal,
            @RequestParam("prefijo") String prefijo,
            @RequestParam("codigo-area") String codigoArea,
            @RequestParam("numero-telefono") String numeroTelefono,
            @RequestParam("correo") String correo,
            @RequestParam("password") String password,
            BindingResult result)
    {
        String username = usuarioService.generarUsername(rubro, razonSocial);
        try {
            usuarioService.validarUsuario(username, password);
        } catch (PasswordNoValidaException e) {
            result.rejectValue("password", "error.password", e.getMessage());

            return "/registro-persona-juridica";
        }

        PersonaJuridica personaJuridica = new PersonaJuridica(razonSocial, tipoPersonaJuridica, rubro);
        Ubicacion domicilio = new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, pais);
        ColaboradorJuridico colaborador = new ColaboradorJuridico(new NoUsuario(), personaJuridica, domicilio, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Telefono telefono = new Telefono(prefijo, codigoArea, numeroTelefono);
        EMail eMail = new EMail(correo);
        Usuario usuario = usuarioService.crearUsuario(username, password, Usuario.TipoUsuario.COLABORADOR_JURIDICO);


        colaborador.agregarMedioDeContacto(telefono);
        colaborador.agregarMedioDeContacto(eMail);

        Long colaboradorId = colaboradorService.guardarColaborador(colaborador).getId();

        colaboradorService.asignarUsuario(colaboradorId, usuario);
        colaboradorService.contactar(colaboradorId, eMail, "Nuevo usuario", "Tu usuario es: " + username);

        return "redirect:/index";
    }
}