package com.tp_anual_dds.migrador;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class ExtraccionCSV extends ExtraccionDeDatos {
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

/*public ArrayList<String[]> extract(String csv) {
        String linea;
        String separador = ",";
        ArrayList<String[]> dataColaboradores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            while ((linea = reader.readLine()) != null) {
                String[] dataColaborador = linea.split(separador);
                dataColaboradores.add(dataColaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();    // TODO Hay que cambiar esto
        }

        return dataColaboradores;
    }*/

/* public ArrayList<String[]> extract(String csv) {
        ArrayList<String[]> dataColaboradores = new ArrayList<>();

        // Verificar si el archivo existe y se puede leer
        File file = new File(csv);
        if (!file.exists()) {
            System.err.println("El archivo no existe: " + csv);
            return dataColaboradores;
        }
        if (!file.canRead()) {
            System.err.println("No se puede leer el archivo: " + csv);
            return dataColaboradores;
        }

        try (CSVReader reader = new CSVReader(new FileReader(csv))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                dataColaboradores.add(nextLine);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            e.printStackTrace();  // Imprime el stack trace para más detalles.
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();  // Imprime el stack trace para más detalles.
        }

        return dataColaboradores;
    }*/


}
