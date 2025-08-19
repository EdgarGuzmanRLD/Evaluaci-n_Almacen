/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author edgar
 */
public class Permisos {
    private int Id_Permiso;
    private String nombre_Permiso;

    /**
     * @return the Id_Permiso
     */
    public int getId_Permiso() {
        return Id_Permiso;
    }

    /**
     * @param Id_Permiso the Id_Permiso to set
     */
    public void setId_Permiso(int Id_Permiso) {
        this.Id_Permiso = Id_Permiso;
    }

    /**
     * @return the nombre_Permiso
     */
    public String getNombre_Permiso() {
        return nombre_Permiso;
    }

    /**
     * @param nombre_Permiso the nombre_Permiso to set
     */
    public void setNombre_Permiso(String nombre_Permiso) {
        this.nombre_Permiso = nombre_Permiso;
    }
    
}
