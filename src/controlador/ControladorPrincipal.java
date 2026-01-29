package controlador;

import vista.VistaConsola;
import modelo.Cliente;

public class ControladorPrincipal {

    private VistaConsola vista;
    private ControladorCliente ctrlCliente;
    private ControladorVentas ctrlVentas;

    public ControladorPrincipal() {
        this.vista = new VistaConsola();
        this.ctrlCliente = new ControladorCliente(vista);
        this.ctrlVentas = new ControladorVentas(vista);
    }

    // ---------------------------------------------------------
    // PUNTO DE ARRANQUE
    // ---------------------------------------------------------
    public void iniciarAplicacion() {
        boolean salir = false;

        while (!salir) {
            vista.mostrarMensaje("\n*********************************");
            vista.mostrarMensaje("     🎥 CINE CIUDAD - INICIO 🎥   ");
            vista.mostrarMensaje("*********************************");
            vista.mostrarMensaje("1. Iniciar Sesión (Login)");
            vista.mostrarMensaje("2. Registrarse (Crear cuenta)");
            vista.mostrarMensaje("3. Salir");

            int opcion = vista.pedirInt("Elige una opción");

            switch (opcion) {
                case 1 -> procesarLogin();
                case 2 -> procesarRegistro();
                case 3 -> {
                    vista.mostrarMensaje("¡Hasta pronto! 👋");
                    salir = true;
                }
                default -> vista.mostrarMensaje("❌ Opción no válida.");
            }
        }
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    private void procesarLogin() {
        vista.mostrarMensaje("\n--- 🔐 INICIAR SESIÓN ---");

        // ADMIN "PUERTA TRASERA"
        String dni = vista.pedirDato("DNI / Usuario");
        String pass = vista.pedirDato("Contraseña");

        if (dni.equalsIgnoreCase("admin") && pass.equals("1234")) {
            vista.mostrarMensaje("✅ Bienvenido, Administrador.");
            return;
        }

        // LOGIN NORMAL
        boolean ok = ctrlCliente.iniciarSesion();

        if (ok) {
            Cliente clienteLogueado = ctrlCliente.getClienteLogueado();
            vista.mostrarMensaje(" Bienvenido de nuevo, " + clienteLogueado.getNombre());
            mostrarMenuUsuario(clienteLogueado);
        } else {
            vista.mostrarMensaje(" Error: Usuario o contraseña incorrectos.");
        }
    }

    // ---------------------------------------------------------
    // MENÚ DEL USUARIO LOGUEADO
    // ---------------------------------------------------------
    private void mostrarMenuUsuario(Cliente cliente) {
        boolean salir = false;

        while (!salir) {
            vista.mostrarMensaje("\n--- MENÚ PRINCIPAL ---");
            vista.mostrarMensaje("1. Comprar entradas");
            vista.mostrarMensaje("2. Cerrar sesión");

            int opcion = vista.pedirInt("Elige una opción");

            switch (opcion) {
                case 1 -> ctrlVentas.iniciarVenta(cliente);
                case 2 -> {
                    vista.mostrarMensaje("🔒 Sesión cerrada.");
                    salir = true;
                }
                default -> vista.mostrarMensaje("❌ Opción no válida.");
            }
        }
    }

    // ---------------------------------------------------------
    // REGISTRO
    // ---------------------------------------------------------
    private void procesarRegistro() {
        vista.mostrarMensaje("\n--- 📝 CREAR NUEVA CUENTA ---");
        ctrlCliente.registrarNuevoCliente();
    }
}
