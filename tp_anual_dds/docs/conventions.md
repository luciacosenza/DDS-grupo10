# Decisiones y Convenciones Internas de Código

## Nombres de Parámetros en los Constructores

En los Constructores, decidimos ponerle a los parámetros **`vAtributo`** haciendo referencia a "Valor Atributo", para que queden nombres distintos.

## Uso de Modificador *final*

En los atributos de las diferentes clases, usamos *final* cuando estamos seguros de que el valor de ese atributo se va a mantener constante.

### No usamos *final*

- Cuando creemos que es un atributo que puede cambiar de valor, como:
  - Nombres
  - Apellidos
  - Documentos (a chequear)
  - Ubicaciones / Areas (no sus atributos \*)
  - Medios de contacto
  - Menores a cargo
  - Atributos de Vianda
  - Atributos de Oferta
  - Atributos de Suscripción (salvo Colaborador y Heladera)
  - Etc.
- Cuando los atributos se definen en una clase abstracta (por cómo manejamos los constructores, estos sólo se implementan en las clases concretas)

> \* Los objetos de tipo Ubicacion y Area, usados por Colaborador (domicilio), PersonaEnSituacionVulnerable (domicilio), Tecnico (areaDeCobertura), etc. son modificados reemplazándolos por otro objeto del mismo tipo. Por eso es que estos no son final, pero sus atributos (latitud, longitud, domicilio, x, y, etc.) sí.

## Tipo de Datos para Documentos

Para todo lo referido a números de documento (tanto de **DNI**, **Pasaporte**, **CUIL**, etc.) decidimos usar **Strings** (también para teléfonos y otros tipos).

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

A setters y getters, decidimos llamarlos, en su mayoría, **`getX`** y **`setX`**.

- Sólo no se llamarán así cuando la expresividad tenga un peso significativo.

## Nombres de Interfaces y Clases al usar Patrones de Diseño

Las Interfaces y Clases necesarias para utilizar algún Patron de Diseño, cuando sea necesario, llevarán (en general) el nombre de la clase con la que se relacionan + el rol que tomen en el uso de ese Patrón.

## Utilización de Patrones

### Diseño

- **FACTORY METHOD**
  - En **Contribucion**
    - **Creator**
      - ContribucionCreator (*Interface*)
      - CargaOfertaCreator (*Class*)
      - DistribucionViandasCreator (*Class*)
      - DonacionDineroCreator (*Class*)
      - DonacionViandaCreator (*Class*)
      - HacerseCargoDeHeladeraCreator (*Class*)
      - RegistroDePersonaEnSituacionVulnerableCreator (*Class*)
    - **Product**
      - Contribucion (*Abstract Class*)
      - CargaOferta (*Class*)
      - DistribucionViandas (*Class*)
      - DonacionDinero (*Class*)
      - DonacionVianda (*Class*)
      - HacerseCargoDeHeladera (*Class*)
      - RegistroDePersonaEnSituacionVulnerable (*Class*)
  - En **Tarjeta**:
    - **Creator**
      - TarjetaCreator (*Interface*)
      - TarjetaColaboradorCreator (*Class*)
      - TarjetaPersonaEnSituacionVulnerable (*Class*)
    - **Product**
      - Tarjeta (*Abstract Class*)
      - TarjetaColaborador (*Class*)
      - TarjetaPersonaEnSituacionVulnerable (*Class*)

- **NULL OBJECT**
  - En **Heladera**
    - **Null Object**
      - HeladeraNula (*Class*)
    - **Real Object**
      - HeladeraActiva (*Class*)
  - En **TarjetaColaborador**
    - **Null Object**
      - TarjetaColaboradorNula (*Class*)
    - **Real Object**
      - TarjetaColaboradorActiva (*Class*)
  - En **EstadoSolicitud**
    - **Null Object**
      - EstadoNoAplica (*Class*)
    - **Real Object**
      - EstadoPosible (*Class*)
      - EstadoPendiente (*Class*)
      - EstadoRealizada (*Class*)
      - EstadoExpirada (*Class*)
  - En **PermisoApertura**
    - **Null Object**
      - PermisoAperturaNulo (*Class*)
    - **Real Object**
      - PermisoAperturaActivo (*Class*)
  - En **EnvioDeDatos**
    - **Null Object**
      - EnvioNulo (*Class*)
    - **Real Object**
      - EnvioEMail (*Class*)

- **OBSERVER**
  - En **Heladera**
    - **Observer**
      - HeladeraObserver (*Interface*)
      - Heladera (*Abstract Class*)
      - HeladeraActiva (*Class*)
      - HeladeraNula (*Class*)
    - **Subject**
      - SensorSubject (*Interface*)
      - Sensor (*Abstract Class*)
      - SensorMovimiento (*Class*)
      - SensorTemperatura (*Class*)
  - En **Heladera**
    - **Observer**
      - ColaboradorHumanoObserver (*Interface*) (A nivel conceptual)
      - ColaboradorHumano (*Class*)
    - **Subject**
      - HeladeraSubject(*Interface*) (A nivel conceptual)
      - Heladera (*Abstract Class*)
      - HeladeraActiva (*Class*)
      - HeladeraNula (*Class*)

- **STATE**
  - En **TarjetaColaborador**
    - **Context**
      - TarjetaColaborador (*Abstract Class*)
      - TarjetaColaboradorActiva (*Class*)
      - TarjetaColaboradorNula (*Class*)
    - **State**
      - EstadoSolicitudApertura (*Interface*)
      - EstadoPosible (*Class*)
      - EstadoPendiente (*Class*)
      - EstadoRealizada (*Class*)
      - EstadoExpirada (*Class*)
      - EstadoNoAplica (*Class*)

- **STRATEGY**
  - En **Migrador**
    - **Context**
      - Migrador (*Class*)
    - **Strategy**
      - En **ExtraccionDeDatos**:
        - ExtraccionDeDatosStrategy (*Interface*)
        - ExtraccionDeDatos (*Abstract Class*)
        - ExtraccionCSV (*Class*)
      - En **EnvioDeDatos**:
        - EnvioDeDatosStrategy (*Interface*)
        - EnvioDeDatos (*Abstract Class*)
        - EnvioEMail (*Class*)
