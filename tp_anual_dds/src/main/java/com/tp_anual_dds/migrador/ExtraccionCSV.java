package com.tp_anual_dds.migrador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExtraccionCSV extends ExtraccionDeDatos {
    @Override
    public ArrayList<String[]> extract(String csv) {
        String linea;
        String separador = ",";
        ArrayList<String[]> dataColaboradores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            while ((linea = reader.readLine()) != null) {
                String[] dataColaborador = linea.split(separador);
                dataColaboradores.add(dataColaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();    // Hay que cambiar esto (TODO)
        }

        return dataColaboradores;
    }    
}
