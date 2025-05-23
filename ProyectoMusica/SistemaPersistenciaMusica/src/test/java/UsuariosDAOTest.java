/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.sistemapersistenciamusica.Implementacion.UsuariosDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sistemadominiomusica.Dominio.Usuario;
import sistemadominiomusica.MusicaDtos.UsuarioDTO;

/**
 *
 * @author santi
 */
public class UsuariosDAOTest {
    
    public UsuariosDAOTest() {
    }
    
    private Usuario usuarioGuardado;
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    
    @Test
    public void testAgregarUsuario() {
        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                "BartoloXD",
                "bartoloSabritones@gmail.com",
                "123contrasenia123",
                null,
                null
        );

        Usuario usuarioAgregado = usuariosDAO.agregarUsuario(nuevoUsuario);
        usuarioGuardado = usuarioAgregado; // Comentar esta linea para ver en MongoDB

        assertEquals("BartoloXD", usuarioAgregado.getUsername());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
