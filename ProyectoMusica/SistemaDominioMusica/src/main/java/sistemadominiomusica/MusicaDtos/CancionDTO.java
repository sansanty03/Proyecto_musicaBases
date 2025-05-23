/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.MusicaDtos;

import org.bson.types.ObjectId;

/**
 *
 * @author santi
 */
public class CancionDTO {
    private String Id;
    private String Titulo;
    private Double Duracion;
    private String idArtista;

    public CancionDTO(String Titulo, Double Duracion, String idArtista) {
        this.Titulo = Titulo;
        this.Duracion = Duracion;
        this.idArtista = idArtista;
    }

    public CancionDTO(String Titulo, Double Duracion) {
        this.Titulo = Titulo;
        this.Duracion = Duracion;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    
    
    public CancionDTO() {
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

    public String getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(String idArtista) {
        this.idArtista = idArtista;
    }
    
    
}
