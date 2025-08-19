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
public class Usuario {
    private int Id_Usuario;
    private String nombre_Usuario;
    private String contraseña;
    private int id_Rol;
    private String correo;

    public Usuario() { }

    /**
     * @return the Id_Usuario
     */
    public int getId_Usuario() {
        return Id_Usuario;
    }

    /**
     * @param Id_Usuario the Id_Usuario to set
     */
    public void setId_Usuario(int Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    /**
     * @return the nombre_Usuario
     */
    public String getNombre_Usuario() {
        return nombre_Usuario;
    }

    /**
     * @param nombre_Usuario the nombre_Usuario to set
     */
    public void setNombre_Usuario(String nombre_Usuario) {
        this.nombre_Usuario = nombre_Usuario;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * @return the id_Rol
     */
    public int getId_Rol() {
        return id_Rol;
    }

    /**
     * @param id_Rol the id_Rol to set
     */
    public void setId_Rol(int id_Rol) {
        this.id_Rol = id_Rol;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
