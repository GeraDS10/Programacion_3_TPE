import procesador.Procesador;
import tarea.Tarea;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Servicios servicios = new Servicios("./src/datasets/Procesadores.csv", "./src/datasets/Tareas.csv", 90);

        /*
        BACKTRACKING
         */

        servicios.backtracking();
        LinkedList<Procesador> solucionBacktracking = servicios.getSolucionBacktracking();
        System.out.println("SOLUCION BACKTRACKING");
        for (Procesador p: solucionBacktracking
             ) {
            System.out.println(p);
            for (Tarea t: p.getTareas()
                 ) {
                System.out.println(t);
            }

        }
        System.out.println("El tiempo máximo de ejecución óptimo es: " + servicios.getTiempoOptimoBacktracking());
        System.out.println("La cantidad de estados generados por backtracking es = " + servicios.getCantidadEstadosBacktraking());




        /*
        GREEDY
         */

        System.out.println("");
        System.out.println("SOLUCION GREEDY");
        servicios.greedy();
        System.out.println("El tiempo máximo de ejecución óptimo es: " + servicios.getTiempoOptimoGreedy());
        System.out.println("La cantidad de candidatos considerados por greedy fue = " + servicios.getCantidadCandidatosGreedy());






        /*
        ---------------SERVICIOS 1, 2, 3 --------------------------


        System.out.println("--------SERVICIO 1 ------------");
        Tarea porID = new Tarea();
        porID = servicios.servicio1("T2");
        System.out.println("Respuesta ID = T2 -> " + porID);

        Tarea porIDDos = new Tarea();
        porIDDos = servicios.servicio1("T7");
        System.out.println("Respuesta ID = T7 -> " + porIDDos);


        System.out.println("--------SERVICIO 2 ------------");
        List<Tarea> tareasCriticas = new LinkedList<>();
        tareasCriticas = servicios.servicio2(true);
        System.out.println("TAREAS CRITICAS");
        Iterator<Tarea> iterator = tareasCriticas.iterator();
        while (iterator.hasNext()) {
            Tarea tarea = iterator.next();
            System.out.println(tarea);
        }

        List<Tarea> tareasNoCriticas = new LinkedList<>();
        tareasNoCriticas = servicios.servicio2(false);
        System.out.println("TAREAS NO CRITICAS");
        Iterator<Tarea> iteratorNoCriticas = tareasNoCriticas.iterator();
        while (iteratorNoCriticas.hasNext()) {
            Tarea tarea = iteratorNoCriticas.next();
            System.out.println(tarea);
        }


        System.out.println("--------SERVICIO 3 ------------");

        List<Tarea> taresPrimeraMitad = servicios.servicio3(1,50);
        System.out.println("TAREAS ENTRE 1 Y 50");
        Iterator<Tarea> iteratorPM = taresPrimeraMitad.iterator();
        while (iteratorPM.hasNext()) {
            Tarea tarea = iteratorPM.next();
            System.out.println(tarea);
        }

        List<Tarea> taresSegundaMitad = servicios.servicio3(51,100);
        System.out.println("TAREAS ENTRE 51 Y 100");
        Iterator<Tarea> iteratorSM = taresSegundaMitad.iterator();
        while (iteratorSM.hasNext()) {
            Tarea tarea = iteratorSM.next();
            System.out.println(tarea);
        }



         */

    }
}
