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
            log.log(Level.SEVERE, I18n.getMessage("usuario.Usuario.validarUsuario.esPasswordValida_err", password, username));
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

    public Usuario registrarUsuario(Usuario usuario) throws PasswordNoValidaException, UsuarioRepetidoException {
        String username = usuario.getUsername();
        String password = usuario.getPassword();
        Usuario.TipoUsuario tipo = usuario.getTipo();

        validarUsuario(username, password);

        // String hashedPassword = passwordEncoder.encode(password);

        return guardarUsuario(usuario);
    }
}
