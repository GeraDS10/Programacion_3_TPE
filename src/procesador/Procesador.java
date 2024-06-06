package procesador;

import tarea.Tarea;

import java.util.LinkedList;
import java.util.List;

public class Procesador {
    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;

    private List<Tarea> tareas;

    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
        this.tareas = new LinkedList<>();
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
        return tareas.stream().mapToInt(tarea -> tarea.getTiempo()).sum();
    }

    public boolean puedeTomarTarea(Tarea t, int X){
        if (t.getCritica() && this.getCantidadCriticas() >= 2) {
            return false;
        }
        // Check non-refrigerated processor time limit
        if (!this.getRefrigerado() && (this.getTiempoTotal() + t.getTiempo()) > X) {
            return false;
        }
        return true;
    }

    public int getCantidadCriticas() {
        return (int) tareas.stream().filter(task -> task.esCritica()).count();
    }

    public void addTarea(Tarea t){
        this.tareas.add(t);
    }

    public void deleteUltimaTarea(){
        this.tareas.remove(tareas.size() -1);
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
