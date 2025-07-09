/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Exceptions.NegociosException;
import Interfaces.IUsuariosBO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
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
public class UsuariosBO implements IUsuariosBO {
    private IUsuariosDAO usuariosDAO;

    public UsuariosBO(IUsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegociosException {
        String username = nuevoUsuario.getUsername();
        String email = nuevoUsuario.getEmail();
        String contrasenia = nuevoUsuario.getContrasenia();

        Usuario usuarioExistente = usuariosDAO.consultarPorUsername(username);

        if (usuarioExistente == null) {

            Usuario usuario = usuariosDAO.agregarUsuario(nuevoUsuario);

            if (usuario != null) {
                UsuarioDTO usuarioObtenido = new UsuarioDTO();
                usuarioObtenido.setUsername(usuario.getUsername());
                usuarioObtenido.setEmail(usuario.getEmail());
                usuarioObtenido.setContrasenia(usuario.getContrasenia());
                usuarioObtenido.setImagenPerfil(usuario.getImagenPerfil());
                usuarioObtenido.setRestricciones(usuario.getRestricciones());

                return usuarioObtenido;
            } else {
                throw new NegociosException("No fue posible registrar al usuario en la base de datos.");
            }

        } else {
            throw new NegociosException("El nombre de usuario '" + username + "' ya se encuentra registrado.");
        }
    }

    @Override
    public UsuarioDTO consultarPorId(String idUsuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        Usuario usuarioObtenido = usuariosDAO.consultarPorId(idUsuario);

        usuarioDTO.setUsername(usuarioObtenido.getUsername());
        usuarioDTO.setEmail(usuarioObtenido.getEmail());
        usuarioDTO.setContrasenia(usuarioObtenido.getContrasenia());
        usuarioDTO.setImagenPerfil(usuarioObtenido.getImagenPerfil());
        usuarioDTO.setRestricciones(usuarioObtenido.getRestricciones());

        return usuarioDTO;
    }

    @Override
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) throws NegociosException {
        if (datosActualizados == null) {
            throw new NegociosException("No se recibieron datos para actualizar. El objeto proporcionado es nulo.");
        }
        UsuarioDTO usuarioExistente = consultarPorId(idUsuario);
        if (usuarioExistente == null) {
            throw new NegociosException("No se encontró un usuario con el ID especificado para realizar la modificación.");
        }
        if (datosActualizados.getUsername() != null) {
            
            Usuario usuarioConMismoUsername = usuariosDAO.consultarPorUsername(datosActualizados.getUsername());
            if (usuarioConMismoUsername != null && !usuarioConMismoUsername.getId().toString().equals(idUsuario)) {
                throw new NegociosException("El nombre de usuario ingresado ya está siendo utilizado por otra cuenta.");
            }
        }
        if (datosActualizados.getEmail() != null) {
            
        }

        if (datosActualizados.getContrasenia() != null) {
            
        }
        if (datosActualizados.getUsername() == null
                && datosActualizados.getEmail() == null
                && datosActualizados.getContrasenia() == null
                && datosActualizados.getImagenPerfil() == null) {
            throw new NegociosException("Debe indicar al menos un campo válido para realizar la actualización del usuario.");
        }

        return usuariosDAO.modificarUsuario(idUsuario, datosActualizados);

    }

    @Override
    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegociosException {
        Usuario usuarioExistente = usuariosDAO.consultarPorUsername(username);

        if (usuarioExistente != null) {

            Usuario usuario = usuariosDAO.InicioSesion(username, contrasenia);

            if (usuario != null) {
                UsuarioDTO usuarioObtenido = new UsuarioDTO();
                usuarioObtenido.setId(usuario.getId().toString()); // Esto estaba en toHexString() si hay un error cambiarlo nuevamente
                usuarioObtenido.setUsername(usuario.getUsername());
                usuarioObtenido.setEmail(usuario.getEmail());
                usuarioObtenido.setContrasenia(usuario.getContrasenia());
                usuarioObtenido.setImagenPerfil(usuario.getImagenPerfil());
                usuarioObtenido.setRestricciones(usuario.getRestricciones());

                return usuarioObtenido;
            } else {
                throw new NegociosException("Credenciales incorrectas: el usuario o la contraseña no coinciden.");
            }
        } else {
            throw new NegociosException("Usuario no registrado en el sistema.");
        }
    }

    @Override
    public void agregarGeneroRestringido(String idUsuario, String genero) {
        usuariosDAO.agregarRestringido(idUsuario, genero);
    }

