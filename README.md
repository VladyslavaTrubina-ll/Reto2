# Comprar ntradas de Cine

## Descripción General

Este proyecto consiste en una aplicación de consola desarrollada en Java para gestionar la venta de entradas de cine. Hemos utilizado una arquitectura MVC (Modelo-Vista-Controlador) para organizar el código y una base de datos MySQL para guardar toda la información.

El objetivo principal es simular un sistema real donde los clientes pueden registrarse, ver la cartelera, elegir asientos y comprar entradas, aplicándose descuentos automáticos según la cantidad de sesiones.

## Funcionalidades Principales

### Gestión de Usuarios

Registro de nuevos clientes con validación de datos (DNI, email, contraseña)
Sistema de login seguro con encriptación de contraseñas
Validación de email (solo Gmail)
Verificación de datos duplicados en base de datos

### Consulta de Películas y Sesiones

Visualización de películas disponibles con información (duración, género)
Filtrado de películas por fechas disponibles
Consulta de sesiones por película y fecha
Información de salas con capacidad de asientos disponibles
Visualización de precios por sesión

### Sistema de Carrito y Compra

Añadir múltiples entradas al carrito desde diferentes sesiones
Cálculo automático de descuentos (20% para 2 sesiones, 30% para 3 o más)
Resumen detallado de compra antes de confirmar
Cálculo de IVA (21%) al finalizar
Cancelación de reservas si no se confirma la compra

### Gestión de Entradas

Generación de entradas para cada compra
Actualización de aforo de salas
Guardado de tickets en archivo
Detalles completos de cada entrada (película, fecha, hora, sala, precio)

## Estructura del Proyecto

El proyecto sigue la arquitectura MVC dividida en tres capas principales:

### Modelo (`src/modelo/`)

Contiene las clases que representan los objetos del negocio:

`ClienteAcesso.java` Información del cliente registrado
`Pelicula.java` Datos de películas
`Sala.java` Información de salas con capacidad de asientos
`Sesion.java` Sesiones de cine con fecha, hora y precio
`Carrito.java` Carrito de compra con gestión de precios y descuentos
`Ticket.java` Información de entradas compradas
`GestorCine.java` Clase controladora de lógica de negocio
`GestorTicket.java` Gestión de guardado de tickets
`EspectadoresSesion.java` Registro de espectadores por sesión
`dniMailCliente.java` Validación de datos de cliente

### Vista (`src/vista/`)

Interfaz de usuario basada en consola:

`Launcher.java` Punto de entrada de la aplicación con menús interactivos

### Controlador (`src/controlador/`)

Capa intermedia que gestiona la lógica de entrada/salida y acceso a datos:

`ControladorEntradaYSalida.java` Lectura y validación de entrada del usuario (menús, números, textos, DNI)
`ControladorDB.java` Conexión y operaciones con base de datos MySQL
`Imprimir.java` Formateo y presentación de información en consola

## Flujo de Funcionamiento

1. **Conexión a Base de Datos**: Al iniciar, el sistema intenta conectarse a la base de datos MySQL "cine_daw"

2. **Autenticación**:
   El usuario indica si tiene cuenta existente
   Si no tiene cuenta, se realiza el registro con validación de datos
   Si tiene cuenta, procede al login

3. **Selección de Película**:
   Se muestran las películas disponibles (con sesiones futuras)
   El usuario selecciona una película

4. **Selección de Fecha**:
   Se muestran las fechas disponibles para la película seleccionada
   El usuario elige una fecha

5. **Selección de Sesión**:
   Se muestran las sesiones disponibles (horarios y salas)
   El usuario selecciona una sesión y cantidad de entradas
   Se verifica disponibilidad de asientos

6. **Carrito de Compra**:
   Se pueden añadir más películas al carrito
   El sistema calcula descuentos automáticamente

7. **Confirmación y Pago**:
   Se muestra resumen de compra con descuentos e IVA
   Se actualiza la base de datos con los datos de compra
   Se genera y guarda el ticket

## Requisitos Técnicos

### Software Necesario

Java Development Kit (JDK) versión 8 o superior
MySQL Server
Driver JDBC para MySQL (mysqlconnectorjava)

### Base de Datos

Base de datos MySQL: `cine_daw`
Usuario: `root`
Contraseña: (vacía por defecto)
Host: localhost
Puerto: 3306

### Tablas Necesarias

`Cliente` Almacena información de usuarios registrados
`Pelicula` Catálogo de películas
`Sala` Información de salas de proyección
`Sesion` Sesiones de películas (fecha, hora, sala, precio, espectadores)
`Compra` Registro de compras realizadas
`Entrada` Detalles de entradas vendidas

## Cómo Ejecutar

1. Asegurarse de que MySQL está en ejecución
2. Crear la base de datos `cine_daw` con las tablas necesarias
3. Compilar el proyecto en Java
4. Ejecutar la clase `Launcher.java` como aplicación principal
5. Seguir los menús de consola para navegar por la aplicación

## Validaciones Implementadas

DNI: Exactamente 9 caracteres
Email: Solo se aceptan direcciones de Gmail (@gmail.com)
Contraseña: Se encripta con AES en base de datos
Asientos: No se puede comprar más entradas que asientos disponibles
Sesiones: Solo se muestran sesiones futuras
Duplicados: Se valida que DNI y email no estén ya registrados

## Sistema de Descuentos

El sistema aplica descuentos automáticos según la cantidad de sesiones compradas:

1 sesión: 0% descuento
2 sesiones: 20% descuento
3 o más sesiones: 30% descuento

El descuento se aplica sobre el subtotal antes de calcular el IVA.

## Notas de Desarrollo

El proyecto utiliza encriptación AES para almacenar contraseñas de forma segura
Las consultas a base de datos utilizan PreparedStatement para evitar inyección SQL
Los horarios de películas se consideran únicamente si son posteriores a la hora actual
El carrito se vacía automáticamente después de completar o cancelar una compra
Los tickets se guardan en archivos de texto para referencia del cliente

## Autores

Proyecto de equipo de desarrollo para la asignatura de DAW (Desarrollo de Aplicaciones Web)
