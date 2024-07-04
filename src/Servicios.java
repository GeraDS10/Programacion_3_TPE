import procesador.Procesador;
import tarea.Tarea;
import utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "tarea.Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    private LinkedList<Procesador> procesadores;
    private LinkedList<Tarea> tareas;
    private HashMap<String, Tarea> tareasCriticas;
    private HashMap<String, Tarea> tareasNoCriticas;

    private int XBacktracking;
    private int XGreedy;
    private int tiempoOptimo;

    private LinkedList<Procesador> solucionBacktracking;
    private int cantidadEstadosBacktracking;

    private LinkedList<Procesador> solucionGreedy;
    private int cantidadCandidatosGreedy;
    private CSVReader reader;

    private void getMapsTareas(){
        for (Tarea t: this.tareas){
            if (t.getCritica()){
                this.tareasCriticas.put(t.getId(), t);
            }
            else {
                this.tareasNoCriticas.put(t.getId(), t);
            }
        }
    }

    /*
     * La complejidad temporal del constructor sera O(n), ya que la iniciacion de atributos tiene
     * una complejidad O(1), la complejidad de la lectura de archivos CSV poseen una complejidad O(n) + O(n) y
     * la creacion de los HashMap tiene una complejidad O(n)
     */
    public Servicios(String pathProcesadores, String pathTareas)
    {
        tareas = new LinkedList<>();
        procesadores = new LinkedList<>();
        tareasCriticas = new HashMap<>();
        tareasNoCriticas = new HashMap<>();
        reader = new CSVReader();
        procesadores = reader.readProcessors(pathProcesadores);
        tareas = reader.readTasks(pathTareas);
        this.XBacktracking = Integer.MAX_VALUE;
        this.XGreedy = Integer.MAX_VALUE;
        this.tiempoOptimo = Integer.MAX_VALUE;
        cantidadEstadosBacktracking = 0;
        solucionBacktracking = new LinkedList<>();
        solucionGreedy = new LinkedList<>();
        cantidadCandidatosGreedy = 0;
        getMapsTareas();
    }



    /*
     * Expresar la complejidad temporal del servicio 1.
     * La complejidad temporal es O(1) ya que verificar la existencia de una clave en un HashMap es O(1),
     * acceder a un valor del HashMap es un acceso directo con complejidad O(1),
     * crear una instancia de Tarea representa complejidad también O(1).
     */

    public Tarea servicio1(String ID) {
        if (tareasCriticas.containsKey(ID)){
            return new Tarea(tareasCriticas.get(ID));
        }
        else if (tareasNoCriticas.containsKey(ID)){
            return new Tarea(tareasNoCriticas.get(ID));
        }
            return null;
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     * La complejidad temporal será O(n) ya que deberá devolver una vista de un HashMap, lo cual representa una complejidad O(n)
     * porque deberá recorrer todos los valores del HasMap para crear el ArrayList.
     */


    public List<Tarea> servicio2(boolean esCritica) {
        if (esCritica){
            return new ArrayList<>(this.tareasCriticas.values());
        }
        else {
            return new ArrayList<>(this.tareasNoCriticas.values());
        }
    }

    /*
     * Expresar la complejidad temporal del servicio 3.
     * La complejidad temporal será O(n) ya que insertar un valor al final de la linkedList implica una complejidad O(1), pero
     * deberá iterar sobre cada valor de los HashMap lo cual representa una complejidad O(n + m).
     */


    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> tareasEnRango = new LinkedList<>();
        for (Tarea tarea : tareasCriticas.values()) {
            if (tarea.getPrioridad() >= prioridadInferior && tarea.getPrioridad() <= prioridadSuperior) {
                tareasEnRango.add(tarea);
            }
        }
        for (Tarea tarea : tareasNoCriticas.values()) {
            if (tarea.getPrioridad() >= prioridadInferior && tarea.getPrioridad() <= prioridadSuperior) {
                tareasEnRango.add(tarea);
            }
        }
        return tareasEnRango;
    }




/*
* -------------------------------------- BACKTRACKING -----------------------------------------------
* */

    /**
    La estrategia elegida para realizar el algoritmo de backtracking fue buscar todas las combinaciones posibles de asignacion de tareas a los distintos
     procesadores de manera recursiva contemplando que se cumplan las restricciones de tareas criticas y tiempo máximo de asignacion de tareas, con lo cual
     se generan tantos arboles como combinacion de tarea-procesador puede existir, donde cada nivel corresponde con una tarea. Además se implementó un mecanismo de poda a partir de comparar
     el tiempo de ejecución de la solución que se esta generando con el tiempo de la solución que se tenga hasta el momento como óptima, que será la de
     menor tiempo total de ejecución.
     */
    public void backtracking(int x) {
        for (Procesador p: this.procesadores){
            p.deleteTareas();
        }
        this.XBacktracking = x;
        asignarTareas(0);
        if (this.cantidadEstadosBacktracking > 0){
            System.out.println("SOLUCION BACKTRACKING");
            mostrarSolucion(this.solucionBacktracking);
            System.out.println("El tiempo maximo de ejecución backtracking es: " + this.tiempoOptimo);
            System.out.println("La cantidad de estados generados es: " + this.cantidadEstadosBacktracking);
        }
        else {
            System.out.println("NO EXISTE SOLUCION BACKTRACKING");
        }

    }

    private void asignarTareas(int indiceTarea) {
        if (indiceTarea == tareas.size()) {

            cantidadEstadosBacktracking++;
            int tiempoMaximo = calcularTiempoMaximo();
            if (tiempoMaximo < tiempoOptimo) {
                this.solucionBacktracking.clear();
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
            if (p.puedeTomarTarea(tareaActual, this.XBacktracking)) {
                p.addTarea(tareaActual);

                if (calcularTiempoMaximo() < this.tiempoOptimo){
                    asignarTareas(indiceTarea + 1);
                }

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
     * y optando por asignar la tarea al procesador que tenga el menor tiempo de ejecución actual.
    * */
    public void greedy(int x) {
        this.XGreedy = x;
        for (Procesador p: this.procesadores){
            p.deleteTareas();
        }

           for (Tarea tareaActual : this.tareas) {
            Procesador mejorProcesador = null;
            int tiempoMinimo = Integer.MAX_VALUE;

            for (Procesador p : this.procesadores) {
                cantidadCandidatosGreedy++;
                if (p.puedeTomarTarea(tareaActual, this.XGreedy)) {
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
                System.out.println("NO EXISTE SOLUCION GREEDY");
                return;
            }
        }
        System.out.println("SOLUCION GREEDY");
           mostrarSolucion(this.procesadores);
        System.out.println("El tiempo óptimo Greedy es: " + calcularTiempoMaximoGreedy(this.procesadores));
        System.out.println("La cantidad de candidatos considerados por greedy fue = " + this.cantidadCandidatosGreedy);
    }

    private int calcularTiempoMaximoGreedy(LinkedList<Procesador> listaProcesadores){
        int tiempoMaximo = 0;
        for (Procesador p : listaProcesadores) {
            int tiempoProcesador = p.getTiempoTotal();
            if (tiempoProcesador > tiempoMaximo) {
                tiempoMaximo = tiempoProcesador;
            }
        }
        return tiempoMaximo;
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