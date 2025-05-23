/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.NegociosException;
import java.util.List;
import sistemadominiomusica.Dominio.Artista;
import sistemadominiomusica.MusicaDtos.ArtistaDTO;
import sistemadominiomusica.MusicaDtos.IntegranteDTO;

/**
 *
 * @author santi
 */
public interface IArtistasBO {
    public abstract Artista registrarArtista(ArtistaDTO nuevoArtista) throws NegociosException;

    public ArtistaDTO buscarArtistaPorId(String idArtista) throws NegociosException;

    public abstract List<ArtistaDTO> buscarArtistasPorNombre(String idUsuario, String nombre) throws NegociosException;

    public abstract List<ArtistaDTO> buscarArtistasPorGenero(String idUsuario, String genero)throws NegociosException;

    public abstract List<ArtistaDTO> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero)throws NegociosException;

    public abstract List<ArtistaDTO> buscarArtistas(String idUsuario)throws NegociosException;

    public ArtistaDTO buscarArtistaPorNombre(String nombre) throws NegociosException;

    public abstract boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) throws NegociosException;

    public abstract List<IntegranteDTO> consultarTodosLosIntegrantes(String idArtista);

    public abstract List<IntegranteDTO> consultarIntegrantesActivos(String idArtista);
}
