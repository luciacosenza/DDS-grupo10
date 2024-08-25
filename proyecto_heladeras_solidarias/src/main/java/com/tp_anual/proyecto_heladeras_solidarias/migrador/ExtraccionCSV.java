package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

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
            System.out.println("Error de I/O al leer el archivo CSV");  // TODO Hay que cambiar esto
        } catch (CsvException e) {
            System.out.println("Error al procesar el archivo CSV"); // TODO Hay que cambiar esto
        }

        return dataColaboradores;
    }
}
