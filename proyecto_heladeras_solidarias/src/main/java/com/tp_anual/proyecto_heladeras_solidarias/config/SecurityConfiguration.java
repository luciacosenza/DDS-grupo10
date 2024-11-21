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

        http.csrf(crsf -> crsf.ignoringRequestMatchers(toH2Console()))      // Habilito Consola H2
                .userDetailsService(userDetailsService)
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
                        // Permito H2 y la api para cualquier usuario
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers("/api/heladeras").authenticated()
                        // Endpoints exclusivos de 'ROL_CJ' (Colaborador Jurídico)
                        .requestMatchers("/cargar-premio", "/colaborar-personas-juridicas", "/colocar-heladera")
                        .hasRole("ROL_CJ")
                        // Endpoints exclusivos de 'ROL_CH' (Colaborador Humano)
                        .requestMatchers("/colaborar-personas-fisicas", "/distribuir-viandas", "/donar-vianda",
                                "/registrar-persona-vulnerable", "/suscribirse")
                        .hasRole("ROL_CH")
                        // Endpoints accesibles por 'ROL_CJ' y 'ROL_CH' (ambos colaboradores)
                        .requestMatchers("/contribuciones", "/donar-dinero", "/reportar-falla-tecnica", "/tienda")
                        .hasAnyRole("ROL_CH", "ROL_CJ")
                        // Endpoints exclusivos de 'ROL_ADMIN' (admin)
                        .requestMatchers("/admin").hasRole("ROL_ADMIN")
                        .requestMatchers("/registro-tecnico").hasRole("ROL_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer ->  httpSecurityFormLoginConfigurer
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/?loginSuccess=true", true)
                        .failureUrl("/login?error"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/?logoutSuccess=true"))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
