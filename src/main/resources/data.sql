/*Insertar datos de prueba
  Insertando datos de prueba en la base de datos de personas, roles y usuarios
 */
-- Personas
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (1, 'st 123 # 456', '25-12-2000', 123, '1', 'email@some.com', 'Name', '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S', '1234567890', 'Surname', NULL);
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (2, 'Calle 27A', '10-08-1998', 1067961817, '1', 'omar@mau5.co', 'Omar', '$2a$10$kWzHbkn7FE8qdTmrD6qrx.VDMS9sq3vMHbKZoUeWS3aQAIxyHkRmW', '+573126791586', 'Izquierdo', NULL);
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (3, 'Calle Principal 123', '10-09-2005', 1, '1', 'maria.gomez@example.com', 'María', '$2a$10$MW8moW48zmxYhJub5ih5f.Yg.XBj7e1pL7hbuG9WsszHrKf7mhp3i', '+573123456789', 'Gómez', NULL);
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (4, 'Avenida Central 456', '05-12-1992', 2, '1', 'alejandro.lopez@example.com', 'Alejandro', '$2a$10$UsP8CxKEpoa2Z0DKruHEGuW0fgChKU80CE.PJ8Ke0HIKVLL.YrrKW', '+573987654321', 'López', NULL);
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (5, 'Plaza Mayor 789', '18-07-1988', 3, '1', 'isabella.martinez@example.com', 'Isabella', '$2a$10$JwnHB.79mD47G6sDl37GhOeIuPLGPnWg8Bw/EskmISBByu94pG5aK', '+573555555555', 'Martínez', NULL);
INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (17, '123 Main St', '03-06-1933', 1567, '1', 'alex.mason@example.com', 'Alex', '$2a$10$pEoQ2tRxKaDC68VK97B2bO0IVvJicAP6C608Usao2Xp2W7/9q4yEe', '+573123456689', 'Mason', NULL);
-- Roles
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('1', 'ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('2', 'ROLE_OWNER', 'ROLE_OWNER');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('3', 'ROLE_EMPLOYEE', 'ROLE_EMPLOYEE');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('4', 'ROLE_USER', 'ROLE_USER');
-- Usuarios (personas con roles)
INSERT INTO `user` (`id_person`, `id_role`) VALUES ('1', '1');
INSERT INTO `user` (`id_person`, `id_role`) VALUES ('2', '2');