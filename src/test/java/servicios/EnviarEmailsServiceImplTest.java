package servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnviarEmailsServiceImplTest {

    private EnviarEmailsServiceImpl servicio;
    private Logger loggerMock;

    @BeforeEach
    void setUp() {
        loggerMock = LoggerFactory.getLogger(EnviarEmailsServiceImplTest.class);
        servicio = new EnviarEmailsServiceImpl(loggerMock);
    }

    @Test
    void testServicioInstanciaYEjecuta() {
        assertNotNull(servicio);
        boolean resultado = servicio.enviarEmail(null, "Test de integración");
        assertTrue(resultado);
    }
}