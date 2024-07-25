# Flujo de Ejecución Principal para Colaboradores

1. **Incorporación de un nuevo Colaborador**
   1. Una Persona decide anotarse como Colaborador de la ONG.
      - La Persona introduce los datos pedidos para convertirse en Colaborador de la ONG.
   2. El Colaborador es dado de alta en el Sistema (registrado en la lista de Colaboradores).

2. **Decisión de realizar una Contribución**
   1. El Colaborador decide colaborar con la causa de la ONG. (1\*)
   2. El Colaborador selecciona una forma de Contribución.
      - Se valida que el Colaborador pueda realizar esa Contribución (según el tipo de Colaborador).
         - Si no puede realizarla:
            1. Se avisa al Colaborador que no puede colaborar de esa forma.
            2. Se le pide que seleccione otra Contribución → (1\*).
         - Si puede realizarla:
            1. Se crea la Contribución.
            2. Se valida que el Colaborador cuente con los datos necesarios para llevar a cabo la Contribución.
               - Si los datos están cargados:
                  - Se piden los datos necesarios al Colaborador.
               - Si los datos están cargados:
                  - Se agrega la Contribución a la lista de Contribuciones pendientes del Colaborador.

3. **Ejecución de la Contribución**
   - El Colaborador lleva a cabo la Contribución.

4. **Confirmación de la Contribución**
   - El Colaborador confirma la exitosa ejecución de la Contribución.
      1. Se agrega la Contribución a la lista de Contribuciones realizadas del Colaborador.
      2. Se remueve la Contribución de la lista de Contribuciones pendientes del Colaborador.

---

## Flujo de Ejecución *Adquisición de Beneficio* para Colaboradores

- **Adquisición de Beneficio**
   1. El Colaborador intenta comprar, con sus puntos, una Oferta (de las publicadas y disponibles).
      1. Se valida que el Colaborador cuente con los puntos suficientes para comprar la Oferta.
         - Si los puntos no son suficientes:
           - Se avisa al Colaborador que no puede comprar esa Oferta.
         - Si los puntos son suficientes:
            1. Se da de baja la Oferta.
            2. Se agrega la Oferta a la lista de beneficios adquiridos del Colaborador.

---

## Flujo de Ejecución *Reporte de Falla Técnica* para Colaboradores

- **Reporte de Falla Técnica**
   1. El Colaborador se encuentra con una Heladera con un desperfecto.
      - > Para Colaboradores Humanos, puede ser cuando se encargaron de una Donación de Vianda o una Distribución de Viandas.
      - > Para Colaboradores Jurídicos, puede ser que se trate de la Heladera de la que se hacen cargo.  
   2. El Colaborador solicita un reporte de Incidente de tipo Falla Técnica, por parte de la Heladera.
