package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//import au.com.bytecode.opencsv.CSVReader;

public class ExtraccionCSV extends ExtraccionDeDatos {
    /*
    @Override
    public ArrayList<String[]> extract(String csv) {
        
        ArrayList<String[]> dataColaboradores = new ArrayList<>();
        
        try  {
            CSVReader reader = new CSVReader(new FileReader(csv));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                dataColaboradores.add(nextLine);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        return dataColaboradores;
    }
    */

    @Override
    public ArrayList<String[]> extract(String csv) {
        String linea;
        String separador = ",";
        ArrayList<String[]> dataColaboradores = new ArrayList<>();

        try {
            FileReader reader = new FileReader(csv);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        
        try (BufferedReader buffer = new BufferedReader(new FileReader(csv))) {
            while ((linea = buffer.readLine()) != null) {
                String[] dataColaborador = linea.split(separador);
                dataColaboradores.add(dataColaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();    // TODO Hay que cambiar esto
        }

        return dataColaboradores;
    }


}
