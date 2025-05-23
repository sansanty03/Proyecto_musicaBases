/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.MusicaDtos;

import java.util.Date;
import sistemadominiomusica.Dominio.IntegranteRol;

/**
 *
 * @author santi
 */
public class IntegranteDTO {
    private String nombre;
    private IntegranteRol rol;
    private Date fechaIngreso;
    private Date fechaSalida;

    public IntegranteDTO() {
    }
    
    public IntegranteDTO(String nombre, IntegranteRol rol, Date fechaIngreso, Date fechaSalida) {
        this.nombre = nombre;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public IntegranteRol getRol() {
        return rol;
    }

    public void setRol(IntegranteRol rol) {
        this.rol = rol;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
    
    
    
}
