package procesador;

import tarea.Tarea;

import java.util.LinkedList;
import java.util.List;

public class Procesador{
    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;
    private int cantidadCriticas;
    private int tiempoEjecucion;

    private List<Tarea> tareas;

    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tareas = new LinkedList<>();
        this.cantidadCriticas = 0;
        this.tiempoEjecucion = 0;
    }

    public Procesador(Procesador p){
        this.id = p.getId();
        this.codigo = p.getCodigo();
        this.refrigerado = p.getRefrigerado();
        this.anio = p.getAnio();
        this.tareas = new LinkedList<>();
        if (p.getTareas().size() > 0){
            for (Tarea t: p.getTareas()
            ) {
                tareas.add(t);
            }
        }

    }

    public LinkedList<Tarea> getTareas(){
        return new LinkedList<Tarea>(this.tareas);
    }

    public int getTiempoTotal() {
        return this.tiempoEjecucion;
    }

    public boolean puedeTomarTarea(Tarea t, int X){
        if (t.getCritica() && this.getCantidadCriticas() >= 2) {
            return false;
        }
        // Check non-refrigerated processor time limit
        if ((this.getRefrigerado() == false) && ((this.getTiempoTotal() + t.getTiempo()) > X)) {
            return false;
        }
        return true;
    }

    public int getCantidadCriticas() {
        return this.cantidadCriticas;
    }

    public void addTarea(Tarea t){

        this.tareas.add(t);
        if (t.getCritica()) {
            this.cantidadCriticas++;
        }
        this.tiempoEjecucion += t.getTiempo();
    }

    public void deleteUltimaTarea(){

        Tarea ultima = this.tareas.getLast();
        this.tiempoEjecucion -= ultima.getTiempo();
        if (ultima.getCritica()){
            this.cantidadCriticas--;
        }
        this.tareas.removeLast();
    }

    public void deleteTareas(){
        this.tareas.clear();
        this.cantidadCriticas = 0;
        this.tiempoEjecucion = 0;
    }
    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Boolean getRefrigerado() {
        return refrigerado;
    }

    public Integer getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return "Procesador{" +
                "id='" + id + '\'' +
                ", codigo='" + codigo + '\'' +
                ", refrigerado=" + refrigerado +
                ", anio=" + anio +
                '}';
    }


}
