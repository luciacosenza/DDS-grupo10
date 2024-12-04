# Flujo de Ejecución para Viandas

1. **Donación de una Vianda**
   - Un Colaborador (Humano) decide realizar una Donación de Vianda:
      1. Colaborador (Humano) solicita una Apertura de la Heladera (con Motivo de ingresar Donación).
      2. El Colaborador intenta una Apertura de la Heladera.
      3. Se agrega la Vianda a la Heladera.
         1. Se asigna la Heladera a la Vianda.
         2. Se marca la Vianda como entregada.
         3. Se actualiza la fecha de donación de la Vianda.

2. **Distribución de Viandas**
   - Si un Colaborador (Humano) decide realizar una Distribución de Viandas:
      1. Colaborador (Humano) solicita una Apertura de la Heladera (con Motivo de retirar lote de Distribución).
      2. El Colaborador intenta una Apertura de la Heladera.
      3. El Colaborador retira una Vianda de la Heladera (esto se repite por cada Vianda del lote a retirar).
         1. El Colaborador obtiene la Vianda.
         2. Se quita la Heladera de la Vianda.
         3. Se marca la Vianda como no entregada.
      4. El Colaborador agrega una Vianda a la Heladera (esto se repite por cada Vianda del lote a retirar).
         1. El Colaborador deposita la Vianda.
         2. Se asigna la Heladera de la Vianda.
         3. Se marca la Vianda como entregada.
