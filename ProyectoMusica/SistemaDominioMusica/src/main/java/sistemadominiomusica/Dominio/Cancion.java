/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.Dominio;

import org.bson.types.ObjectId;

/**
 *
 * @author santi
 */
public class Cancion {
    private ObjectId id;
     private String Titulo;
    private Double Duracion;
    private ObjectId idArtista;

    public Cancion(ObjectId id, String Titulo, Double Duracion, ObjectId idArtista) {
        this.id = id;
        this.Titulo = Titulo;
        this.Duracion = Duracion;
        this.idArtista = idArtista;
    }

    public Cancion() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public Double getDuracion() {
        return Duracion;
    }

    public void setDuracion(Double Duracion) {
        this.Duracion = Duracion;
    }

    public ObjectId getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(ObjectId idArtista) {
        this.idArtista = idArtista;
    }
    
    
}
