/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemadominiomusica.Dominio.Favorito;
import sistemadominiomusica.Dominio.TipoContenido;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.FavoritoDTO;
import sistemadominiomusica.MusicaDtos.UsuarioDTO;

/**
 *
 * @author santi
 */
public class UsuariosDAO implements IUsuariosDAO {
    
    private final String COLECCION = "usuarios";
    private final String ID = "_id";
    private final String USERNAME = "username";
    private final String EMAIL = "email";
    private final String CONTRASENIA = "contrasenia";
    private final String IMAGEN_RUTA = "imagenPerfil";
    private final String FAVORITOS = "favoritos";
    private final String RESTRICCIONES = "restricciones";
    private final String IDFAVORITO = "idContenido";
    private final String TIPO_CONTENIDO = "tipoContenido";
    private final String FECHA_AGREGACION = "fechaAgregacion";
    
    @Override
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(nuevoUsuario.getUsername());
            usuario.setEmail(nuevoUsuario.getEmail());
            usuario.setContrasenia(hashearContrasenia(nuevoUsuario.getContrasenia()));
            if (nuevoUsuario.getImagenPerfil() != null) {
                usuario.setImagenPerfil(nuevoUsuario.getImagenPerfil());
            } else {
                usuario.setImagenPerfil("userImages/userPlaceholder.jpg");
            }
           
            if (nuevoUsuario.getRestricciones() != null) {
                usuario.setRestricciones(nuevoUsuario.getRestricciones());
            }

            coleccion.insertOne(usuario);

