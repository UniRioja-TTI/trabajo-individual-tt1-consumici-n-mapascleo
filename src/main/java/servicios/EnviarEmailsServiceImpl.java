package servicios;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailsServiceImpl implements InterfazEnviarEmails {

    private final Logger logger;

    public EnviarEmailsServiceImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        logger.info("SIMULACIÓN DE ENVÍO DE EMAIL | Destinatario: " + dest + " | Mensaje: " + email);
        return true;
    }
}