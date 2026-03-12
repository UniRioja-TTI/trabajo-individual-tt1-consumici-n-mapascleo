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
import io.swagger.client.api.ResultadosApi;
import io.swagger.client.model.ResultsResponse;
import io.swagger.client.ApiException;

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
        ResultadosApi resultadosApi = new ResultadosApi();
        resultadosApi.getApiClient().setBasePath("http://IP_DE_TU_MAQUINA:PUERTO");
        try {
            ResultsResponse response = resultadosApi.resultadosPost("usuario_prueba", ticket);
            return new DatosSimulation(response.getData());
        } catch (ApiException e) {
            System.err.println("Error al contactar con la API: " + e.getMessage());
            return new DatosSimulation("Error al obtener los datos");
        }
    }
}