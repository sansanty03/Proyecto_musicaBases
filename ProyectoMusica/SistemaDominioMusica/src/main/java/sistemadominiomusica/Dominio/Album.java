/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.Dominio;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author santi
 */
public class Album {
   private ObjectId id;
    private String nombre;
    private Date fechaLanzamiento;
    private Genero genero;
    private String ImagenDireccion;
    private ObjectId idArtista;
    private List<Cancion> canciones; 

    public Album() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagenDireccion() {
        return ImagenDireccion;
    }

    public void setImagenDireccion(String ImagenDireccion) {
        this.ImagenDireccion = ImagenDireccion;
    }

    public ObjectId getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(ObjectId idArtista) {
        this.idArtista = idArtista;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
    
    
    
    
}
