package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.Telefono;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.TelefonoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TelefonoService extends MedioDeContactoService {

    private final TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository vTelefonoRepository) {
        super();
        telefonoRepository = vTelefonoRepository;
    }

    @Override
    public Telefono obtenerMedioDeContacto(Long telefonoId) {
        return telefonoRepository.findById(telefonoId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    @Override
    public Telefono guardarMedioDeContacto(MedioDeContacto telefono) {
        return telefonoRepository.save((Telefono) telefono);
    }

    @Override
    public void contactar(Long telefonoId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
