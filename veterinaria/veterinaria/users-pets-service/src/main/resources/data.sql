INSERT INTO usuarios (nombre, telefono, email, password, rol)
VALUES
('Administrador', '5555-0001', 'admin@veterinaria.com', '$2a$10$XmN5.V... (o hash BCrypt válido)', 'ADMIN'),
('Dr. Veterinario', '5555-0002', 'vet@veterinaria.com', '$2a$10$XmN5.V...', 'VET'),
('Cliente Dueño', '5555-0003', 'cliente@veterinaria.com', '$2a$10$XmN5.V...', 'CLIENTE');