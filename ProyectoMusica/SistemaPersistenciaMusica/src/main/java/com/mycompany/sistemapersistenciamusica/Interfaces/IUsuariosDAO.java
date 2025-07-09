/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Interfaces;

import java.util.Date;
import java.util.List;
import org.bson.Document;
import sistemadominiomusica.Dominio.Favorito;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.FavoritoDTO;
import sistemadominiomusica.MusicaDtos.UsuarioDTO;

/**
 *
 * @author santi
 */
public interface IUsuariosDAO {
     public abstract Usuario agregarUsuario(UsuarioDTO nuevoUsuario);
     
    public abstract Usuario consultarPorId(String idUsuario);
    
    public abstract Usuario consultarPorUsername(String username);
    
    public abstract Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);
    
    public abstract Usuario InicioSesion(String username, String contrasenia);

    public abstract void agregarRestringido(String idUsuario, String genero);
    
    public abstract void eliminarRestringido(String idUsuario, String genero);
    
    public abstract List<String> mostrarRestringidos(String idUsuario);
    
    public abstract boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO);
    public abstract boolean eliminarFavorito(String idUsuario, String idContenido);
    public abstract List<Document> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum);
    public abstract List<Document> obtenerArtistasFavoritos(String idUsuario, String nombreArtista);
    public abstract List<Document> obtenerCancionesFavoritas(String idUsuario, String nombreCancion);
    public abstract List<Document> obtenerGenerosFavoritos(String idUsuario, String genero);
    public abstract List<Document> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin);
    public abstract List<Document> obtenerTodosFavoritos(String idUsuario);
    public abstract List<Favorito> consultarFavoritos(String idUsuario);
}
