/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mycompany.sistemapersistenciamusica.Interfaces.IArtistasDAO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemadominiomusica.Dominio.Artista;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.ArtistaDTO;
import sistemadominiomusica.MusicaDtos.IntegranteDTO;

/**
 *
 * @author santi
 */
public class ArtistasDAO implements IArtistasDAO {
    
    private final String COLECCION = "artistas";
    private final String ID = "_id";
    private final String NOMBRE = "nombre";
    private final String GENERO = "genero";

    @Override
    public Artista registrarArtista(ArtistaDTO nuevoArtista) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        Artista artista = new Artista();
        artista.setNombre(nuevoArtista.getNombre());
        artista.setGenero(nuevoArtista.getGenero());
        artista.setTipo(nuevoArtista.getTipo());
        artista.setImagen(nuevoArtista.getImagen());
        coleccion.insertOne(artista);
        return artista;
    }

    @Override
    public List<Artista> buscarArtistasNombre(String idUsuario, String nombre) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        List<String> restricciones = obtenerRestringidos(idUsuario);
        Document filtros = new Document(NOMBRE, new Document("$regex", nombre).append("$options", "i"));
        if (!restricciones.isEmpty()) {
            filtros.append(GENERO, new Document("$nin", restricciones));
        }
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new LinkedList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public Artista buscarArtistaId(String idArtista) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        Document filtros = new Document();
        filtros.append(ID, new ObjectId(idArtista));
        FindIterable<Artista> artistas = coleccion.find(filtros);
        Artista artista = artistas.first();
        return artista;
    }

    @Override
    public List<Artista> buscarArtistasGenero(String idUsuario, String genero) {
        List<String> restricciones = obtenerRestringidos(idUsuario);
        if (restricciones.contains(genero)) {
            return new ArrayList<>(); 
        }
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        Document filtros = new Document();
        filtros.append(GENERO, genero);
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public List<Artista> buscarArtistasNombreGenero(String idUsuario, String nombre, String genero) {
        List<String> restricciones = obtenerRestringidos(idUsuario);
        if (restricciones.contains(genero)) {
            return new ArrayList<>();
        }
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = db.getCollection(COLECCION, Artista.class);
        Document filtro = new Document()
                .append(NOMBRE, new Document("$regex", nombre).append("$options", "i"))
                .append(GENERO, genero);
        FindIterable<Artista> resultados = coleccion.find(filtro);
        List<Artista> lista = new ArrayList<>();
        resultados.into(lista);
        return lista;
    }

    @Override
    public List<Artista> buscarArtistas(String idUsuario) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        List<String> restricciones = obtenerRestringidos(idUsuario);
        Document filtro = new Document();
        if (!restricciones.isEmpty()) {
            filtro.append(GENERO, new Document("$nin", restricciones));
        }
        FindIterable<Artista> resultados = coleccion.find(filtro);
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public Artista buscarArtistaNombre(String nombre) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        Document filtro = new Document();
        filtro.append(NOMBRE, nombre);
        FindIterable<Artista> resultados = coleccion.find(filtro);
        Artista artista = resultados.first();
        if (artista != null) {
            return artista;
        } else {
            return null;
        }
    }

    @Override
    public boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        Document integranteDoc = new Document("nombre", nuevoIntegrante.getNombre())
                .append("rol", nuevoIntegrante.getRol().toString())
                .append("fechaIngreso", nuevoIntegrante.getFechaIngreso());

        if (nuevoIntegrante.getFechaSalida() != null) {
            integranteDoc.append("fechaSalida", nuevoIntegrante.getFechaSalida());
        }
        ObjectId id = new ObjectId(idArtista);
        UpdateResult result = coleccion.updateOne(
                Filters.eq(ID, id),
                Updates.push("integrantes", integranteDoc));
        return result.getModifiedCount() > 0;
    }

    @Override
    public List<Document> consultarIntegrantes(String idArtista) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document(ID, new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);

        Document resultado = coleccion.find(filtro).projection(proyeccion).first();

        if (resultado != null && resultado.containsKey("integrantes")) {
            return (List<Document>) resultado.get("integrantes");
        }

        return new ArrayList<>();
    }

    @Override
    public List<Document> consultarIntegrantesActivos(String idArtista) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        Document filtro = new Document(ID, new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);
        Document resultado = coleccion.find(filtro).projection(proyeccion).first();
        List<Document> lista = new ArrayList<>();
        if (resultado != null && resultado.containsKey("integrantes")) {
            List<Document> docs = (List<Document>) resultado.get("integrantes");
            for (Document doc : docs) {
                if (!doc.containsKey("fechaFinal") || doc.get("fechaFinal") == null) {
                    lista.add(doc);
                }
            }
        }
        return lista;
    }
    private List<String> obtenerRestringidos(String idUsuario) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection("usuarios", Usuario.class);
        Usuario usuario = coleccion.find(new Document("_id", new ObjectId(idUsuario))).first();
        return (usuario != null && usuario.getRestricciones() != null)
                ? usuario.getRestricciones()
                : new ArrayList<>();
    }
    
}
