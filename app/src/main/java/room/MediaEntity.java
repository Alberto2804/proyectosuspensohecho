package room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "media_local")
public class MediaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int tmdbId;
    private String titulo;
    private String descripcion;
    private String posterPath;
    private String tipo;
    private boolean esPendiente;
    private boolean esSeguimiento;

    private String fechaVisualizacion;
    private float puntuacion;
    private byte[] fotoRecuerdo;

    public MediaEntity() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTmdbId() { return tmdbId; }
    public void setTmdbId(int tmdbId) { this.tmdbId = tmdbId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public boolean isEsPendiente() { return esPendiente; }
    public void setEsPendiente(boolean esPendiente) { this.esPendiente = esPendiente; }
    public boolean isEsSeguimiento() { return esSeguimiento; }
    public void setEsSeguimiento(boolean esSeguimiento) { this.esSeguimiento = esSeguimiento; }
    public String getFechaVisualizacion() { return fechaVisualizacion; }
    public void setFechaVisualizacion(String fechaVisualizacion) { this.fechaVisualizacion = fechaVisualizacion; }
    public float getPuntuacion() { return puntuacion; }
    public void setPuntuacion(float puntuacion) { this.puntuacion = puntuacion; }
    public byte[] getFotoRecuerdo() { return fotoRecuerdo; }
    public void setFotoRecuerdo(byte[] fotoRecuerdo) { this.fotoRecuerdo = fotoRecuerdo; }
}