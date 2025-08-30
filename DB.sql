CREATE SCHEMA `sgm` DEFAULT CHARACTER SET utf8mb4 ;

CREATE TABLE `sgm`.`usuariosistema` (
  `id` INT NOT NULL,
  `perfil` CHAR(20) NULL,
  `contrasenia` CHAR(20) NULL,
  PRIMARY KEY (`id`));