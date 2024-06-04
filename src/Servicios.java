import procesador.Procesador;
import tarea.Tarea;
import utils.CSVReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "tarea.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    private Map<String, Procesador> procesadores;
    private Map<String, Tarea> tareas;

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas)
    {
        tareas = new HashMap<>();
        procesadores = new HashMap<>();
        CSVReader reader = new CSVReader();
        procesadores = reader.readProcessors(pathProcesadores);
        tareas = reader.readTasks(pathTareas);


    }

    /*
     * Expresar la complejidad temporal del servicio 1.
     */
    public Tarea servicio1(String ID) {
        if (tareas.containsKey(ID)){
            return new Tarea(tareas.get(ID));
        }
        else {
            return new Tarea();
        }
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        LinkedList<Tarea> tareasSolicitadas = new LinkedList<>();
        for (Tarea t : tareas.values()) {
            if (t.esCritica() == esCritica){
                tareasSolicitadas.add(t);
            }
        }
        return tareasSolicitadas;
    }

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        LinkedList<Tarea> tareasEnRango = new LinkedList<>();
        for (Tarea t : tareas.values()) {
            int prioridad = t.getPrioridad();
            if ((prioridad > prioridadInferior) && (prioridad < prioridadSuperior)){
                tareasEnRango.add(t);
            }
        }
        return tareasEnRango;
    }


}