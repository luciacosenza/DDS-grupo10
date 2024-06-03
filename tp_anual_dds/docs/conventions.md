# Decisiones y Convenciones Internas de Código

## Nombres de Parámetros en los Constructores
- En los Constructores, decidimos ponerle a los parámetros **`vAtributo`** haciendo referencia a "Valor Atributo", para que queden nombres distintos.

## Tipo de Datos para Documentos
- Para todo lo referido a números de documento (tanto de **DNI**, **Pasaporte**, **CUIL**, etc.) decidimos usar **Strings** (también para teléfonos y otros tipos).

## Orden de Métodos en las Clases
1. **Constructores** (cuando aplique).
2. **Getters y Setters**.
3. **Métodos Simples**:
   - Métodos similares a setters (como sumar y restar a un atributo).
   - Comparadores (de atributos propios con un valor pasado).
   - Modificadores de listas propias (agregar/quitar elemento o limpiar).
4. **Resto de Métodos**:
   - Agrupados por contextos en los que son utilizados.

## Convenciones de Nombres para Getters y Setters
- A setters y getters, decidimos llamarlos, en su mayoría, **`getX`** y **`setX`**.
- Sólo no se llamarán así cuando la expresividad tenga un peso significativo.

## Nombres de Interfaces y Clases al usar Patrones de Diseño
- Las Interfaces y Clases necesarias para utilizar algún Patron de Diseño, cuando sea necesario, llevarán el nombre de la clase con la que se relacionan + el rol que tomen en el uso de ese Patrón.
  - **OBSERVER**
    - **Observer**
      - HeladeraObserver Interface
    - **Subject**
      - SensorSubject Interface
  - **FACTORY**
    - **Creator**
      - ContribucionCreator Interface
      - CargaOfertaCreator Class
      - DistribucionViandasCreator Class
      - DonacionDineroCreator Class
      - DonacionViandaCreator Class
      - HacerseCargoDeHeladeraCreator Class
      - RegistroDePersonaEnSituacionVulnerableCreator Class
  - **STRATEGY**
    - **Strategy**
      - ProtocoloExtraccionStrategy Interface