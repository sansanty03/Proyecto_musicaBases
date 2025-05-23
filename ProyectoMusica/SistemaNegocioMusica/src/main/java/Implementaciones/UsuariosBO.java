/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Exceptions.NegociosException;
import Interfaces.IUsuariosBO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;
import java.util.List;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;
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
    
    
    
}
