/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabricas;

import Implementaciones.AlbumesBO;
import Implementaciones.ArtistaBO;
import Implementaciones.UsuariosBO;
import Implementaciones.UtilidadBO;
import Interfaces.IAlbumesBO;
import Interfaces.IArtistasBO;
import Interfaces.IUsuariosBO;
import Interfaces.IUtilidadBO;
import com.mycompany.sistemapersistenciamusica.Implementacion.AlbumesDAO;
import com.mycompany.sistemapersistenciamusica.Implementacion.ArtistasDAO;
import com.mycompany.sistemapersistenciamusica.Implementacion.UsuariosDAO;
import com.mycompany.sistemapersistenciamusica.Implementacion.UtilidadDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IAlbumesDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IArtistasDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUsuariosDAO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUtilidadDAO;

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
    
    public static IUtilidadBO crearUtilidadBO() {
        IUtilidadDAO UtilidadDAO = new UtilidadDAO();
        return new UtilidadBO(UtilidadDAO);
    }

}
