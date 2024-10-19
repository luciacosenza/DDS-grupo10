package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.repository.usuario.UsuarioRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.ValidadorPassword;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioCreator usuarioCreator;
    private final ValidadorPassword validadorPassword;
    // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository vUsuarioRepository, UsuarioCreator vUsuarioCreator, ValidadorPassword vValidadorPassword) {
        usuarioRepository = vUsuarioRepository;
        usuarioCreator = vUsuarioCreator;
        validadorPassword = vValidadorPassword;
    }

    public Usuario obtenerUsuario(Long userId) {
        return usuarioRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Optional<Usuario> obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario crearUser(String username, String password, Usuario.TipoUsuario tipo) {
        return usuarioCreator.crearUsuario(username, password, tipo);
    }

    public Boolean esPasswordValida(String password) {
        return validadorPassword.esValida(password);
    }

    public Boolean existeUsuario(String username) {
        return obtenerUsuarioPorUsername(username).isPresent();
    }

    public void validarUsuario(String username, String password) {
        if (!esPasswordValida(password)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_err", username, password));
            throw new IllegalArgumentException(I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_exception"));
        }

        if (existeUsuario(username)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.existeUsuario_err", username));
            throw new IllegalArgumentException(I18n.getMessage("usuario.Usuario.validarUsuario.existeUsuario_exception"));
        }
    }

    public Usuario registrarUsuario(Usuario usuario) {
        String username = usuario.getUsername();
        String password = usuario.getPassword();
        Usuario.TipoUsuario tipo = usuario.getTipo();

        validarUsuario(username, password);

        // String hashedPassword = passwordEncoder.encode(password);

        return guardarUsuario(usuario);
    }

    public String generarUsername(String nombre, String apellido) {
        String baseUsername = (nombre.charAt(0) + apellido).toLowerCase();
        String username = baseUsername;
        Integer suffix = 1;

        while (existeUsuario(username)) {
            username = baseUsername + suffix;
            suffix++;
        }

        return username;
    }
}
