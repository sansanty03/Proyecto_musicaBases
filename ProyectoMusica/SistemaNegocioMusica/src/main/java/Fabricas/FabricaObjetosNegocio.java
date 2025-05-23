/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabricas;

import Implementaciones.AlbumesBO;
import Implementaciones.ArtistaBO;
import Implementaciones.UsuariosBO;
import Interfaces.IAlbumesBO;
import Interfaces.IArtistasBO;
import Interfaces.IUsuariosBO;
import com.mycompany.sistemapersistenciamusica.Implementacion.AlbumesDAO;
import com.mycompany.sistemapersistenciamusica.Implementacion.ArtistasDAO;
import com.mycompany.sistemapersistenciamusica.Implementacion.UsuariosDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IAlbumesDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IArtistasDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;

/**
 *
 * @author santi
 */
public class FabricaObjetosNegocio {
    public static IArtistasBO crearArtistasBO() {
        IArtistasDAO artistasDAO = new ArtistasDAO();
        return new ArtistaBO(artistasDAO);
    }
    
    public static IUsuariosBO crearUsuariosBO() {
        IUsuariosDAO usuariosDAO = new UsuariosDAO();
        return new UsuariosBO(usuariosDAO);
    }
    
    public static IAlbumesBO crearAlbumesBO() {
        IAlbumesDAO albumesDAO = new AlbumesDAO();
        return new AlbumesBO(albumesDAO);
    }

}
