package com.tp_anual.proyecto_heladeras_solidarias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService vUserDetailsService) {
        userDetailsService = vUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        // Permito recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/assets/**").permitAll()
                        // Endpoints "públicos"
                        .requestMatchers("/", "/quienes-somos", "/como-participar", "/mapa-heladeras",
                                "/seleccion-persona",
                                "/registro-persona-humana", "/registro-persona-humana/guardar",
                                "/registro-persona-juridica", "/registro-persona-juridica/guardar",
                                "/registro-tecnico", "/registro-tecnico/guardar",
                                "/ubicador-api", "/ubicador-api/{latitud}/{longitud}",
                                "/api/heladeras").permitAll()
                        // Endpoints para cualquier usuario
                        .requestMatchers("/api/heladeras").authenticated()
                        // Endpoints exclusivos de 'ROL_CJ' (Colaborador Jurídico)
                        .requestMatchers(new AntPathRequestMatcher("/cargar-premio"),
                                new AntPathRequestMatcher("/colaborar-personas-juridicas"),
                                new AntPathRequestMatcher("/colocar-heladera"))
                        .hasAuthority("ROL_CJ")
                        // Endpoints exclusivos de 'ROL_CH' (Colaborador Humano)
                        .requestMatchers(new AntPathRequestMatcher("/colaborar-personas-fisicas"),
                                new AntPathRequestMatcher("/distribuir-viandas"),
                                new AntPathRequestMatcher("/donar-vianda"),
                                new AntPathRequestMatcher("/registrar-persona-vulnerable"),
                                new AntPathRequestMatcher("/suscribirse"))
                        .hasAuthority("ROL_CH")
                        // Endpoints accesibles por 'ROL_CJ' y 'ROL_CH' (ambos colaboradores)
                        .requestMatchers(new AntPathRequestMatcher("/contribuciones"),
                                new AntPathRequestMatcher("/donar-dinero"),
                                new AntPathRequestMatcher("/reportar-falla-tecnica"),
                                new AntPathRequestMatcher("/tienda"))
                        .hasAnyAuthority("ROL_CH", "ROL_CJ")
                        // Endpoints exclusivos de 'ROL_ADMIN' (admin)
                        .requestMatchers(new AntPathRequestMatcher("/admin"),
                                new AntPathRequestMatcher("/registro-tecnico"))
                        .hasAuthority("ROL_ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")  // Página de login personalizada
                        .defaultSuccessUrl("/?loginSuccess=true", true)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/?logoutSuccess=true")
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
