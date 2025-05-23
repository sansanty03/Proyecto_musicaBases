/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Exceptions.NegociosException;
import Interfaces.IAlbumesBO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IAlbumesDAO;
import java.util.ArrayList;
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
public class AlbumesBO implements IAlbumesBO {
    private IAlbumesDAO albumesDAO;

    public AlbumesBO(IAlbumesDAO albumesDAO) {
        this.albumesDAO = albumesDAO;
    }

    @Override
    public AlbumDTO agregarAlbum(AlbumDTO nuevoAlbum) throws NegociosException {
        Album album = albumesDAO.agregarAlbum(nuevoAlbum);

        if (album != null) {
            AlbumDTO albumObtenido = new AlbumDTO();
            albumObtenido.setNombre(album.getNombre());
            albumObtenido.setFechaLanzamiento(album.getFechaLanzamiento());
            albumObtenido.setGenero(album.getGenero());
            albumObtenido.setImagenPortada(album.getImagenDireccion());
            albumObtenido.setIdArtista(album.getIdArtista().toString());
            if (album.getCanciones() != null) {
                if (album.getCanciones().size() < 2) {
                    throw new NegociosException("Necesita al menos 3 canciones para agregar un album.");
                } else {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : album.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumObtenido.setCanciones(cancionesDTO);
                }
            }

            return albumObtenido;
        } else {
            throw new NegociosException("Error al obtener la base de datos");
        }
    }

    @Override
    public List<AlbumDTO> obtenerAlbumes(String idUsuario) throws NegociosException {
        List<Album> albumes = albumesDAO.obtenerAlbumes(idUsuario);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenDireccion());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegociosException("Error al obtener la base de datos");
        }
    }

    @Override
    public List<AlbumDTO> obtenerAlbumesPorGenero(String idUsuario, String generoBuscado) throws NegociosException {
        List<Album> albumes = albumesDAO.obtenerAlbumesGenero(idUsuario, generoBuscado);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenDireccion());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegociosException("No se podueron obtener los albumes de la base de datos.");
        }
    }

    @Override
    public List<AlbumDTO> obtenerAlbumesPorNombre(String idUsuario, String nombreBuscado) throws NegociosException {
         List<Album> albumes = albumesDAO.obtenerAlbumesNombre(idUsuario, nombreBuscado);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenDireccion());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegociosException("No se pudieron obtener los albumes de la base de datos.");
        }
    }

    @Override
    public AlbumDTO obtenerAlbumPorNombre(String nombreBuscado) throws NegociosException {
        Album album = albumesDAO.obtenerAlbumNombre(nombreBuscado);

        if (album != null) {
            AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setId(album.getId().toString());
            albumDTO.setNombre(album.getNombre());
            albumDTO.setFechaLanzamiento(album.getFechaLanzamiento());
            albumDTO.setGenero(album.getGenero());
            albumDTO.setImagenPortada(album.getImagenDireccion());
            albumDTO.setIdArtista(album.getIdArtista().toString());
            if (album.getCanciones() != null) {
                List<CancionDTO> cancionesDTO = new ArrayList<>();

                for (Cancion c : album.getCanciones()) {
                    CancionDTO cancionDTO = new CancionDTO();
                    cancionDTO.setId(c.getId().toString());
                    cancionDTO.setTitulo(c.getTitulo());
                    cancionDTO.setDuracion(c.getDuracion());
                    cancionDTO.setIdArtista(c.getIdArtista().toString());

                    cancionesDTO.add(cancionDTO);
                }

                albumDTO.setCanciones(cancionesDTO);
            }

            return albumDTO;
        } else {
            throw new NegociosException("No se pudo obtener el album.");
        }
    }

    @Override
    public List<AlbumDTO> obtenerAlgumesPorFecha(String idUsuario, String fechaTexto) throws NegociosException {
        List<Album> albumes = albumesDAO.obtenerAlbumesFecha(idUsuario, fechaTexto);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenDireccion());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegociosException("No se pudieron obtener los albumes de la base de datos.");
        }

        
        
    }

    @Override
    public List<CancionDTO> obtenerCancionesPorIdAlbum(String idAlbum) throws NegociosException {
        List<Cancion> canciones = albumesDAO.obtenerCancionesIdAlbum(idAlbum);

        if (canciones != null) {
            List<CancionDTO> cancionesDTO = new ArrayList<>();

            for (Cancion cancion : canciones) {
                CancionDTO cancionDTO = new CancionDTO();
                cancionDTO.setId(cancion.getId().toString());
                cancionDTO.setTitulo(cancion.getTitulo());
                cancionDTO.setDuracion(cancion.getDuracion());
                cancionDTO.setIdArtista(cancion.getIdArtista().toString());

                cancionesDTO.add(cancionDTO);
            }

            return cancionesDTO;
        } else {
            throw new NegociosException("No se pudieron obtener las canciones del album.");
        }
    }

    @Override
    public CancionDTO buscarCancionPorId(String idCancion) {
        return albumesDAO.buscarCancionId(idCancion);
    }

    @Override
    public List<CancionDTO> buscarCancionesPorNombre(String nombre, String idUsuario) throws NegociosException {
        List<Document> documentos = albumesDAO.buscarCancionesNombre(nombre, idUsuario);
        
        List<CancionDTO> canciones = new ArrayList<>();
        
        if(documentos != null){
            for(Document d : documentos){
                CancionDTO dto = new CancionDTO();
                dto.setId(d.getObjectId("idCancion").toString());
                dto.setTitulo(d.getString("titulo"));
                dto.setDuracion(d.getDouble("duracion"));
                canciones.add(dto);
            }
        }
        return canciones;
    }

    @Override
    public List<CancionDTO> obtenerTodasLasCanciones(String idUsuario) {
        List<Document> documentos = albumesDAO.obtenerTodasLasCanciones(idUsuario);
        
        List<CancionDTO> canciones = new ArrayList<>();
        
        if(documentos != null){
            for(Document d : documentos){
                CancionDTO dto = new CancionDTO();
                dto.setId(d.getObjectId("idCancion").toString());
                dto.setTitulo(d.getString("titulo"));
                dto.setDuracion(d.getDouble("duracion"));
                canciones.add(dto);
            }
        }
        return canciones;
    }
    
}
    
    

