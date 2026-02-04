package controlador;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class ImprimirTest {
	
	private static Imprimir imprimir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		imprimir = new Imprimir();
	}

	@Test
	public void testImprimirFechas() {
		ArrayList<String> fechas = new ArrayList<>();
        fechas.add("26-02-2026");
        fechas.add("26-02-2026");
        fechas.add("27-02-2026");
        
        ArrayList<String> resultado = imprimir.imprimirFechas(fechas, "test");
        
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("26-02-2026"));
        assertTrue(resultado.contains("27-02-2026"));
	}
	
    @Test
    public void testImprimirFechasUnica() {
        ArrayList<String> fechas = new ArrayList<>();
        fechas.add("26-02-2026");
        
        ArrayList<String> resultado = imprimir.imprimirFechas(fechas, "test2");
        
        assertEquals(1, resultado.size());
        assertEquals("26-02-2026", resultado.get(0));
    }
}
