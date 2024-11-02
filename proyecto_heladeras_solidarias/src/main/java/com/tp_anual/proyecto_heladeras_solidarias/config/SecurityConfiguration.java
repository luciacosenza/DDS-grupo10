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
                        .requestMatchers("/css/**", "/js/**", "/assets/**") // Permitir recursos estáticos
                        .permitAll()
                        .requestMatchers("/", "/seleccion-persona", "/quienes-somos", "/como-participar",
                                "/registro-persona-humana", "/registro-persona-humana/guardar",
                                "/registro-persona-juridica", "/registro-persona-juridica/guardar",
                                "/registro-tecnico", "/registro-tecnico/guardar").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer ->  httpSecurityFormLoginConfigurer
                        .loginPage("/iniciar-sesion").permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/iniciar-sesion?error"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/cerrar-sesion"))
                        .logoutSuccessUrl("/"))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
