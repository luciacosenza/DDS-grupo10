package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.utils.converter.ConversorFormaContribucion;
import com.tp_anual.proyecto_heladeras_solidarias.utils.converter.ConversorTipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.exception.migrador.FilaDeDatosIncompletaException;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;

import com.tp_anual.proyecto_heladeras_solidarias.service.usuario.CustomUserDetailsService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TransformacionDeDatos {

    private final CustomUserDetailsService customUserDetailsService;
    private final ConversorFormaContribucion conversorFormaContribucion;
    private final ConversorTipoDocumento conversorTipoDocumento;

    private final I18nService i18nService;

    public TransformacionDeDatos(CustomUserDetailsService vCustomUserDetailsService, ConversorFormaContribucion vConversorFormaContribucion, ConversorTipoDocumento vConversorTipoDocumento, I18nService vI18nService) {
        customUserDetailsService = vCustomUserDetailsService;
        conversorFormaContribucion = vConversorFormaContribucion;
        conversorTipoDocumento = vConversorTipoDocumento;

        i18nService = vI18nService;
    }

    private String quitarEspacios(String string) {
        return string.replaceAll("\\s+", "");
    }

    private String quitarNumericosYEspeciales(String string) {
        return string.replaceAll("[^a-zA-Z]", "");
    }

    private String limpiarTexto(String string) {
        return quitarEspacios(quitarNumericosYEspeciales(string));
    }

    private Contribucion registrarContribucion(String formaContribucionStr, LocalDateTime fechaContribucion) throws DatosInvalidosCrearCargaOfertaException, DatosInvalidosCrearDistribucionViandasException, DatosInvalidosCrearDonacionDineroException, DatosInvalidosCrearDonacionViandaException, DatosInvalidosCrearHCHException, DatosInvalidosCrearRPESVException {
        ContribucionCreator creator = conversorFormaContribucion.convertirStrAContribucionCreator(formaContribucionStr);
        return creator.crearContribucion(null, fechaContribucion, true);    // Tengo que guardar al Colaborador como null y asignarlo después de guardar al Colaborador porque sino tengo un problema de persistencia cíclica con las Contribuciones
    }
    
    private ColaboradorHumano procesarColaborador(String[] data) throws FilaDeDatosIncompletaException, DatosInvalidosCrearCargaOfertaException, DatosInvalidosCrearDistribucionViandasException, DatosInvalidosCrearDonacionDineroException, DatosInvalidosCrearDonacionViandaException, DatosInvalidosCrearHCHException, DatosInvalidosCrearRPESVException {
        if (data.length != 8) {
            log.log(Level.SEVERE, i18nService.getMessage("migrador.TransformacionDeDatos.procesarColaborador_err_fila_incompleta", Arrays.toString(data)));
            throw new FilaDeDatosIncompletaException();
        }
        
        String tipoDocStr = data[0];
        String numDoc = data[1];
        String nombre = data[2];
        String apellido = data[3];
        String direcMail = data[4];
        String fechaContribucionStr = data[5];
        String formaContribucionStr = data[6];
        Integer cantColabs = Integer.valueOf(data[7]);

        // Transforma a documento
        Documento.TipoDocumento tipoDoc = conversorTipoDocumento.convertirStrATipoDocumento(tipoDocStr);
        Documento documento = new Documento(tipoDoc, numDoc, null);
        
        // Transforma a eMail
        EMail mail = new EMail(direcMail);
        List<MedioDeContacto> contactos = new ArrayList<>();
        contactos.add(mail);

        // Transforma a fechaContribucion
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime fechaContribucion;
        fechaContribucion = LocalDateTime.parse(fechaContribucionStr, dateFormat);

        // Genera un usuario
        String username = customUserDetailsService.generarUsername(nombre, apellido);
        Usuario usuario = new Usuario(username, username, "ROL_CH");

        // Transforma a colaborador
        ColaboradorHumano colaborador = new ColaboradorHumano(usuario, new PersonaFisica(nombre, apellido, documento, null), null, contactos, new ArrayList<>(), new ArrayList<>(), null); // Los atributos que no estan en el csv los ponemos en null (luego veremos que hacer con eso)

        // Agrega contribuciones a colaborador
        for (Integer i = 0; i < cantColabs; i++) {
            Contribucion contribucion = registrarContribucion(formaContribucionStr, fechaContribucion); // Tengo que guardar al Colaborador como null y asignarlo después de guardar al Colaborador porque sino tengo un problema de persistencia cíclica con las Contribuciones
            colaborador.agregarContribucion(contribucion);  // Llamo directamente al método del colaborador, porque se va a guardar en el Migrador
        }

        return colaborador;
    }    

    public void confirmarTransformation() {
        log.log(Level.INFO, i18nService.getMessage("migrador.TransformacionDeDatos.confirmarTransformation_info"));
    }

    public List<ColaboradorHumano> transform(List<String[]> data) throws DatosInvalidosCrearCargaOfertaException, DatosInvalidosCrearDistribucionViandasException, DatosInvalidosCrearDonacionDineroException, DatosInvalidosCrearDonacionViandaException, DatosInvalidosCrearHCHException, FilaDeDatosIncompletaException, DatosInvalidosCrearRPESVException {
        Map<String, ColaboradorHumano> colaboradoresProcesados = new HashMap<>();

        for (String[] dataColaborador : data) {
            ColaboradorHumano colaborador = procesarColaborador(dataColaborador);
            
            String clave = colaborador.getDocumento().getTipo().name()
                + "-" + colaborador.getDocumento().getNumero();

            // Junta los repetidos
            if (colaboradoresProcesados.containsKey(clave)) {
                ColaboradorHumano colaboradorExistente = colaboradoresProcesados.get(clave);
                colaboradorExistente.getContribuciones().addAll(colaborador.getContribuciones());
            } else {
                colaboradoresProcesados.put(clave, colaborador);
            }
        }

        confirmarTransformation();

        return new ArrayList<>(colaboradoresProcesados.values());
    }
}
