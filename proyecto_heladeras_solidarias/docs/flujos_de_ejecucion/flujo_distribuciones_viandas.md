# Flujo de Ejecución para Distribuciones de Viandas

1. **Selección de una Carga de Oferta**
   - Un Colaborador (Humano) selecciona una Distribución de Viandas.

2. **Ejecución de una Distribución de Viandas**
   1. Se crea una Tarjeta Solidaria.
   2. Se asigna la Tarjeta Solidaria al Colaborador.
   3. El Colaborador solicita una Apertura de la Heladera origen (con Motivo de retirar lote de Distribución).
   4. El Colaborador intenta una Apertura de la Heladera origen.
   5. El Colaborador retira el lote de Viandas de la Heladera origen.
   6. El Colaborador solicita una Apertura de la Heladera destino (con Motivo de ingresar lote de Distribución).
   7. El Colaborador intenta una Apertura de la Heladera destino.
   8. El Colaborador agrega el lote de Viandas a la Heladera destino.

3. **Confirmación de la Distribución de Viandas**
   - El Colaborador confirma la exitosa ejecución de la Distribución de Viandas.
      - Se suman los puntos correspondientes al Colaborador (multiplicador * cantidad de Viandas distribuidas).
