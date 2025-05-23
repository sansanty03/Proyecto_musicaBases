/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.NegociosException;
import java.util.List;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;

/**
 *
 * @author santi
 */
public interface IAlbumesBO {
    public AlbumDTO agregarAlbum(AlbumDTO nuevoAlbum) throws NegociosException;

    public List<AlbumDTO> obtenerAlbumes(String idUsuario) throws NegociosException;

    public List<AlbumDTO> obtenerAlbumesPorGenero(String idUsuario, String generoBuscado) throws NegociosException;

    public List<AlbumDTO> obtenerAlbumesPorNombre(String idUsuario, String nombreBuscado) throws NegociosException;

    public AlbumDTO obtenerAlbumPorNombre(String nombreBuscado) throws NegociosException;

    public List<AlbumDTO> obtenerAlgumesPorFecha(String idUsuario, String fechaTexto) throws NegociosException;

    public List<CancionDTO> obtenerCancionesPorIdAlbum(String idAlbum) throws NegociosException;
    
    public CancionDTO buscarCancionPorId(String idCancion);
    
    public List<CancionDTO> buscarCancionesPorNombre(String nombre, String idUsuario)throws NegociosException;
    
    public List<CancionDTO> obtenerTodasLasCanciones(String idUsuario);
}