            return usuario;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error con la contraseña" + e);
            return null;
        }
    }
    @Override
    public Usuario consultarPorId(String idUsuario) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Document filtros = new Document();
        filtros.append(ID, new ObjectId(idUsuario));
        FindIterable<Usuario> usuarios = coleccion.find(filtros);
        Usuario usuario = usuarios.first();

        return usuario;
    }

    @Override
    public Usuario consultarPorUsername(String username) {
         MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Document filtros = new Document();
        filtros.append(USERNAME, username);
        FindIterable<Usuario> usuarios = coleccion.find(filtros);
        Usuario usuario = usuarios.first();
        return usuario;
    }

    @Override
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Document camposActualizar = new Document();
        if (datosActualizados.getUsername() != null) {
            camposActualizar.append(USERNAME, datosActualizados.getUsername());
        }
        if (datosActualizados.getEmail() != null) {
            camposActualizar.append(EMAIL, datosActualizados.getEmail());
        }
        if (datosActualizados.getContrasenia() != null) {
            try {
                camposActualizar.append(CONTRASENIA, hashearContrasenia(datosActualizados.getContrasenia()));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error con la contraseña " + e.getMessage());
                return null;
            }
        }
        if (datosActualizados.getImagenPerfil() != null) {
            camposActualizar.append(IMAGEN_RUTA, datosActualizados.getImagenPerfil());
        }
        if (camposActualizar.isEmpty()) {
            System.out.println("No hay Se puede actulizar sin campos");
            return null;
        }
        Document actualizacion = new Document("$set", camposActualizar);
        coleccion.updateOne(Filters.eq(ID, new ObjectId(idUsuario)), actualizacion);
        return consultarPorId(idUsuario);
    }

    @Override
    public Usuario InicioSesion(String username, String contrasenia) {
        MongoDatabase baseDatos = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Document filtro = new Document(USERNAME, username);
        FindIterable<Usuario> resultado = coleccion.find(filtro);
        Usuario usuario = resultado.first();
        if (usuario != null){
            try {
                String contraseniaBD = usuario.getContrasenia();
                String contraseniaEncriptadaIngresada = hashearContrasenia(contrasenia);
                if (contraseniaBD.equals(contraseniaEncriptadaIngresada)){
                    return usuario;
                } else {
                    System.err.println("Contrasenia incorrecta.");
                }
            } catch (NoSuchAlgorithmException e){
                System.err.println("Error al encriptar la contrasenia: " + e.getMessage());
            }
        } else {
            System.err.println("Correo no registrado.");
        }
        
        return null;
    }

    @Override
    public void agregarRestringido(String idUsuario, String genero) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection(COLECCION);
        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Document usuario = coleccion.find(filtro).first(); 
        if (usuario == null) {
            return; 
        }
        List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());
        List<Document> aEliminar = new ArrayList<>();
        for (Document fav : favoritos) {
            if (genero.equals(fav.getString("genero"))) {
                aEliminar.add(fav);
            }
        if (!aEliminar.isEmpty()) {
            coleccion.updateOne(filtro, new Document("$pull", new Document("favoritos", new Document("genero", genero))));
        }
        List<String> generos = usuario.getList("restricciones", String.class);
        if (generos == null) {
            generos = new ArrayList<>(); 
        if (generos.contains(genero)) {
            return; 
        }
        coleccion.updateOne(
            filtro,
            new Document("$addToSet", new Document("restricciones", genero))
        );

            }
        }
    }

    @Override
    public void eliminarRestringido(String idUsuario, String genero) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Document update = new Document("$pull", new Document("restricciones", genero));
        coleccion.updateOne(filtro, update);
    }

    @Override
    public List<String> mostrarRestringidos(String idUsuario) {
         MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        
        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Usuario usuario = coleccion.find(filtro).first();

        if (usuario != null && usuario.getRestricciones()!= null) {
            return usuario.getRestricciones();
        } else {
            return new ArrayList<>();
        }
    }
    
    /**
     * Hashea una contraseña usando SHA-256
     *
     * @param contrasenia Contraseña original
     * @return Contraseña hasheada en base64
     */
    public static String hashearContrasenia(String contrasenia) throws NoSuchAlgorithmException {
        
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(contrasenia.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        
    }

     @Override
    public boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        Document nuevoFavorito = new Document()
                .append("_id", new ObjectId())
                .append("idContenido", new ObjectId(favoritoDTO.getIdContenido()))
                .append("tipo", favoritoDTO.getTipo().name())
                .append("nombre", favoritoDTO.getNombreContenido())
                .append("genero", favoritoDTO.getGeneroContenido())
                .append("fechaAgregacion", favoritoDTO.getFechaAgregacion());

        Document filtroDuplicado = new Document("_id", new ObjectId(idUsuario))
                .append("favoritos.idContenido", new ObjectId(favoritoDTO.getIdContenido()))
                .append("favoritos.tipo", favoritoDTO.getTipo().name());

        Document usuarioExistente = usuarios.find(filtroDuplicado).first();
        if (usuarioExistente != null) {
            return false; // Ya está en favoritos
        }

        UpdateResult resultado = usuarios.updateOne(
            Filters.eq("_id", new ObjectId(idUsuario)),
            Updates.push("favoritos", nuevoFavorito)
        );

        return resultado.getModifiedCount() > 0;
    }

     /**
     * Elimina un contenido de la lista de favoritos del usuario.
     *
     * @param idUsuario ID del usuario.
     * @param idContenido ID del contenido a eliminar de favoritos.
     * @return true si se eliminó correctamente, false si no se modificó nada.
     */
    @Override
    public boolean eliminarFavorito(String idUsuario, String idContenido) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        Document favorito = new Document()
            .append("idContenido",new ObjectId(idContenido));

        UpdateResult resultado = usuarios.updateOne(
            Filters.eq("_id", new ObjectId(idUsuario)),
            Updates.pull("favoritos", favorito)
        );

        return resultado.getModifiedCount() > 0;
    }
    
    /**
    * Obtiene los artistas marcados como favoritos por el usuario cuyo nombre coincida parcialmente.
    *
    * @param idUsuario ID del usuario.
    * @param nombreArtista Nombre parcial o completo del artista.
    * @return Lista de documentos con información detallada del artista favorito.
    */
    @Override
    public List<Document> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.ARTISTA.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreArtista + ".*", "i")
        )),
        Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idArtista", "$favoritos.idContenido"),    
            Projections.computed("tipoArtista","$artistaInfo.tipo"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("generoArtista", "$artistaInfo.genero"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
    );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;
    }

    /**
    * Obtiene los álbumes marcados como favoritos por el usuario cuyo nombre coincida parcialmente.
    *
    * @param idUsuario ID del usuario.
    * @param nombreAlbum Nombre parcial o completo del álbum.
    * @return Lista de documentos con información detallada del álbum favorito.
    */
    @Override
    public List<Document> obtenerAlbumesFavoritos(String idUsuario, String nombreAlbum ) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.ALBUM.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreAlbum + ".*", "i")
        )),
        Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"),
        Aggregates.unwind("$albumInfo"),
        Aggregates.lookup("artistas", "albumInfo.idArtista", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idAlbum", "$favoritos.idContenido"),
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaLanzamiento", "$albumInfo.fechaLanzamiento"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
    );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;

               
    }
    
    /**
    * Obtiene las canciones marcadas como favoritas por el usuario cuyo título coincida parcialmente.
    *
    * @param idUsuario ID del usuario.
    * @param nombreCancion Nombre parcial o completo de la canción.
    * @return Lista de documentos con información detallada de la canción favorita.
    */
    @Override
    public List<Document> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.CANCION.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreCancion + ".*", "i")
        )),
        Aggregates.lookup("canciones", "favoritos.idContenido", "_id", "cancionInfo"),
        Aggregates.unwind("$cancionInfo"),
        Aggregates.lookup("albumes", "cancionInfo.idAlbum", "_id", "albumInfo"),
        Aggregates.unwind("$albumInfo"),
        Aggregates.lookup("artistas", "cancionInfo.idArtista", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idCancion", "$favoritos.idContenido"),
            Projections.computed("titulo", "$cancionInfo.titulo"),
            Projections.computed("duracion", "$cancionInfo.duracion"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
        );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;
        

    }
    
    /**
    * Obtiene todos los favoritos del usuario con un género específico.
    *
    * @param idUsuario ID del usuario.
    * @param genero Género a buscar dentro de los favoritos.
    * @return Lista de documentos que representan los favoritos con ese género.
    */
    @Override
    public List<Document> obtenerGenerosFavoritos(String idUsuario, String genero) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION); 

        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();
         if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();

    }
    
    /**
    * Consulta los favoritos agregados por el usuario dentro de un rango de fechas.
    *
    * @param idUsuario ID del usuario.
    * @param fechaInicio Fecha inicial del rango.
    * @param fechaFin Fecha final del rango.
    * @return Lista de documentos de favoritos en ese rango de fechas.
    */
    @Override
    public List<Document> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();

        if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();
       
    }
    
    /**
    * Obtiene todos los favoritos del usuario.
    *
    * @param idUsuario ID del usuario.
    * @return Lista de documentos que representan todos los favoritos del usuario.
    */
    @Override
    public List<Document> obtenerTodosFavoritos(String idUsuario) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        
        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();
        if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();
       
    }

    /**
    * Consulta los favoritos del usuario utilizando objetos de tipo Favorito.
    *
    * @param idUsuario ID del usuario.
    * @return Lista de objetos Favorito del usuario.
    */
    @Override
    public List<Favorito> consultarFavoritos(String idUsuario) {
        MongoDatabase db = ManejadorDeConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        Usuario usuario = coleccion.find(Filters.eq("_id", new ObjectId(idUsuario))).first();

        if (usuario != null) {
            return usuario.getFavoritos();
        }
        return new ArrayList<>();
    }
    
}
