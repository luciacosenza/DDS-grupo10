package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class ExtraccionCSV extends ExtraccionDeDatos {

    private final I18nService i18nService;

    public ExtraccionCSV(I18nService vI18nService) {
        i18nService = vI18nService;
    }

    @Override
    public List<String[]> extract(String csv) {
        List<String[]> dataColaboradores = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csv);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader reader = new CSVReader(isr)) {
            
            // Leo y descarto la primera línea (header)
            reader.readNext();
            
            String[] linea;
            
            // Leo línea por línea para consumir menos memoria (y soportar archivos grandes)
            while ((linea = reader.readNext()) != null) {
                dataColaboradores.add(linea);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, i18nService.getMessage("migrador.ExtraccionCSV.extract_err_io", csv));
            throw new RuntimeException(i18nService.getMessage("migrador.ExtraccionCSV.extract_exception_io"));
        } catch (CsvException e) {
            log.log(Level.SEVERE, i18nService.getMessage("migrador.ExtraccionCSV.extract_err_procesamiento_csv", csv));
            throw new RuntimeException(i18nService.getMessage("migrador.ExtraccionCSV.extract_exception_procesamiento_csv"));
        }
        
        confirmarExtraction();

        return dataColaboradores;
    }
}
