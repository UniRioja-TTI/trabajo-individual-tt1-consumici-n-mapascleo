package servicios;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.springframework.stereotype.Service;
import io.swagger.client.api.SolicitudApi;
import io.swagger.client.model.Solicitud;
import io.swagger.client.model.SolicitudResponse;
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
        SolicitudApi solicitudApi = new SolicitudApi();
        solicitudApi.getApiClient().setBasePath("http://servicio-consumible:8080");
        Solicitud peticion = new Solicitud();
        List<Integer> cantidades = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : ds.getNums().entrySet()) {
            int idEntidad = entry.getKey();
            int cantidad = entry.getValue();
            String nombreEntidad = "Desconocida";
            for (Entidad e : entidadesInventadas) {
                if (e.getId() == idEntidad) {
                    nombreEntidad = e.getName();
                    break;
                }
            }
            cantidades.add(cantidad);
            nombres.add(nombreEntidad);
        }
        peticion.setCantidadesIniciales(cantidades);
        peticion.setNombreEntidades(nombres);
        try {
            SolicitudResponse response = solicitudApi.solicitudSolicitarPost(peticion, "usuario_prueba");
            if (Boolean.TRUE.equals(response.isDone())) {
                int tokenReal = response.getTokenSolicitud();
                almacenamientoProvisional.put(tokenReal, ds);
                return tokenReal;
            } else {
                System.err.println("Error en servidor: " + response.getErrorMessage());
                return -1;
            }

        } catch (ApiException e) {
            System.err.println("Error de conexión al solicitar: " + e.getResponseBody());
            return -1;
        }
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        ResultadosApi resultadosApi = new ResultadosApi();
        resultadosApi.getApiClient().setBasePath("http://servicio-consumible:8080");
        try {
            ResultsResponse response = resultadosApi.resultadosPost("usuario_prueba", ticket);
            return new DatosSimulation(response.getData());
        } catch (ApiException e) {
            System.err.println("Error al contactar con la API: " + e.getMessage());
            return new DatosSimulation("Error devuelto por la API: " + e.getResponseBody());
        }
    }
}