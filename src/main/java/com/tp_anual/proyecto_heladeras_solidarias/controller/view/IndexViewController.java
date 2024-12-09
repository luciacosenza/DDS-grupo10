package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
public class IndexViewController {

    public IndexViewController() {}

    @GetMapping("/")
    public String mostrarPaginaPrincipal(
            @RequestParam(value = "loginSuccess", required = false) String loginSuccess,
            @RequestParam(value = "logoutSuccess", required = false) String logoutSucces,
            @AuthenticationPrincipal OAuth2AuthenticationToken principal,

            Model model) {

        if (principal != null) {
            // Obtener información del usuario autenticado
            String username = principal.getPrincipal().getAttribute("name");  // Nombre del usuario
            String email = principal.getPrincipal().getAttribute("email");    // Correo del usuario

            // Añadir los datos al modelo
            model.addAttribute("userName", username);
            model.addAttribute("userEmail", email);
        }


        if (loginSuccess != null) {
            model.addAttribute("loginSuccess", true);
        }

        if (logoutSucces != null) {
            model.addAttribute("logoutSuccess", true);
        }

        setPaginaActual("/index", model);
        return "index";
    }

    void setPaginaActual(String pagina, Model model) {;
        model.addAttribute("paginaActual", pagina);
    }
}
