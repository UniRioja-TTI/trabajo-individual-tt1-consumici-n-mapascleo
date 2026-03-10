package servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactoSimServiceImplTest {

    private ContactoSimServiceImpl servicio;

    @BeforeEach
    void setUp() {
        servicio = new ContactoSimServiceImpl();
    }

    @Test
    void testServicioInstancia() {
        assertNotNull(servicio);
    }

    @Test
    void testEntidadesInventadasCargadas() {
        assertFalse(servicio.getEntities().isEmpty());
        assertEquals(3, servicio.getEntities().size());
    }

    @Test
    void testValidacionSiempreActiva() {
        assertTrue(servicio.isValidEntityId());
    }
}