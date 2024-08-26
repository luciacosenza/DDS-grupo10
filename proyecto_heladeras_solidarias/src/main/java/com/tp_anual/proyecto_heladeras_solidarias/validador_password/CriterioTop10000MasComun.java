package com.tp_anual.proyecto_heladeras_solidarias.validador_password;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class CriterioTop10000MasComun extends CriterioValidacion {
    private static final Logger logger = Logger.getLogger(CriterioTop10000MasComun.class.getName());

    public CriterioTop10000MasComun() {}
        
    @Override
    public Boolean validar(String contrasenia) {
        List<String> contraseniasComunes;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource("10k-most-common.txt").toURI());
            contraseniasComunes = Files.lines(path).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            logger.log(Level.SEVERE, I18n.getMessage("validador.CriterioTop10000MasComun.validar_err"));
            throw new RuntimeException(I18n.getMessage("validador.CriterioTop10000MasComun.validar_exception"));
        }
        
        return !contraseniasComunes.contains(contrasenia);
    }
}