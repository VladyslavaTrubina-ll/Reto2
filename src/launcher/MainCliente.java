

package launcher;

import controlador.ControladorPrincipal;

public class MainCliente {
    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("        🎬 Bienvenido a Cines Elorrieta 🎬");
        System.out.println("===============================================");
        System.out.println("Regístrate o inicia sesión para continuar.\n");

        ControladorPrincipal app = new ControladorPrincipal();
        app.iniciarAplicacion();
    }
}

