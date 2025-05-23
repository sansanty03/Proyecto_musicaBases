/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Interfaces;

import java.util.List;
import org.bson.Document;
import sistemadominiomusica.Dominio.Album;
import sistemadominiomusica.Dominio.Cancion;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;

/**
 *
 * @author santi
 */
public interface IAlbumesDAO {

    public Album agregarAlbum(AlbumDTO nuevoAlbum);

    public List<Album> obtenerAlbumes(String idUsuario);

    public List<Album> obtenerAlbumesGenero(String idUsuario, String generoBuscado);

    public List<Album> obtenerAlbumesNombre(String idUsuario,String nombreBuscado);

    public Album obtenerAlbumNombre(String nombreBuscado);

    public List<Album> obtenerAlbumesFecha(String idUsuario,String fechaTexto);

    public List<Cancion> obtenerCancionesIdAlbum(String idAlbum);
    
    public CancionDTO buscarCancionId(String idCancion);
    
    public List<Document> buscarCancionesNombre(String nombre, String idUsuario);
    
    public List<Document> obtenerTodasLasCanciones(String idUsuario);
}
