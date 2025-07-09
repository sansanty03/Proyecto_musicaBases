/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.NegociosException;
import java.util.Date;
import java.util.List;
import sistemadominiomusica.Dominio.Favorito;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.AlbumFavoritoDTO;
import sistemadominiomusica.MusicaDtos.ArtistaFavoritoDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;
import sistemadominiomusica.MusicaDtos.CancionFavoritaDTO;
import sistemadominiomusica.MusicaDtos.FavoritoDTO;
import sistemadominiomusica.MusicaDtos.GeneroFavoritoDTO;
import sistemadominiomusica.MusicaDtos.UsuarioDTO;

/**
 *
 * @author santi
 */
public interface IUsuariosBO {
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegociosException;

    public UsuarioDTO consultarPorId(String idUsuario);

    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) throws NegociosException;

    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegociosException;
    
    public abstract void agregarGeneroRestringido(String idUsuario, String genero);

    public abstract void eliminarGeneroRestringido(String idUsuario, String genero);

    public abstract List<String> mostrarGenerosRestringidos(String idUsuario);
    
    public abstract boolean agregarFavorito(String idUsuario, FavoritoDTO favorito) throws NegociosException;

    public abstract boolean eliminarFavorito(String idUsuario, String idContenido) throws NegociosException;

    public abstract List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum);

    public abstract List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista);

    public abstract List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion);

    public abstract List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero);

    public abstract List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin);

    public abstract List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario);

    public abstract List<Favorito> consultarFavoritos(String idUsuario);

}
