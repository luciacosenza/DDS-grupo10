package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;

public abstract class MedioDeContactoService {

    public MedioDeContactoService() {}

    public abstract MedioDeContacto obtenerMedioDeContacto(Long medioDeContactoId);

    public abstract MedioDeContacto guardarMedioDeContacto(MedioDeContacto medioDeContacto);

    public abstract void contactar(Long medioDeContactoId, String asunto, String cuerpo);
}
