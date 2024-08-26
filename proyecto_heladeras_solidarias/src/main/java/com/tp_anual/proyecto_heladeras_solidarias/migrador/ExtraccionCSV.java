package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class ExtraccionCSV extends ExtraccionDeDatos {
    
    @Override
    public ArrayList<String[]> extract(String csv) {
        ArrayList<String[]> dataColaboradores = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csv))) {
            // Leo y descarto la primera línea (header)
            reader.readNext();
            
            String[] linea;
            
            // Leo línea por línea para consumir menos memoria (y soportar archivos grandes)
            while ((linea = reader.readNext()) != null) {
                dataColaboradores.add(linea);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, I18n.getMessage("migrador.ExtraccionCSV.extract_err_io", csv));
            throw new RuntimeException(I18n.getMessage("migrador.ExtraccionCSV.extract_exception_io"));
        } catch (CsvException e) {
            logger.log(Level.SEVERE, I18n.getMessage("migrador.ExtraccionCSV.extract_err_procesamiento_csv", csv));
            throw new RuntimeException(I18n.getMessage("migrador.ExtraccionCSV.extract_exception_procesamiento_csv"));
        }
        
        confirmarExtraction();

        return dataColaboradores;
    }
}
