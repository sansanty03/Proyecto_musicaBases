/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Interfaces;

import java.util.List;
import org.bson.Document;
import sistemadominiomusica.Dominio.Artista;
import sistemadominiomusica.MusicaDtos.ArtistaDTO;
import sistemadominiomusica.MusicaDtos.IntegranteDTO;

/**
 *
 * @author santi
 */
public interface IArtistasDAO {
    public abstract Artista registrarArtista(ArtistaDTO nuevoArtista);

    public abstract List<Artista> buscarArtistasNombre(String idUsuario, String nombre);

    public abstract Artista buscarArtistaId(String idArtista);

    public abstract List<Artista> buscarArtistasGenero(String idUsuario, String genero);

    public abstract List<Artista> buscarArtistasNombreGenero(String idUsuario, String nombre, String genero);

    public abstract List<Artista> buscarArtistas(String idUsuario);

    public abstract Artista buscarArtistaNombre(String nombre);

    public abstract boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante);

    public abstract List<Document> consultarIntegrantes(String idArtista);

    public abstract List<Document> consultarIntegrantesActivos(String idArtista);
}
