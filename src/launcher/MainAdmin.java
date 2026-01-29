package launcher;

import controlador.ControladorCine;
import vista.VistaConsola;

public class MainAdmin {
    public static void main(String[] args) {
        // 1. Crear la Vista
        VistaConsola vista = new VistaConsola();
        
        // 2. Crear el Controlador y darle la vista
        ControladorCine controlador = new ControladorCine(vista);
        
        // 3. Arrancar la aplicación
        controlador.iniciar();
    }
}
