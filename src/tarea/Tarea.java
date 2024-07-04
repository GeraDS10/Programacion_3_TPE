package tarea;

public class Tarea implements Comparable<Tarea>{
    private String id;
    private String nombre;
    private Integer tiempo;
    private Boolean critica;
    private Integer prioridad;

    public Tarea(String id, String nombre, Integer tiempo, Boolean critica, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }

    public Tarea(Tarea t){
        this.id = t.getId();
        this.nombre = t.getNombre();
        this.tiempo = t.getTiempo();
        this.critica = t.getCritica();
        this.prioridad = t.getPrioridad();
    }

    public Tarea(){

    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public Boolean getCritica() {
        return critica;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean esCritica(){
        if (this.critica){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int compareTo(Tarea otraTarea) {
        // Comparar por tiempo de ejecución en orden descendente (de mayor a menor)
        return otraTarea.tiempo - this.tiempo;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tiempo=" + tiempo +
                ", critica=" + critica +
                ", prioridad=" + prioridad +
                '}';
    }
}
