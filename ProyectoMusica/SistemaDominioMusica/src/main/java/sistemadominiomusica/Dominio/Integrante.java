/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadominiomusica.Dominio;

import java.util.Date;
import org.bson.types.ObjectId;
/**
 *
 * @author santi
 */
public class Integrante {
    ObjectId id; //no va esto
    private String nombre;
    private IntegranteRol rol;
    private Date fechaIngreso;
    private Date fechaSalida; // puede ser null
    private boolean activo;

    public Integrante() {
    }
    
    public Integrante(String nombre, IntegranteRol rol, Date fechaIngreso, Date fechaSalida, boolean activo) {
        this.nombre = nombre;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.activo = activo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
