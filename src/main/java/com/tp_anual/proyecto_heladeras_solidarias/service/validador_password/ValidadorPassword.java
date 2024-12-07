package com.tp_anual.proyecto_heladeras_solidarias.service.validador_password;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class ValidadorPassword {
    private final List<CriterioValidacion> criterios = new ArrayList<>();

    public ValidadorPassword() {
        criterios.add(new CriterioLargo(8, 32));    // Estas cantidades son arbitrarias, pueden cambiar después
        criterios.add(new CriterioTop10000MasComun());
    }

    // Este método valida que la contraseña cumpla, para cada Criterio pedido
    public Boolean esValida(String contrasenia) {
        return criterios.stream().allMatch(criterio -> criterio.validar(contrasenia));
    }
}