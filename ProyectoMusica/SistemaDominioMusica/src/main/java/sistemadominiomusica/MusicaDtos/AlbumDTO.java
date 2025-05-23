/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.MusicaDtos;

import java.util.Date;
import java.util.List;
import sistemadominiomusica.Dominio.Genero;

/**
 *
 * @author santi
 */
public class AlbumDTO {
    private String Id;
    private String Nombre;
    private Date FechaLanzamiento;
    private Genero genero;
    private String ImagenPortada;
    private String idArtista;
    private List<CancionDTO> canciones;

    public AlbumDTO() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    public AlbumDTO(String Nombre, Date FechaLanzamiento, Genero genero, String ImagenPortada, String idArtista, List<CancionDTO> canciones) {
        this.Nombre = Nombre;
        this.FechaLanzamiento = FechaLanzamiento;
        this.genero = genero;
        this.ImagenPortada = ImagenPortada;
        this.idArtista = idArtista;
        this.canciones = canciones;
    }

    public AlbumDTO(String Nombre, Date FechaLanzamiento, Genero genero, String ImagenPortada, String idArtista) {
        this.Nombre = Nombre;
        this.FechaLanzamiento = FechaLanzamiento;
        this.genero = genero;
        this.ImagenPortada = ImagenPortada;
        this.idArtista = idArtista;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Date getFechaLanzamiento() {
        return FechaLanzamiento;
    }

    public void setFechaLanzamiento(Date FechaLanzamiento) {
        this.FechaLanzamiento = FechaLanzamiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagenPortada() {
        return ImagenPortada;
    }

    public void setImagenPortada(String ImagenPortada) {
        this.ImagenPortada = ImagenPortada;
    }

    public String getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(String idArtista) {
        this.idArtista = idArtista;
    }

    public List<CancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionDTO> canciones) {
        this.canciones = canciones;
    }
}
