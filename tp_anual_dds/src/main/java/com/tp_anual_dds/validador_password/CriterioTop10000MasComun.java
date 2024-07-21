package com.tp_anual_dds.validador_password;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CriterioTop10000MasComun extends CriterioValidacion {

    public CriterioTop10000MasComun() {}
        
    @Override
    public Boolean validar(String contrasenia) {
        List<String> contraseniasComunes;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource("10k-most-common.txt").toURI());
            contraseniasComunes = Files.lines(path).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            contraseniasComunes = List.of();    // Lista vacia si hay un error
        }
        
        return !contraseniasComunes.contains(contrasenia);
    }
}