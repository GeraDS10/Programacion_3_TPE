import procesador.Procesador;
import tarea.Tarea;
import utils.CSVReader;
import java.util.LinkedList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "tarea.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    private LinkedList<Procesador> procesadores;
    private LinkedList<Tarea> tareas;

    private int X;
    private int tiempoOptimo;

    private LinkedList<Procesador> solucionBacktracking;
    private int cantidadEstadosBacktracking;

    private int cantidadCandidatosGreedy;
    private CSVReader reader;

    /*
     * La complejidad temporal del constructor sera O(n), ya que la iniciacion de atributos tiene
     * una complejidad O(1) y la complejidad de la lectura de archivos CSV poseen una complejidad O(n) + O(n)
     */
    public Servicios(String pathProcesadores, String pathTareas, int X)
    {
        tareas = new LinkedList<>();
        procesadores = new LinkedList<>();
        reader = new CSVReader();
        procesadores = reader.readProcessors(pathProcesadores);
        tareas = reader.readTasks(pathTareas);
        this.X = X;
        this.tiempoOptimo = Integer.MAX_VALUE;
        cantidadEstadosBacktracking = 0;
        solucionBacktracking = new LinkedList<>();
        cantidadCandidatosGreedy = 0;
    }


    /*
     * Expresar la complejidad temporal del servicio 1.
     * La complejidad temporal es O(n) ya que en el peor de los casos debera iterar todos los elementos de la lista
     */

    public Tarea servicio1(String ID) {
        for (Tarea t : this.tareas) {
                if (t.getId().equals(ID)) {
                    return t;
                }
            }
            return new Tarea();
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     * La complejidad temporal será O(n) ya que en el peor de los casos deberá iterar sobre todos los objetos de la lista
     */


    public List<Tarea> servicio2(boolean esCritica) {
        LinkedList<Tarea> tareasSolicitadas = new LinkedList<>();
        for (Tarea t : tareas) {
            if (t.getCritica() == esCritica){
                tareasSolicitadas.add(t);
            }
        }
        return tareasSolicitadas;
    }

    /*
     * Expresar la complejidad temporal del servicio 3.
     * La complejidad temporal será O(n) ya que deberá iterar sobre todoslos objetos de la lista.
     */


    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        LinkedList<Tarea> tareasEnRango = new LinkedList<>();
        for (Tarea t : tareas) {
            int prioridad = t.getPrioridad();
            if ((prioridad > prioridadInferior) && (prioridad < prioridadSuperior)){
                tareasEnRango.add(t);
            }
        }
        return tareasEnRango;
    }




/*
* -------------------------------------- BACKTRACKING -----------------------------------------------
* */

    /**
    La estrategia elegida para realizar el algoritmo de backtracking fue buscar la mayor cantidad de asigaciones de tareas entre los procesadores,
    cumpliendo con las restricciones y analizando el tiempo optimo de realizacion de las tareas de cada solucion para asi hallar la óptima.
     */
    public void backtracking() {
        asignarTareas(0);
        System.out.println("SOLUCION BACKTRACKING");
        mostrarSolucion(this.solucionBacktracking);
        System.out.println("El tiempo maximo de ejecución backtracking es: " + this.tiempoOptimo);
        System.out.println("La cantidad de estados generados es: " + this.cantidadEstadosBacktracking);
    }

    private void asignarTareas(int indiceTarea) {
        if (indiceTarea == tareas.size()) {

            cantidadEstadosBacktracking++;
            int tiempoMaximo = calcularTiempoMaximo();
            if (tiempoMaximo < tiempoOptimo) {
                tiempoOptimo = tiempoMaximo;
                for (Procesador p: this.procesadores
                     ) {

                    Procesador copia = new Procesador(p);
                    this.solucionBacktracking.add(copia);
                }
            }
            return;
        }

        Tarea tareaActual = tareas.get(indiceTarea);
        for (Procesador p : procesadores) {
            if (p.puedeTomarTarea(tareaActual, this.X)) {
                p.addTarea(tareaActual);
                asignarTareas(indiceTarea + 1);
                p.deleteUltimaTarea();
            }
        }
    }

    private int calcularTiempoMaximo() {
        int tiempoMaximo = 0;
        for (Procesador p : this.procesadores) {
            int tiempoProcesador = p.getTiempoTotal();
            if (tiempoProcesador > tiempoMaximo) {
                tiempoMaximo = tiempoProcesador;
            }
        }
        return tiempoMaximo;
    }


    /*
    --------------------------------GREEDY-------------------------------------------
     */

    /**
     * Para la soluciòn mediante el algoritmo Greedy se recorre los procesadores para asignar las tareas, verificando en cada uno que se cumplan las restricciones
     * y luego que sea el procesador que tenga el menor tiempo total en realizacion de tareas
    * */
    public void greedy() {
           for (Tarea tareaActual : this.tareas) {
            Procesador mejorProcesador = null;
            int tiempoMinimo = Integer.MAX_VALUE;

            for (Procesador p : this.procesadores) {
                cantidadCandidatosGreedy++;
                if (p.puedeTomarTarea(tareaActual, this.X)) {
                    int totalTime = p.getTiempoTotal();
                    if (totalTime < tiempoMinimo) {
                        tiempoMinimo = totalTime;
                        mejorProcesador = p;
                    }
                }
            }

            if (mejorProcesador != null) {
                mejorProcesador.addTarea(tareaActual);
            } else {
                System.out.println("No se puede asignar la tarea -> " + tareaActual);
            }
        }
        System.out.println("SOLUCION GREEDY");
           mostrarSolucion(this.procesadores);
        System.out.println("El tiempo óptimo Greedy es: " + calcularTiempoMaximo());
        System.out.println("La cantidad de candidatos considerados por greedy fue = " + this.cantidadCandidatosGreedy);
    }

    private void mostrarSolucion(LinkedList<Procesador> procesadores){
        for (Procesador p: procesadores
             ) {
            System.out.println(p);
            for (Tarea t: p.getTareas()
                 ) {
                if (t != null){
                    System.out.println(t);
                }
            }
        }

    }
}