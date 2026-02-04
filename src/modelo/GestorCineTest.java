package modelo;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class GestorCineTest {

	private static GestorCine gestorCine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gestorCine = new GestorCine();
        gestorCine.controlador.iniciarConexion();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		   // Cerrar conexión con BD
        if (gestorCine != null && gestorCine.controlador != null) {
            gestorCine.controlador.cerrarConexion();
        }
	}

    @Test
    public void testDniExistente() {

    	boolean resultado = gestorCine.dniOEmailYaRegistrados("12345678A");
        assertTrue("Debería detectar DNI ya registrado", resultado);
    }

    @Test
    public void testEmailExistente() {
    	
        boolean resultado = gestorCine.dniOEmailYaRegistrados("laura.gonzalez@email.com");
        assertTrue("Debería detectar email ya registrado", resultado);
    }
    
    @Test
    public void testDniNoExistente() {
    	
        boolean resultado = gestorCine.dniOEmailYaRegistrados("99999999Z");
        assertFalse("No debería estar registrado", resultado);
    }

    @Test
    public void testEmailNoExistente() {

    	boolean resultado = gestorCine.dniOEmailYaRegistrados("noexiste@gmail.com");
        assertFalse("No debería estar registrado", resultado);
    }


}
