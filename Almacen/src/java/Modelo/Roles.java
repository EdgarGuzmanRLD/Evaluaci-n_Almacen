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
public class Roles {
    private int Id_Rol;
    private String nombreRol; 

    /**
     * @return the Id_Rol
     */
    public int getId_Rol() {
        return Id_Rol;
    }

    /**
     * @param Id_Rol the Id_Rol to set
     */
    public void setId_Rol(int Id_Rol) {
        this.Id_Rol = Id_Rol;
    }

    /**
     * @return the nombreRol
     */
    public String getNombreRol() {
        return nombreRol;
    }

    /**
     * @param nombreRol the nombreRol to set
     */
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
