-- ===========================================
-- TABLA: vendedores
-- ===========================================
CREATE TABLE vendedores (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    nombre VARCHAR(120),
    fecha_nacimiento DATE,
    direccion VARCHAR(200),
    ciudad VARCHAR(100),
    departamento VARCHAR(100),
    telefono VARCHAR(20),
    cupo_total NUMERIC(12,2),
    cupo_disponible NUMERIC(12,2),
    porcentaje_comision NUMERIC(5,2),
    ganancias_acumuladas NUMERIC(12,2),
    estado VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===========================================
-- TABLA: Clientes
-- ===========================================
CREATE TABLE Clientes (
    id SERIAL PRIMARY KEY,
    vendedor_id INT NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    nombre VARCHAR(120),
    fecha_nacimiento DATE,
    direccion VARCHAR(200),
    ciudad VARCHAR(100),
    departamento VARCHAR(100),
    telefono VARCHAR(20),
    estado VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vendedor_id) REFERENCES vendedores(id)
);

-- ===========================================
-- TABLA: administradores
-- ===========================================
CREATE TABLE administradores (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    nombre VARCHAR(120),
    fecha_nacimiento DATE,
    direccion VARCHAR(200),
    ciudad VARCHAR(100),
    departamento VARCHAR(100),
    telefono VARCHAR(20),
    estado VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===========================================
-- TABLA: prestamos
-- ===========================================
CREATE TABLE prestamos (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    vendedor_id INT,
    producto_nombre VARCHAR(150) NOT NULL,
    monto_producto NUMERIC(12,2),
    monto_prestamo NUMERIC(12,2),
    num_cuotas INT,
    periodo_tipo VARCHAR(20),
    cuota_valor NUMERIC(12,2),
    estado VARCHAR(30),
    fecha_inicio DATE,
    fecha_final DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Clientes(id),
    FOREIGN KEY (vendedor_id) REFERENCES vendedores(id)
);

-- ===========================================
-- TABLA: pagos
-- ===========================================
CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    prestamo_id INT,
    metodo VARCHAR(20),
    tipo_pago VARCHAR(20),
    monto NUMERIC(12,2),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    referencia_externa VARCHAR(100),
    registrado_por INT,
    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id),
    FOREIGN KEY (registrado_por) REFERENCES Clientes(id)
);

-- ===========================================
-- TABLA: solicitudes
-- ===========================================
CREATE TABLE solicitudes (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(30),
    descripcion TEXT,
    vendedor_id INT,
    usuario_id INT,
    admin_aprobador_id INT,
    monto NUMERIC(12,2),
    metodo VARCHAR(20),
    estado VARCHAR(30),
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion TIMESTAMP NULL,
    FOREIGN KEY (vendedor_id) REFERENCES vendedores(id),
    FOREIGN KEY (usuario_id) REFERENCES Clientes(id),
    FOREIGN KEY (admin_aprobador_id) REFERENCES administradores(id)
);
