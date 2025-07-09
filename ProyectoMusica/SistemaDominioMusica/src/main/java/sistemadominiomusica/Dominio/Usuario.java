/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.Dominio;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author santi
 */
public class Usuario {
    private ObjectId id;
    private String username;
    private String email;
    private String contrasenia;
    private String imagenPerfil;
    private List<String> restricciones;
     private List<Favorito> favoritos;

    public Usuario() {
    }

    public Usuario(String username, String email, String contrasenia, String imagenPerfil, List<String> restricciones) {
        this.username = username;
        this.email = email;
        this.contrasenia = contrasenia;
        this.imagenPerfil = imagenPerfil;
        this.restricciones = restricciones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public List<String> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<String> restricciones) {
        this.restricciones = restricciones;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }
    
    
}
