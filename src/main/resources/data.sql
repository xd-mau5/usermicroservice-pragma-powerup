INSERT INTO `person` (
    `id`,
    `address`,
    `dni_number`,
    `birth_date`,
    `id_dni_type`,
    `mail`,
    `name`,
    `password`,
    `phone`,
    `surname`,
    `token_password`
  )
VALUES
  (
    '1',
    'st 123 # 456',
    '123',
    '25-12-2000',
    '1',
    'email@some.com',
    'Name',
    '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S',
    '1234567890',
    'Surname',
    NULL
  );

INSERT INTO `person` (`id`, `address`, `birth_date`, `dni_number`, `id_dni_type`, `mail`, `name`, `password`, `phone`, `surname`, `token_password`) VALUES (2, 'Calle 27A', '10-08-1998', 1067961817, '1', 'omar@mau5.co', 'Omar', '$2a$10$kWzHbkn7FE8qdTmrD6qrx.VDMS9sq3vMHbKZoUeWS3aQAIxyHkRmW', '+573126791586', 'Izquierdo', NULL);



INSERT INTO `role` (`id`, `description`, `name`) VALUES ('1', 'ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('2', 'ROLE_OWNER', 'ROLE_OWNER');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('3', 'ROLE_EMPLOYEE', 'ROLE_EMPLOYEE');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('4', 'ROLE_USER', 'ROLE_USER');

INSERT INTO `user` (`id_person`, `id_role`) VALUES ('1', '1');