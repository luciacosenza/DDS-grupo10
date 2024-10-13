package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.repository.contacto.MedioDeContactoRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class WhatsAppService extends MedioDeContactoService {

    public WhatsAppService(MedioDeContactoRepository vMedioDeContactoRepository) {
        super(vMedioDeContactoRepository);

    }

    public void contactar(Long whatsAppId, String asunto, String cuerpo) {} // TODO: Se implementará posteriormente, por ahora sólo lo creamos para los Tests
}
