package lucas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class FacturiTest {

    @Test
    void testFacturaConstructorAndGetters() {
        Factura factura = new Factura("John Doe", "1234567890123", "0723456789", "Laptop", 2000.0, 1000.0, "25/01/2025", false);
        
        assertEquals("John Doe", factura.getNumeClient());
        assertEquals("1234567890123", factura.getCnp());
        assertEquals("0723456789", factura.getTelefon());
        assertEquals("Laptop", factura.getProduse());
        assertEquals(2000.0, factura.getSumaTotala());
        assertEquals(1000.0, factura.getSumaPlatita());
        assertEquals("25/01/2025", factura.getData());
        assertFalse(factura.isEstePlatita());
    }

    @Test
    void testPlateste() {
        Factura factura = new Factura("John Doe", "1234567890123", "0723456789", "Laptop", 2000.0, 1000.0, "25/01/2025", false);
        factura.plateste(500.0);
        assertEquals(1500.0, factura.getSumaPlatita());
    }

    @Test
    void testEstePlatita() {
        Factura factura = new Factura("John Doe", "1234567890123", "0723456789", "Laptop", 2000.0, 1000.0, "25/01/2025", false);
        assertFalse(factura.estePlatita());
        
        factura.plateste(1000.0);
        assertTrue(factura.estePlatita());
    }

    @Test
    void testToString() {
        Factura factura = new Factura("John Doe", "1234567890123", "0723456789", "Laptop", 2000.0, 1000.0, "25/01/2025", false);
        String expected = "John Doe,1234567890123,0723456789,Laptop,2000.0,1000.0,25/01/2025,false";
        assertEquals(expected, factura.toString());
    }
    
    @Test
    void testFormatDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(2025 - 1900, 0, 25);
        
        String formattedDate = sdf.format(date);
        
        assertEquals("2025-01-25", formattedDate);
    }

}
