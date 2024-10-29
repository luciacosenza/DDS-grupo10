package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.exception.PasswordNoValidaException;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.repository.usuario.UsuarioRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.ValidadorPassword;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ValidadorPassword validadorPassword;

    public CustomUserDetailsService(UsuarioRepository vUsuarioRepository, ValidadorPassword vValidadorPassword) {
        usuarioRepository = vUsuarioRepository;
        validadorPassword = vValidadorPassword;
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Usuario obtenerUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));

        return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).authorities(List.of(new SimpleGrantedAuthority(usuario.getRole()))).build();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarRole(Long usuarioId, String role) {
        Usuario usuario = obtenerUsuario(usuarioId);
        usuario.setRole(role);

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    public Boolean esPasswordValida(String password) {
        return validadorPassword.esValida(password);
    }

    public void validarPassword(String username, String password) throws PasswordNoValidaException {
        if (!esPasswordValida(password)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_err", password, username));
            throw new PasswordNoValidaException();
        }
    }

    public Boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    public String generarUsername(String param1, String param2) {
        String usernameBase = param2.toLowerCase();
        String usernamePrefix = param1.toLowerCase();

        String fullUsername = usernamePrefix + usernameBase;
        String username;

        // Si ya sé (de entrada) que el username más largo que se puede generar con los dos params ya existe, paso al método b
        if (existeUsuario(fullUsername)) {
            username = fullUsername;
            Integer suffix = 0;

            // Genero un username único con un sufijo numérico, incrementándolo en cada iteración
            while (existeUsuario(username)) {
                username = fullUsername + suffix++;
            }

        } else {

            // Genero un nombre de usuario único, basado en el primer caracter de param1 más el param2
            Integer letraIndex = 0;
            StringBuilder usernameBuilder  = new StringBuilder().append(usernamePrefix.charAt(0)).append(usernameBase);

            while (letraIndex < usernamePrefix.length() && existeUsuario(usernameBuilder.toString())) {
                usernameBuilder.insert(letraIndex + 1, param1.charAt(letraIndex + 1));
                letraIndex++;
            }

            username = usernameBuilder.toString();
        }

        return username;
    }
}
