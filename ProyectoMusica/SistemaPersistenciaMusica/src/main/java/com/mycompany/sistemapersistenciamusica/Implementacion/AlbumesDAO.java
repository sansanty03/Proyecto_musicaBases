/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mycompany.sistemapersistenciamusica.Interfaces.IAlbumesDAO;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemadominiomusica.Dominio.Album;
import sistemadominiomusica.Dominio.Cancion;
import sistemadominiomusica.Dominio.Genero;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;

/**
 *
 * @author santi
 */
public class AlbumesDAO implements IAlbumesDAO {
    
    private final String COLECCION = "albumes";
    private final String ID = "_id";
    private final String NOMBRE = "nombre";
    private final String FECHA_LANZAMIENTO = "fechaLanzamiento";
    private final String GENERO = "genero";
    private final String IMAGEN_PORTADA = "imagenPortada";
    private final String ID_ARTISTA = "idArtista";
    private final String CANCIONES = "canciones";
    private final String ARTISTA_INFO = "artistaInfo";

    @Override
    public Album agregarAlbum(AlbumDTO nuevoAlbum) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Album> coleccion = baseDatos.getCollection(COLECCION, Album.class);
        try {
            Album album = new Album();
            album.setNombre(nuevoAlbum.getNombre());
            album.setFechaLanzamiento(nuevoAlbum.getFechaLanzamiento());
            album.setGenero(nuevoAlbum.getGenero());
            album.setImagenDireccion(nuevoAlbum.getImagenPortada());
            album.setIdArtista(new ObjectId(nuevoAlbum.getIdArtista()));
            if (nuevoAlbum.getCanciones() != null
                    && !nuevoAlbum.getCanciones().isEmpty()) {
                List<Cancion> canciones = new ArrayList<>();
                for (CancionDTO cancion : nuevoAlbum.getCanciones()) {
                    Cancion nuevaCancion = new Cancion();
                    nuevaCancion.setId(new ObjectId());
                    nuevaCancion.setTitulo(cancion.getTitulo());
                    nuevaCancion.setDuracion(cancion.getDuracion());
                    if (cancion.getIdArtista() != null) {
                        nuevaCancion.setIdArtista(new ObjectId(cancion.getIdArtista()));
                    }
                    canciones.add(nuevaCancion);
                }
                album.setCanciones(canciones);
            } else {
                album.setCanciones(new ArrayList<>());
            }
            coleccion.insertOne(album);
            return album;
        } catch (Exception e) {
            System.out.println("Error al agregar el album: " + e);
            return null;
        }
    }

    @Override
    public List<Album> obtenerAlbumes(String idUsuario) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        List<Bson> pipeline = new ArrayList<>();
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
        }
        pipeline.addAll(Arrays.asList(
            Aggregates.lookup("artistas", ID_ARTISTA, ID, ARTISTA_INFO),
            Aggregates.unwind("$" + ARTISTA_INFO),
            Aggregates.project(Projections.fields(
                Projections.computed("id", "$" + ID),
                Projections.include(NOMBRE, FECHA_LANZAMIENTO, GENERO, IMAGEN_PORTADA),
                Projections.computed(ID_ARTISTA, "$" + ID_ARTISTA),
                Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                 Projections.include(CANCIONES)
            ))
        ));
        return consultaAlbumes(pipeline, coleccion);
    }

    @Override
    public List<Album> obtenerAlbumesGenero(String idUsuario, String generoBuscado) {
         MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.regex(GENERO, ".*" + generoBuscado + ".*", "i")));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
        }
        pipeline.addAll(Arrays.asList(
                Aggregates.lookup("artistas", ID_ARTISTA, ID, ARTISTA_INFO),
                Aggregates.unwind("$" + ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + ID),
                        Projections.include(NOMBRE, FECHA_LANZAMIENTO,GENERO, IMAGEN_PORTADA),
                        Projections.computed(ID_ARTISTA, "$" + ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                        Projections.include(CANCIONES)
                ))
        ));

        return consultaAlbumes(pipeline, coleccion);
    }

    @Override
    public List<Album> obtenerAlbumesNombre(String idUsuario, String nombreBuscado) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);

        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.regex(NOMBRE, ".*" + nombreBuscado + ".*", "i")));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
        }
        pipeline.addAll(Arrays.asList(
                Aggregates.lookup("artistas", ID_ARTISTA, ID, ARTISTA_INFO),
                Aggregates.unwind("$" + ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + ID),
                        Projections.include(NOMBRE, FECHA_LANZAMIENTO, GENERO, IMAGEN_PORTADA),
                        Projections.computed(ID_ARTISTA, "$" + ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                        Projections.include(CANCIONES)
                ))
        ));

        return consultaAlbumes(pipeline, coleccion);
    }

    @Override
    public Album obtenerAlbumNombre(String nombreBuscado) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq(NOMBRE, nombreBuscado)),
                Aggregates.lookup("artistas", ID_ARTISTA, ID, ARTISTA_INFO),
                Aggregates.unwind("$" + ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + ID),
                        Projections.include(NOMBRE, FECHA_LANZAMIENTO, GENERO, IMAGEN_PORTADA),
                        Projections.computed(ID_ARTISTA, "$" + ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                        Projections.include(CANCIONES)
                )),
                Aggregates.limit(1)
        );

        List<Album> resultado = consultaAlbumes(pipeline, coleccion);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public List<Album> obtenerAlbumesFecha(String idUsuario, String fechaTexto) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        ZonedDateTime inicioZdt, finZdt;
        try {
            LocalDate fechaLocal = LocalDate.parse(fechaTexto);
            inicioZdt = fechaLocal.atStartOfDay(ZoneOffset.UTC);
            finZdt = fechaLocal.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1);
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida: " + fechaTexto);
            return new ArrayList<>();
        }
        Date inicio = Date.from(inicioZdt.toInstant());
        Date fin = Date.from(finZdt.toInstant());
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.and(
                Filters.gte(FECHA_LANZAMIENTO, inicio),
                Filters.lte(FECHA_LANZAMIENTO, fin)
        )));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
        }
        pipeline.addAll(Arrays.asList(
                Aggregates.lookup("artistas", ID_ARTISTA, ID, ARTISTA_INFO),
                Aggregates.unwind("$" + ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + ID),
                        Projections.include(NOMBRE, FECHA_LANZAMIENTO, GENERO, IMAGEN_PORTADA),
                        Projections.computed(ID_ARTISTA, "$" + ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                        Projections.include(CANCIONES)
                ))
        ));
        return consultaAlbumes(pipeline, coleccion);
    }

    @Override
    public List<Cancion> obtenerCancionesIdAlbum(String idAlbum) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        ObjectId objectId;
        try {
            objectId = new ObjectId(idAlbum);
        } catch (IllegalArgumentException e) {
            System.out.println("ID de album invalido:" + idAlbum);
            return new ArrayList<>();
        }
        Document filtro = new Document();
        filtro.append("_id", objectId);
        Document albumDoc = coleccion.find(filtro).first();
        if (albumDoc == null) {
            System.out.println("No se encontro el album con ID: " + idAlbum);
            return new ArrayList<>();
        }
        List<Cancion> canciones = new ArrayList<>();
        List<Document> cancionesDoc = (List<Document>) albumDoc.get(CANCIONES, List.class);
        if (cancionesDoc != null){
            for (Document c : cancionesDoc) {
                Cancion cancion = new Cancion();
                if (c.containsKey(ID)) {
                    cancion.setId(c.getObjectId(ID));
                }
                cancion.setTitulo(c.getString("titulo"));
                cancion.setDuracion(((Number) c.get("duracion")).doubleValue());
                if (c.containsKey(ID_ARTISTA)) {
                    cancion.setIdArtista(c.getObjectId(ID_ARTISTA));
                }
                canciones.add(cancion);
            }
        }
        return canciones;
    }

    @Override
    public CancionDTO buscarCancionId(String idCancion) {
         MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);
        ObjectId id = new ObjectId(idCancion);
        List<Bson> pipeline = Arrays.asList(
            Aggregates.unwind("$canciones"),
            Aggregates.match(Filters.eq("canciones._id", id)),
            Aggregates.project(Projections.fields(
                Projections.include("canciones"),
                Projections.computed("idArtista", "$idArtista")
            ))
        );
        AggregateIterable<Document> resultado = albumes.aggregate(pipeline);
        Document doc = resultado.first();
        if (doc == null) return null;
        Document cancionDoc = (Document) doc.get("canciones");
        CancionDTO dto = new CancionDTO();
        dto.setId(cancionDoc.getObjectId("_id").toHexString());
        dto.setTitulo(cancionDoc.getString("titulo"));
        dto.setDuracion(cancionDoc.getDouble("duracion"));
        ObjectId idArtista = cancionDoc.getObjectId("idArtista");
        if (idArtista != null) {
            dto.setIdArtista(idArtista.toHexString());
        }
        return dto;
    }

    @Override
    public List<Document> buscarCancionesNombre(String nombre, String idUsuario) {
         MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);
        
        List<Bson> pipeline = new ArrayList<>();

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
        }

        pipeline.addAll(Arrays.asList(
            Aggregates.unwind("$" + CANCIONES),
            Aggregates.match(Filters.regex(CANCIONES + ".titulo", Pattern.compile(nombre, Pattern.CASE_INSENSITIVE))),
            Aggregates.lookup("artistas", ID_ARTISTA, "_id", ARTISTA_INFO),
            Aggregates.unwind("$" + ARTISTA_INFO),
            Aggregates.project(Projections.fields(
                Projections.computed("idCancion", "$" + CANCIONES + "._id"),
                Projections.computed("titulo", "$" + CANCIONES + ".titulo"),
                Projections.computed("duracion", "$" + CANCIONES + ".duracion"),
                Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                Projections.computed("idAlbum", "$_id"),
                Projections.computed("nombreAlbum", "$nombre")
            ))
        ));

        return albumes.aggregate(pipeline).into(new ArrayList<>());
    }

    @Override
    public List<Document> obtenerTodasLasCanciones(String idUsuario) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
            MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);
            List<Bson> pipeline = new ArrayList<>();

            List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
            if (!restricciones.isEmpty()) {
                pipeline.add(Aggregates.match(Filters.nin(GENERO, restricciones)));
            }

            pipeline.addAll(Arrays.asList(
                Aggregates.unwind("$" + CANCIONES), //permite que la cancion sea un documento separado
                Aggregates.lookup("artistas", ID_ARTISTA, "_id", ARTISTA_INFO),
                Aggregates.unwind("$" + ARTISTA_INFO), //permite acceder a los datos del artista luego de un lookup
                Aggregates.project(Projections.fields(
                    Projections.computed("idCancion", "$" + CANCIONES + "._id"),
                    Projections.computed("titulo", "$" + CANCIONES + ".titulo"),
                    Projections.computed("duracion", "$" + CANCIONES + ".duracion"),
                    Projections.computed("nombreArtista", "$" + ARTISTA_INFO + ".nombre"),
                    Projections.computed("nombreAlbum", "$nombre")
                ))
            ));

            return albumes.aggregate(pipeline).into(new ArrayList<>());
    }
    
    private List<Album> consultaAlbumes(List<Bson> pipeline, MongoCollection<Document> coleccion) {
        List<Album> resultado = new ArrayList<>();

        try (MongoCursor<Document> cursor = coleccion.aggregate(pipeline).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Album album = new Album();
                album.setId(doc.getObjectId("id"));
                album.setNombre(doc.getString(NOMBRE));
                album.setFechaLanzamiento(doc.getDate(FECHA_LANZAMIENTO));
                album.setGenero(Genero.valueOf(doc.getString(GENERO)));
                album.setImagenDireccion(doc.getString(IMAGEN_PORTADA));
                album.setIdArtista(doc.getObjectId(ID_ARTISTA));
                List<Cancion> canciones = new ArrayList<>();
                List<Document> cancionesDoc = (List<Document>) doc.get(CANCIONES, List.class);
                if (cancionesDoc != null) {
                    for (Document c : cancionesDoc) {
                        Cancion cancion = new Cancion();
                        if (c.containsKey(ID)) {
                            cancion.setId(c.getObjectId(ID));
                        }
                        cancion.setTitulo(c.getString("titulo"));
                        cancion.setDuracion(((Number) c.get("duracion")).doubleValue());
                        if (c.containsKey(ID_ARTISTA)) {
                            cancion.setIdArtista(c.getObjectId(ID_ARTISTA));
                        }
                        canciones.add(cancion);
                    }
                }
                album.setCanciones(canciones);
                resultado.add(album);
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta de albumes: " + e);
        }
        return resultado;
    }
    
    private List<String> obtenerGenerosRestringidos(String idUsuario) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection("usuarios", Usuario.class);
        Usuario usuario = coleccion.find(new Document("_id", new ObjectId(idUsuario))).first();
        return (usuario != null && usuario.getRestricciones() != null)
                ? usuario.getRestricciones()
                : new ArrayList<>();
    }
}
