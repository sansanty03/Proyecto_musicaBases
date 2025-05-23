/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.NegociosException;
import java.util.List;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.AlbumDTO;
import sistemadominiomusica.MusicaDtos.CancionDTO;
import sistemadominiomusica.MusicaDtos.UsuarioDTO;

/**
 *
 * @author santi
 */
public interface IUsuariosBO {
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegociosException;

    public UsuarioDTO consultarPorId(String idUsuario);

    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) throws NegociosException;

    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegociosException;
    
    public abstract void agregarGeneroRestringido(String idUsuario, String genero);

    public abstract void eliminarGeneroRestringido(String idUsuario, String genero);

    public abstract List<String> mostrarGenerosRestringidos(String idUsuario);
}
