package com.tp_anual.proyecto_heladeras_solidarias.converter;

import java.util.HashMap;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

@Log
public class ConversorTipoDocumento {
    private static final HashMap<String, TipoDocumento> conversorTipoDocumento = new HashMap<>();

    static {
        conversorTipoDocumento.put("DNI", TipoDocumento.DNI);
        conversorTipoDocumento.put("LC",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("LE",TipoDocumento.LIBRETA_ENROLAMIENTO);
    }

    public static TipoDocumento convertirStrATipoDocumento(String tipoDocumentoStr) {
        TipoDocumento tipoDocumento = conversorTipoDocumento.get(tipoDocumentoStr);
        
        if (tipoDocumento == null) {
            log.log(Level.SEVERE, I18n.getMessage("conversor.ConversorTipoDeDocumento.convertirStrATipoDocumento_err", tipoDocumentoStr));
            throw new IllegalArgumentException(I18n.getMessage("conversor.ConversorTipoDeDocumento.convertirStrATipoDocumento_exception"));
        }
        
        return tipoDocumento;
    }
}
