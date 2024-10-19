package com.tp_anual.proyecto_heladeras_solidarias.controller;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
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
    public String mostrarRegistroPersonaHumana() {
        return "registro-persona-humana";
    }

    @GetMapping("/registro-persona-juridica")
    public String mostrarRegistroPersonaJuridica() {
        return "registro-persona-juridica";
    }

    @GetMapping("/crear-usuario")
    public String mostrarCrearUsuario(@RequestParam("colaboradorId") Long colaboradorId, Model model) {
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("colaborador", colaborador);

        return "crear-usuario";
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
        @RequestParam("correo") String correo)
    {
        Documento documento = new Documento(tipoDocumento, numeroDocumento, sexoDocumento);
        PersonaFisica personaFisica = new PersonaFisica(nombre, apellido, documento, fechaNacimiento);
        Ubicacion domicilio = new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, pais);
        ColaboradorHumano colaborador = new ColaboradorHumano(new NoUsuario(), personaFisica, domicilio, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Telefono telefono = new Telefono(prefijo, codigoArea, numeroTelefono);
        EMail eMail = new EMail(correo);

        colaborador.agregarMedioDeContacto(telefono);
        colaborador.agregarMedioDeContacto(eMail);

        Long colaboradorId = colaboradorService.guardarColaborador(colaborador).getId();

        return "redirect:/crear-usuario?colaboradorId=" + colaboradorId;
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
            @RequestParam("correo") String correo)
    {
        PersonaJuridica personaJuridica = new PersonaJuridica(razonSocial, tipoPersonaJuridica, rubro);
        Ubicacion domicilio = new Ubicacion(null, null, (calle + " " + altura), codigoPostal, ciudad, pais);
        ColaboradorJuridico colaborador = new ColaboradorJuridico(new NoUsuario(), personaJuridica, domicilio, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Telefono telefono = new Telefono(prefijo, codigoArea, numeroTelefono);
        EMail eMail = new EMail(correo);

        colaborador.agregarMedioDeContacto(telefono);
        colaborador.agregarMedioDeContacto(eMail);

        colaboradorService.guardarColaborador(colaborador);

        Long colaboradorId = colaboradorService.guardarColaborador(colaborador).getId();

        return "redirect:/crear-usuario?colaboradorId=" + colaboradorId;
    }

    @PostMapping("/crear-usuario/guardar")
    public String guardarUsuario(@RequestParam("colaboradorId") Long colaboradorId, @ModelAttribute Usuario usuario, BindingResult result) {
        try {
            usuarioService.registrarUsuario(usuario);
            colaboradorService.asignarUsuario(colaboradorId, usuario);
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", "error.username", e.getMessage());
            return "redirect:/crear-usuario?colaboradorId=" + colaboradorId;
        }

        return "redirect:/index";
    }


}
