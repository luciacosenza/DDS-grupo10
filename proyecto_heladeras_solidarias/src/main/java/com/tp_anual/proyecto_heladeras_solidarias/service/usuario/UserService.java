package com.tp_anual.proyecto_heladeras_solidarias.service.usuario;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;
import com.tp_anual.proyecto_heladeras_solidarias.repository.usuario.UserRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.ValidadorPassword;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class UserService {

    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final ValidadorPassword validadorPassword;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository vUserRepository, UserCreator vUserCreator, ValidadorPassword vValidadorPassword) {
        userRepository = vUserRepository;
        userCreator = vUserCreator;
        validadorPassword = vValidadorPassword;
    }

    public User obtenerUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public Optional<User> obtenerUserPorUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User guardarUser(User user) {
        return userRepository.save(user);
    }

    public void registrarUser(String username, String password, User.TipoUser tipo) {
        validarUser(username, password);

        String hashedPassword = passwordEncoder.encode(password);
        User user = userCreator.crearUser(username, password, tipo);
        guardarUser(user);
    }

    public Boolean esPasswordValida(String password) {
        return validadorPassword.esValida(password);
    }

    public Boolean existeUser(String username) {
        return obtenerUserPorUsername(username).isPresent();
    }

    public void validarUser(String username, String password) {
        if (!esPasswordValida(password)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.User.validarUser.esPasswordValida_err", username, password));
            throw new IllegalArgumentException(I18n.getMessage("usuario.User.validarUser.esPasswordValida_exception"));
        }

        if (existeUser(username)) {
            log.log(Level.SEVERE, I18n.getMessage("usuario.User.validarUser.existeUser_err", username));
            throw new IllegalArgumentException(I18n.getMessage("usuario.User.validarUser.existeUser_exception"));
        }
    }

}
