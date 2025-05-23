/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Exceptions.NegociosException;
import Interfaces.IArtistasBO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IArtistasDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import sistemadominiomusica.Dominio.Artista;
import sistemadominiomusica.Dominio.IntegranteRol;
import sistemadominiomusica.MusicaDtos.ArtistaDTO;
import sistemadominiomusica.MusicaDtos.IntegranteDTO;

/**
 *
 * @author santi
 */
public class ArtistaBO implements IArtistasBO {
    private IArtistasDAO artistasDAO;

    public ArtistaBO(IArtistasDAO artistasDAO) {
        this.artistasDAO = artistasDAO;
    }

    @Override
    public Artista registrarArtista(ArtistaDTO nuevoArtista) throws NegociosException {
        if (nuevoArtista == null || nuevoArtista.getNombre() == null || nuevoArtista.getNombre().isEmpty()) {
            throw new NegociosException("El nombre del grupo no puede estar vacío.");
        }
        if (nuevoArtista.getGenero() == null) {
            throw new NegociosException("Debe especificarse el género musical del grupo.");
        }
        if (!nuevoArtista.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegociosException("El nombre del grupo solo puede incluir letras, números y espacios.");
        }
        if (nuevoArtista.getNombre().length() > 30) {
            throw new NegociosException("El nombre del grupo no debe superar los 30 caracteres.");
        }
        Artista existente = artistasDAO.buscarArtistaNombre(nuevoArtista.getNombre());
        if (existente != null) {
            throw new NegociosException("Ya hay un grupo registrado con ese nombre.");
        }
        return artistasDAO.registrarArtista(nuevoArtista);

    }

    @Override
    public ArtistaDTO buscarArtistaPorId(String idArtista) throws NegociosException {
        Artista artista = artistasDAO.buscarArtistaId(idArtista);

        if (artista == null) {
            throw new NegociosException("No se encontró ningún artista con el ID proporcionado.");
        }

        ArtistaDTO artistaDTO = new ArtistaDTO();
        artistaDTO.setTipo(artista.getTipo());
        artistaDTO.setNombre(artista.getNombre());
        artistaDTO.setImagen(artista.getImagen());
        artistaDTO.setGenero(artista.getGenero());

        return artistaDTO;

    }

    @Override
    public List<ArtistaDTO> buscarArtistasPorNombre(String idUsuario, String nombre) throws NegociosException {
        List<Artista> artistas = artistasDAO.buscarArtistasNombre(idUsuario, nombre);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas == null || artistas.isEmpty()) {
            return artistasDTO;
        }

        for (Artista a : artistas) {
            ArtistaDTO dto = new ArtistaDTO();
            dto.setNombre(a.getNombre());
            dto.setGenero(a.getGenero());
            dto.setTipo(a.getTipo());
            artistasDTO.add(dto);
        }

        return artistasDTO;

    }

    @Override
    public List<ArtistaDTO> buscarArtistasPorGenero(String idUsuario, String genero) throws NegociosException {
        List<Artista> artistas = artistasDAO.buscarArtistasGenero(idUsuario, genero);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas == null || artistas.isEmpty()) {
            return artistasDTO;
        }

        for (Artista a : artistas) {
            ArtistaDTO dto = new ArtistaDTO();
            dto.setNombre(a.getNombre());
            dto.setGenero(a.getGenero());
            dto.setTipo(a.getTipo());
            artistasDTO.add(dto);
        }

        return artistasDTO;
    }

    @Override
    public List<ArtistaDTO> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero) throws NegociosException {
        List<Artista> artistas = artistasDAO.buscarArtistasNombreGenero(idUsuario, nombre, genero);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas == null || artistas.isEmpty()) {
            return artistasDTO;
        }

        for (Artista a : artistas) {
            ArtistaDTO dto = new ArtistaDTO();
            dto.setNombre(a.getNombre());
            dto.setGenero(a.getGenero());
            dto.setTipo(a.getTipo());
            artistasDTO.add(dto);
        }

        return artistasDTO;
    }

    @Override
    public List<ArtistaDTO> buscarArtistas(String idUsuario) throws NegociosException {
        List<Artista> artistas = artistasDAO.buscarArtistas(idUsuario);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas == null || artistas.isEmpty()) {
            return artistasDTO;
        }

        for (Artista a : artistas) {
            ArtistaDTO dto = new ArtistaDTO();
            dto.setNombre(a.getNombre());
            dto.setGenero(a.getGenero());
            dto.setTipo(a.getTipo());
            artistasDTO.add(dto);
        }

        return artistasDTO;
    }

    @Override
    public ArtistaDTO buscarArtistaPorNombre(String nombre) throws NegociosException {
         Artista artista =  artistasDAO.buscarArtistaNombre(nombre);

         ArtistaDTO dto = new ArtistaDTO();
        if (artista == null ) {
            return dto;
        }
        dto.setNombre(artista.getNombre());
        dto.setGenero(artista.getGenero());
        dto.setTipo(artista.getTipo());


        return dto;
    }

    @Override
    public boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) throws NegociosException {
       if (nuevoIntegrante == null || nuevoIntegrante.getNombre() == null || nuevoIntegrante.getNombre().isEmpty()) {
            throw new NegociosException("El nombre del integrante no puede estar vacío.");
        }
        if (!nuevoIntegrante.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegociosException("El nombre del integrante solo puede contener caracteres alfanuméricos y espacios.");
        }
        return artistasDAO.agregarIntegrante(idArtista, nuevoIntegrante);

    }

    @Override
    public List<IntegranteDTO> consultarTodosLosIntegrantes(String idArtista) {
        List<Document> integrantes = artistasDAO.consultarIntegrantes(idArtista);
        List<IntegranteDTO> integrantesDTO = new ArrayList<>();

        if (integrantes == null || integrantes.isEmpty()) {
            return integrantesDTO;
        }

        for (Document i : integrantes) {
            IntegranteDTO dto = new IntegranteDTO();
            dto.setNombre(i.getString("nombre"));
            dto.setRol(IntegranteRol.valueOf(i.getString("rol")));
            dto.setFechaIngreso(i.getDate("fechaIngreso"));
            dto.setFechaSalida(i.getDate("fechaSalida")); // Puede ser null

            integrantesDTO.add(dto);
        }

        return integrantesDTO;

    }

    @Override
    public List<IntegranteDTO> consultarIntegrantesActivos(String idArtista) {
        List<Document> integrantes = artistasDAO.consultarIntegrantesActivos(idArtista);

        List<IntegranteDTO> integrantesDTO = new ArrayList<>();

        if (integrantes != null) {
            for (Document i : integrantes) {
                IntegranteDTO dto = new IntegranteDTO();
                dto.setNombre(i.getString("nombre"));
                dto.setRol(IntegranteRol.valueOf(i.getString("rol")));
                dto.setFechaIngreso(i.getDate("fechaIngreso"));
                dto.setFechaSalida(i.getDate("fechaSalida"));

                integrantesDTO.add(dto);
            }
        }

        return integrantesDTO;
    }
    
    
}