    @Override
    public void eliminarGeneroRestringido(String idUsuario, String genero) {
        usuariosDAO.eliminarRestringido(idUsuario, genero);
    }

    @Override
    public List<String> mostrarGenerosRestringidos(String idUsuario) {
        return usuariosDAO.mostrarRestringidos(idUsuario);
    }

    @Override
    public boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO) throws NegociosException {
        if (idUsuario == null || favoritoDTO == null) {
            throw new NegociosException("Debe seleccionar un usario y un contenido a agregar a favoritos.");
        }
        return usuariosDAO.agregarFavorito(idUsuario, favoritoDTO);
    }

 
    @Override
    public boolean eliminarFavorito(String idUsuario, String idContenido) throws NegociosException {
        if (idUsuario == null || idContenido == null) {
            throw new NegociosException("Debe seleccionar un usario y un contenido a eliminar favoritos.");
        }
        return usuariosDAO.eliminarFavorito(idUsuario, idContenido);
    }

    @Override
    public List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsuario, String nombreAlbum) {
        List<Document> documentos = usuariosDAO.obtenerAlbumesFavoritos(idUsuario, nombreAlbum);
        
        List<AlbumFavoritoDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            AlbumFavoritoDTO dto = new AlbumFavoritoDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdAlbum(doc.getObjectId("idAlbum").toString());
            dto.setNombreAlbum(doc.getString("nombreAlbum"));
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaLanzamiento(doc.getDate("fechaLanzamiento"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
             resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        List<Document> documentos = usuariosDAO.obtenerArtistasFavoritos(idUsuario, nombreArtista);
        
        List<ArtistaFavoritoDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            ArtistaFavoritoDTO dto = new ArtistaFavoritoDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdArtista(doc.getObjectId("idArtista").toString());
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setTipoArtista(doc.getString("tipoArtista"));
            dto.setGeneroArtista(doc.getString("generoArtista"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;
  
    }

    @Override
    public List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        
        List<Document> documentos = usuariosDAO.obtenerCancionesFavoritas(idUsuario, nombreCancion);
        
        List<CancionFavoritaDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            CancionFavoritaDTO dto = new CancionFavoritaDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdCancion(doc.getObjectId("idCancion").toString());
            dto.setTitulo(doc.getString("titulo"));
            dto.setDuracion(doc.getDouble("duracion").floatValue());
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setNombreAlbum(doc.getString("nombreAlbum"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;

    }

    @Override
    public List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero) {
        
        List<Document> documentos = usuariosDAO.obtenerGenerosFavoritos(idUsuario, genero);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();
        for (Document fav : documentos) {
                String generoFav = fav.getString("genero");
                if (generoFav != null && generoFav.toLowerCase().contains(genero.toLowerCase())) {
                    GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                    dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                    dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                    dto.setNombre(fav.getString("nombre"));
                    dto.setGenero(generoFav);
                    dto.setTipo(fav.getString("tipo"));
                    dto.setFechaAgregacion(fav.getDate("fechaAgregacion"));
                    resultado.add(dto);
                }
            }
        
        return resultado;
    }

    @Override
    public List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        
        List<Document> documentos = usuariosDAO.consultarFavoritosPorRangoFechas(idUsuario, fechaInicio, fechaFin);
        List<GeneroFavoritoDTO> resultados = new ArrayList<>();
        
        for (Document fav : documentos) {
                Date fecha = fav.getDate("fechaAgregacion");
                if (fecha != null && !fecha.before(fechaInicio) && !fecha.after(fechaFin)) {
                    GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                    dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                    dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                    dto.setNombre(fav.getString("nombre"));
                    dto.setGenero(fav.getString("genero"));
                    dto.setTipo(fav.getString("tipo"));
                    dto.setFechaAgregacion(fecha);
                    resultados.add(dto);
                }
            }
        
        return resultados;
    }

    @Override
    public List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario) {
        List<Document> documentos = usuariosDAO.obtenerTodosFavoritos(idUsuario);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();
        
        for (Document fav : documentos) {
                GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                dto.setNombre(fav.getString("nombre"));
                dto.setGenero(fav.getString("genero"));
                dto.setTipo(fav.getString("tipo"));
                dto.setFechaAgregacion(fav.getDate("fechaAgregacion"));
                resultado.add(dto);
               } 
        return resultado;
    }
    
    @Override
    public List<Favorito> consultarFavoritos(String idUsuario) {
        return usuariosDAO.consultarFavoritos(idUsuario);
    }
    
    
    
}
