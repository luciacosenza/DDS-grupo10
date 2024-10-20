package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.exception.PasswordNoValidaException;
import com.tp_anual.proyecto_heladeras_solidarias.exception.UsuarioRepetidoException;
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

    public Usuario crearUsuario(String username, String password, Usuario.TipoUsuario tipo) {
        return usuarioCreator.crearUsuario(username, password, tipo);
    }

    public Boolean esPasswordValida(String password) {
        return validadorPassword.esValida(password);
    }

    public Boolean existeUsuario(String username) {
        return obtenerUsuarioPorUsername(username).isPresent();
    }

    public void validarUsuario(String username, String password) throws PasswordNoValidaException {
        if (!esPasswordValida(password)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_err", username, password));
            throw new PasswordNoValidaException();
        }

        /*
        if (existeUsuario(username)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.existeUsuario_err", username));
            throw new UsuarioRepetidoException();
        }
        */
    }

    public String generarUsername(String param1, String param2) {
        String baseUsername = (param1.charAt(0) + param2).toLowerCase();
        String fullUsername = (param1 + param2).toLowerCase();
        String username = baseUsername;

        if (existeUsuario(fullUsername)) {
            username = fullUsername;
            int suffix = 1;

            while (existeUsuario(username)) {
                username = fullUsername + suffix;
                suffix++;
            }
        } else {
            int letraIndex = 1;

            while (existeUsuario(username)) {
                if (letraIndex < param1.length()) {
                    username = baseUsername + param1.charAt(letraIndex);
                    letraIndex++;
                } else {
                    break;
                }
            }
        }

        return username;
    }

    public Usuario registrarUsuario(Usuario usuario) throws PasswordNoValidaException, UsuarioRepetidoException {
        String username = usuario.getUsername();
        String password = usuario.getPassword();
        Usuario.TipoUsuario tipo = usuario.getTipo();

        validarUsuario(username, password);

        // String hashedPassword = passwordEncoder.encode(password);

        return guardarUsuario(usuario);
    }
}
