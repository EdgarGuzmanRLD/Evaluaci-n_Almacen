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
public class Bitacora_Movimientos {
    private int Id_Bitacora;
    private String Producto;
    private String Usuario;
    private String tipo_Movimiento;
    private float cantidad_Movimiento;
    private String fecha_Movimiento;
    private String hora_Movimiento;

    public Bitacora_Movimientos(String Producto, String Usuario, String tipo_Movimiento, float cantidad_Movimiento, String fecha_Movimiento, String hora_Movimiento) {
        this.Producto = Producto;
        this.Usuario = Usuario;
        this.tipo_Movimiento = tipo_Movimiento;
        this.cantidad_Movimiento = cantidad_Movimiento;
        this.fecha_Movimiento = fecha_Movimiento;
        this.hora_Movimiento = hora_Movimiento;
    }

   
    /**
     * @return the Id_Bitacora
     */
    public int getId_Bitacora() {
        return Id_Bitacora;
    }

    /**
     * @param Id_Bitacora the Id_Bitacora to set
     */
    public void setId_Bitacora(int Id_Bitacora) {
        this.Id_Bitacora = Id_Bitacora;
    }

    /**
     * @return the Producto
     */
    public String getProducto() {
        return Producto;
    }

    /**
     * @param Producto the Producto to set
     */
    public void setProducto(String Producto) {
        this.Producto = Producto;
    }

    /**
     * @return the Usuario
     */
    public String getUsuario() {
        return Usuario;
    }

    /**
     * @param Usuario the Usuario to set
     */
    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    /**
     * @return the tipo_Movimiento
     */
    public String getTipo_Movimiento() {
        return tipo_Movimiento;
    }

    /**
     * @param tipo_Movimiento the tipo_Movimiento to set
     */
    public void setTipo_Movimiento(String tipo_Movimiento) {
        this.tipo_Movimiento = tipo_Movimiento;
    }

    /**
     * @return the cantidad_Movimiento
     */
    public float getCantidad_Movimiento() {
        return cantidad_Movimiento;
    }

    /**
     * @param cantidad_Movimiento the cantidad_Movimiento to set
     */
    public void setCantidad_Movimiento(float cantidad_Movimiento) {
        this.cantidad_Movimiento = cantidad_Movimiento;
    }

    /**
     * @return the fecha_Movimiento
     */
    public String getFecha_Movimiento() {
        return fecha_Movimiento;
    }

    /**
     * @param fecha_Movimiento the fecha_Movimiento to set
     */
    public void setFecha_Movimiento(String fecha_Movimiento) {
        this.fecha_Movimiento = fecha_Movimiento;
    }

    /**
     * @return the hora_Movimiento
     */
    public String getHora_Movimiento() {
        return hora_Movimiento;
    }

    /**
     * @param hora_Movimiento the hora_Movimiento to set
     */
    public void setHora_Movimiento(String hora_Movimiento) {
        this.hora_Movimiento = hora_Movimiento;
    }
    
    
}
