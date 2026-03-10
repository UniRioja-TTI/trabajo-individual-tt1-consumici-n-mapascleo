package servicios;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ContactoSimServiceImpl implements InterfazContactoSim {

    private Map<Integer, DatosSolicitud> almacenamientoProvisional = new HashMap<>();
    private List<Entidad> entidadesInventadas;

    public ContactoSimServiceImpl() {
        entidadesInventadas = new ArrayList<>();

        Entidad e1 = new Entidad();
        e1.setId(1);
        e1.setName("Servidor Alpha");
        e1.setDescripcion("Servidor principal");
        entidadesInventadas.add(e1);

        Entidad e2 = new Entidad();
        e2.setId(2);
        e2.setName("Base de Datos");
        e2.setDescripcion("BD central");
        entidadesInventadas.add(e2);

        Entidad e3 = new Entidad();
        e3.setId(3);
        e3.setName("Clúster de Cálculo");
        e3.setDescripcion("Procesamiento pesado");
        entidadesInventadas.add(e3);
    }

    @Override
    public List<Entidad> getEntities() {
        return entidadesInventadas;
    }

    @Override
    public boolean isValidEntityId() {
        return true;
    }

    @Override
    public int solicitarSimulation(DatosSolicitud ds) {
        int token = new Random().nextInt(9000) + 1000;
        almacenamientoProvisional.put(token, ds);
        return token;
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        return null;
    }
}