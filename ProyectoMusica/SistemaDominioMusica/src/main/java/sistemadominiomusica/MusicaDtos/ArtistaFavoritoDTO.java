/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.MusicaDtos;

import java.util.Date;

/**
 *
 * @author LABCISCO-PC036
 */
public class ArtistaFavoritoDTO {
    
    private String idFavorito;
    private String idArtista;
    private String nombreArtista;
    private String tipoArtista;
    private String generoArtista;
    private Date fechaAgregacion;

    public ArtistaFavoritoDTO() {
    }

    public ArtistaFavoritoDTO(String idFavorito, String idArtista, String nombreArtista, String tipoArtista, String generoArtista, Date fechaAgregacion) {
        this.idFavorito = idFavorito;
        this.idArtista = idArtista;
        this.nombreArtista = nombreArtista;
        this.tipoArtista = tipoArtista;
        this.generoArtista = generoArtista;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(String idArtista) {
        this.idArtista = idArtista;
    }

    

    public String getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(String idFavorito) {
        this.idFavorito = idFavorito;
    }

      

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public String getGeneroArtista() {
        return generoArtista;
    }

    public void setGeneroArtista(String generoArtista) {
        this.generoArtista = generoArtista;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }
    
    
}
