package com.tp_anual.proyecto_heladeras_solidarias.service.validador_password;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class CriterioTop10000MasComun extends CriterioValidacion {
    public CriterioTop10000MasComun() {}
        
    @Override
    public Boolean validar(String contrasenia) {
        List<String> contraseniasComunes;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource("10k-most-common.txt").toURI());
            contraseniasComunes = Files.lines(path).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            log.log(Level.SEVERE, I18n.getMessage("validador.CriterioTop10000MasComun.validar_err"));
            throw new RuntimeException(I18n.getMessage("validador.CriterioTop10000MasComun.validar_exception"));
        }
        
        return !contraseniasComunes.contains(contrasenia);
    }
}