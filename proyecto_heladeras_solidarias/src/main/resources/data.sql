-- Insertar documentos
INSERT INTO documento (tipo, numero, sexo) VALUES
    ('DNI', '12345678', 'MASCULINO'),
    ('DNI', '23456789', 'FEMENINO'),
    ('DNI', '34567890', 'MASCULINO'),
    ('DNI', '45678901', 'FEMENINO'),
    ('DNI', '56789012', 'MASCULINO'),
    ('DNI', '67890123', 'FEMENINO'),
    ('DNI', '78901234', 'MASCULINO'),
    ('DNI', '89012345', 'FEMENINO'),
    ('DNI', '90123456', 'MASCULINO'),
    ('DNI', '01234567', 'FEMENINO');

-- Insertar ubicaciones
INSERT INTO ubicacion (latitud, longitud, direccion, codigo_postal, ciudad, pais) VALUES
    (-34.6037, -58.3816, 'Av. de Mayo 123', 'C1002', 'Buenos Aires', 'Argentina'),
    (-31.4201, -64.1888, 'Av. Colón 456', 'X5000', 'Córdoba', 'Argentina'),
    (-32.9476, -60.6393, 'Av. San Martín 789', 'S3000', 'Santa Fe', 'Argentina'),
    (-34.9215, -57.9549, 'Calle 10 101', 'B1900', 'La Plata', 'Argentina'),
    (-37.8125, -57.5586, 'Calle 5 202', 'B8000', 'Mar del Plata', 'Argentina'),
    (-37.4754, -59.0246, 'Av. Rivadavia 303', 'C9000', 'Bahía Blanca', 'Argentina'),
    (-38.4189, -57.5513, 'Av. Luro 404', 'B8000', 'Necochea', 'Argentina'),
    (-31.7310, -60.5063, 'Calle 15 505', 'E3100', 'Paraná', 'Argentina'),
    (-40.8489, -63.9421, 'Calle 20 606', 'R9400', 'Neuquén', 'Argentina'),
    (-38.9513, -68.0593, 'Calle 25 707', 'Q8370', 'Mendoza', 'Argentina');

-- Insertar personas físicas
INSERT INTO persona_fisica (id, nombre, apellido, documento, fecha_nacimiento) VALUES
    (0, 'Juan', 'Pérez', (SELECT id FROM documento WHERE numero = '12345678'), '1990-01-01 00:00:00'),
    (1, 'Ana', 'Gómez', (SELECT id FROM documento WHERE numero = '23456789'), '1992-02-02 00:00:00'),
    (2, 'Carlos', 'Fernández', (SELECT id FROM documento WHERE numero = '34567890'), '1988-03-03 00:00:00'),
    (3, 'María', 'López', (SELECT id FROM documento WHERE numero = '45678901'), '1985-04-04 00:00:00'),
    (4,'Pedro', 'Martínez', (SELECT id FROM documento WHERE numero = '56789012'), '1995-05-05 00:00:00'),
    (5,'Lucía', 'Ramírez', (SELECT id FROM documento WHERE numero = '67890123'), '1998-06-06 00:00:00'),
    (6,'Javier', 'Torres', (SELECT id FROM documento WHERE numero = '78901234'), '1991-07-07 00:00:00'),
    (7,'Sofía', 'Cruz', (SELECT id FROM documento WHERE numero = '89012345'), '1989-08-08 00:00:00'),
    (8,'Diego', 'Reyes', (SELECT id FROM documento WHERE numero = '90123456'), '1993-09-09 00:00:00'),
    (9,'Valentina', 'Mendoza', (SELECT id FROM documento WHERE numero = '01234567'), '1994-10-10 00:00:00');

-- Insertar colaboradores
INSERT INTO colaborador (persona, domicilio, puntos, tipo_colaborador) VALUES
    ((SELECT id FROM persona_fisica WHERE nombre = 'Juan' AND apellido = 'Pérez'), (SELECT id FROM ubicacion WHERE direccion = 'Av. de Mayo 123'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Ana' AND apellido = 'Gómez'), (SELECT id FROM ubicacion WHERE direccion = 'Av. Colón 456'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Carlos' AND apellido = 'Fernández'), (SELECT id FROM ubicacion WHERE direccion = 'Av. San Martín 789'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'María' AND apellido = 'López'), (SELECT id FROM ubicacion WHERE direccion = 'Calle 10 101'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Pedro' AND apellido = 'Martínez'), (SELECT id FROM ubicacion WHERE direccion = 'Calle 5 202'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Lucía' AND apellido = 'Ramírez'), (SELECT id FROM ubicacion WHERE direccion = 'Av. Rivadavia 303'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Javier' AND apellido = 'Torres'), (SELECT id FROM ubicacion WHERE direccion = 'Av. Luro 404'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Sofía' AND apellido = 'Cruz'), (SELECT id FROM ubicacion WHERE direccion = 'Calle 15 505'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Diego' AND apellido = 'Reyes'), (SELECT id FROM ubicacion WHERE direccion = 'Calle 20 606'), 0.0, 'Humano'),
    ((SELECT id FROM persona_fisica WHERE nombre = 'Valentina' AND apellido = 'Mendoza'), (SELECT id FROM ubicacion WHERE direccion = 'Calle 25 707'), 0.0, 'Humano');

-- Insertar medios de contacto
INSERT INTO email (id, direccion_correo) VALUES
    (0, 'juan.perez@gmail.com'),
    (1, 'ana.gomez@gmail.com'),
    (2, 'carlos.fernandez@gmail.com'),
    (3, 'maria.lopez@gmail.com'),
    (4, 'pedro.martinez@gmail.com'),
    (5, 'lucia.ramirez@gmail.com'),
    (6, 'javier.torres@gmail.com'),
    (7, 'sofia.cruz@gmail.com'),
    (8, 'diego.reyes@gmail.com'),
    (9, 'valentina.mendoza@gmail.com');

-- Asociar medios de contacto a colaboradores
UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Juan' AND apellido = 'Pérez'))
WHERE direccion_correo = 'juan.perez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Ana' AND apellido = 'Gómez'))
WHERE direccion_correo = 'ana.gomez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Carlos' AND apellido = 'Fernández'))
WHERE direccion_correo = 'carlos.fernandez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'María' AND apellido = 'López'))
WHERE direccion_correo = 'maria.lopez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Pedro' AND apellido = 'Martínez'))
WHERE direccion_correo = 'pedro.martinez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Lucía' AND apellido = 'Ramírez'))
WHERE direccion_correo = 'lucia.ramirez@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Javier' AND apellido = 'Torres'))
WHERE direccion_correo = 'javier.torres@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Sofía' AND apellido = 'Cruz'))
WHERE direccion_correo = 'sofia.cruz@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Diego' AND apellido = 'Reyes'))
WHERE direccion_correo = 'diego.reyes@gmail.com';

UPDATE email
SET colaborador = (SELECT id FROM colaborador WHERE persona = (SELECT id FROM persona_fisica WHERE nombre = 'Valentina' AND apellido = 'Mendoza'))
WHERE direccion_correo = 'valentina.mendoza@gmail.com';