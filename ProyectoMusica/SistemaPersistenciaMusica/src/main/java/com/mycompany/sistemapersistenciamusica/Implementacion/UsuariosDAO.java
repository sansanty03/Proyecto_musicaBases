/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemapersistenciamusica.Implementacion;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemadominiomusica.Dominio.Usuario;
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
    
}
