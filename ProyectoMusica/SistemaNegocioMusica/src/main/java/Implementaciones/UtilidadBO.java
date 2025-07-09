/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Interfaces.IUtilidadBO;
import com.mycompany.sistemapersistenciamusica.Interfaces.IUtilidadDAO;

/**
 *
 * @author LABCISCO-PC036
 */
public class UtilidadBO implements IUtilidadBO {

      IUtilidadDAO utilidadDAO;

    public UtilidadBO(IUtilidadDAO utilidadDAO) {
        this.utilidadDAO = utilidadDAO;
    }
    
    @Override
    public void insertarDatos(){
        utilidadDAO.insertarDatos();
    }
    
}
