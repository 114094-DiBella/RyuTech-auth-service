-- Contraseña 'password' encriptada con BCrypt
INSERT INTO users (id, email, first_name, last_name, password)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'admin@example.com', 'Admin', 'User', '12345678');

-- Otro usuario para pruebas
INSERT INTO users (id, email, first_name, last_name, password)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'usuario@example.com', 'Usuario', 'Normal', '$2a$10$rqciY/eYlWHFg.G0lNyaH.Z.k5dMJZtl5UbwTXoQbPz.hQjRTOOUu');