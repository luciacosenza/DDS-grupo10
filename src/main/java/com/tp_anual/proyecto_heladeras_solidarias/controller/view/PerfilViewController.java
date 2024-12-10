package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class PerfilViewController {

    private final ColaboradorService colaboradorService;
    private final CustomUserDetailsService customUserDetailsService;

    public PerfilViewController(ColaboradorService vColaboradorService, CustomUserDetailsService customUserDetailsService) {
        colaboradorService = vColaboradorService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/perfil-persona-humana")
    public String mostrarPerfilPersonaHumana(Model model) {
        setPaginaActual("/perfil-persona-humana", model);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);
        String eMail = colaborador.getMedioDeContacto(EMail.class).getDireccionCorreo();

        model.addAttribute("colaborador", colaborador);
        model.addAttribute("email", eMail);

        return "perfil-persona-humana";
    }

    @GetMapping("/perfil-persona-juridica")
    public String mostrarPerfilPersonaJuridica(Model model) {
        setPaginaActual("/perfil-persona-juridica", model);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        ColaboradorJuridico colaborador = colaboradorService.obtenerColaboradorJuridicoPorUsername(username);
        String eMail = colaborador.getMedioDeContacto(EMail.class).getDireccionCorreo();

        model.addAttribute("colaborador", colaborador);
        model.addAttribute("email", eMail);

        return "perfil-persona-juridica";
    }
    
    @PostMapping("/perfil-persona-humana/guardar")
    public String guardarPerfilPersonaHumana(
        @RequestParam("nombre") String nombre,
        @RequestParam("apellido") String apellido,
        @RequestParam("direccion") String direccion,
        @RequestParam("ciudad") String ciudad,
        @RequestParam("codigo-postal") String codigoPostal,
        @RequestParam("correo") String correo,
        @RequestParam("password") String password)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumanoPorUsername(username);

        PersonaFisica personaFisica = colaborador.getPersonaFisica();
        Ubicacion domicilio = new Ubicacion(colaborador.getDomicilio().getLatitud(), colaborador.getDomicilio().getLongitud(), direccion, codigoPostal, ciudad, colaborador.getDomicilio().getPais());
        EMail eMail = new EMail(correo);
        Usuario usuario = colaborador.getUsuario();

        personaFisica.setNombre(nombre);
        personaFisica.setApellido(apellido);
        colaborador.setDomicilio(domicilio);
        colaborador.setMedioDeContacto(EMail.class, eMail);

        if (password != null && !password.isEmpty()) {
            usuario.setPassword(password);
            customUserDetailsService.guardarUsuario(usuario);
        }

        colaboradorService.guardarColaborador(colaborador);

        return "redirect:/perfil-persona-humana";
    }

    @PostMapping("/perfil-persona-juridica/guardar")
    public String guardarPerfilPersonaJuridica(
            @RequestParam("razon-social") String razonSocial,
            @RequestParam("direccion") String direccion,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("codigo-postal") String codigoPostal,
            @RequestParam("correo") String correo,
            @RequestParam("password") String password
    )
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        ColaboradorJuridico colaborador = colaboradorService.obtenerColaboradorJuridicoPorUsername(username);

        PersonaJuridica personaJuridica = colaborador.getPersonaJuridica();
        Ubicacion domicilio = new Ubicacion(colaborador.getDomicilio().getLatitud(), colaborador.getDomicilio().getLongitud(), direccion, codigoPostal, ciudad, colaborador.getDomicilio().getPais());
        EMail eMail = new EMail(correo);
        Usuario usuario = colaborador.getUsuario();

        personaJuridica.setRazonSocial(razonSocial);
        colaborador.setDomicilio(domicilio);
        colaborador.setMedioDeContacto(EMail.class, eMail);

        if (password != null && !password.isEmpty()) {
            usuario.setPassword(password);
            customUserDetailsService.guardarUsuario(usuario);
        }

        colaboradorService.guardarColaborador(colaborador);

        return "redirect:/perfil";
    }
    
    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
