# Flujo de Ejecución para Heladeras

1. **Instalación de una nueva Heladera**
   1. Un Colaborador (Jurídico) decide Hacerse Cargo de una Heladera.
   2. Se levantan los Sensores correspondientes y el Gestor de Aperturas de la Heladera.
   3. El Colaborador da de alta a la Heladera en el Sistema.

2. **Agregación de Vianda**
   - Si un Colaborador (Humano) decide agregar una Vianda a la Heladera:
      1. Colaborador (Humano) solicita una Apertura de la Heladera (con Motivo de ingresar Donación / lote de Distribución).
      2. El Colaborador intenta una Apertura de la Heladera.
      3. La Heladera valida no estar llena.
         - Si la Heladera está llena:
            - Se avisa al Colaborador que no puede agregar la Vianda.
         - Si la Heladera no está llena:
            1. El Colaborador agrega la Vianda a la Heladera.
            2. Se verifican condiciones de Suscripción.

3. **Retiro de Vianda**
   - Si un Colaborador (Humano) decide retirar una Vianda a la Heladera:
      1. Colaborador (Humano) solicita una Apertura de la Heladera (con Motivo de retirar lote de Distribución).
      2. El Colaborador intenta una Apertura de la Heladera.
      3. La Heladera valida no estar vacía.
         - Si la Heladera está vacía:
            - Se avisa al Colaborador que no puede retirar una Vianda.
         - Si la Heladera no está vacía:
            1. El Colaborador retira una Vianda de la Heladera.
            2. Se verifican Condiciones de Suscripción.

4. **Verificación de Condiciones de Suscripción**
   - Si un Colaborador (Humano) agrega o retira una Vianda o se produce un Incidente.
      - Se chequea si alguna de las Condiciones de alguna Suscripción se cumple:
         - Para las que se cumplen (si hay alguna):
            - La Heladera envía un Mensaje de Estado con Condición que se cumplió y Medio de Contacto elegido de Suscripción.

5. **Notificación de Temperatura**
   - La Heladera recibe la temperatura sensada por el Sensor de Temperatura (cada 5 minutos).
      1. La Heladera actualiza su temperatura actual.
      2. La Heladera verifica la temperatura actual.
         - Si la temperatura actual no está entre los parámetros correctos:
            - La Heladera produce una Alerta de tipo Temperatura.
               1. La Heladera se marca como inactiva.
               2. Se verifican Condiciones de Suscripción (para avisar a los Colaboradores suscriptos del desperfecto).
               3. La Heladera envía un Mensaje de Incidente con la Alerta.
         - Si la temperatura actual está entre los parámetros correctos:
            - Se continúa el Flujo de Ejecución Normal.

6. **Notificación de Fraude**
   - La Heladera recibe si hay movimiento, sensado por el Sensor de Movimiento (cada 5 minutos, enviado sólo si lo hubo).
      - La Heladera produce una Alerta de tipo Fraude.
         1. La Heladera se marca como inactiva.
         2. Se verifican Condiciones de Suscripción (para avisar a los Colaboradores suscriptos del desperfecto).
         3. La Heladera envía un Mensaje de Incidente con la Alerta.

7. **Notificación de Falla de Conexión**
   - La Heladera recibe el reporte de una Falla de Conexión.
      - La Heladera produce una Falla de Conexión.
         1. La Heladera se marca como inactiva.
         2. Se verifican Condiciones de Suscripción (para avisar a los Colaboradores suscriptos del desperfecto).
         3. La Heladera envía un Mensaje de Incidente con la Falla de Conexión.

8. **Notificación de Falla Técnica**
   - La Heladera recibe el reporte de una Falla Técnica.
      - La Heladera produce una Falla Técnica.
         1. La Heladera se marca como inactiva.
         2. Se verifican Condiciones de Suscripción (para avisar a los Colaboradores suscriptos del desperfecto).
         3. La Heladera envía un Mensaje de Incidente con la Falla Técnica.
