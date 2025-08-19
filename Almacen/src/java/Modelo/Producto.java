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
public class Producto {

    private int Id_Producto;
    private String nombre_Producto;
    private float cantidad;
    private int Id_Unidad;
    private String unidad;
    private int estatus;

    public Producto(int Id_Producto, String nombre_Producto, float cantidad, int Id_Unidad, String unidad, int estatus) {
        this.Id_Producto = Id_Producto;
        this.nombre_Producto = nombre_Producto;
        this.cantidad = cantidad;
        this.Id_Unidad = Id_Unidad;
        this.unidad = unidad;
        this.estatus = estatus;
    }


/**
 * @return the Id_Producto
 */
public int getId_Producto() {
        return Id_Producto;
    }

    /**
     * @param Id_Producto the Id_Producto to set
     */
    public void setId_Producto(int Id_Producto) {
        this.Id_Producto = Id_Producto;
    }

    /**
     * @return the nombre_Producto
     */
    public String getNombre_Producto() {
        return nombre_Producto;
    }

    /**
     * @param nombre_Producto the nombre_Producto to set
     */
    public void setNombre_Producto(String nombre_Producto) {
        this.nombre_Producto = nombre_Producto;
    }

    /**
     * @return the cantidad
     */
    public float getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the Id_Unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param Id_Unidad the Id_Unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the estatus
     */
    public int getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the Id_Unidad
     */
    public int getId_Unidad() {
        return Id_Unidad;
    }

    /**
     * @param Id_Unidad the Id_Unidad to set
     */
    public void setId_Unidad(int Id_Unidad) {
        this.Id_Unidad = Id_Unidad;
    }
}
