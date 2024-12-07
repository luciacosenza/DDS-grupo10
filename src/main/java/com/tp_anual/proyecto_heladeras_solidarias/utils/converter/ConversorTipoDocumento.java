package com.tp_anual.proyecto_heladeras_solidarias.utils.converter;

import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Log
public class ConversorTipoDocumento {

    private final HashMap<String, TipoDocumento> conversorTipoDocumento = new HashMap<>();

    public ConversorTipoDocumento() {
        conversorTipoDocumento.put("DNI", TipoDocumento.DNI);
        conversorTipoDocumento.put("LC",TipoDocumento.LIBRETA_CIVICA);
        conversorTipoDocumento.put("LE",TipoDocumento.LIBRETA_ENROLAMIENTO);
    }

    public TipoDocumento convertirStrATipoDocumento(String tipoDocumentoStr) {
        TipoDocumento tipoDocumento = conversorTipoDocumento.get(tipoDocumentoStr);
        
        if (tipoDocumento == null) {
            MessageSource messageSource = SpringContext.getBean(MessageSource.class);
            String logMessage = messageSource.getMessage("conversor.ConversorTipoDeDocumento.convertirStrATipoDocumento_err", new Object[] {tipoDocumentoStr}, Locale.getDefault());
            String exceptionMessage = messageSource.getMessage("conversor.ConversorTipoDeDocumento.convertirStrATipoDocumento_exception", null, Locale.getDefault());

            log.log(Level.SEVERE, logMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        
        return tipoDocumento;
    }
}
