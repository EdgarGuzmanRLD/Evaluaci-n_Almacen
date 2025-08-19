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
public class Unidad_Medida {
    private int Id_Unidad;
    private String Unidad;

    public Unidad_Medida(int Id_Unidad, String Unidad) {
        this.Id_Unidad = Id_Unidad;
        this.Unidad =Unidad;
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

    /**
     * @return the Unidad
     */
    public String getUnidad() {
        return Unidad;
    }

    /**
     * @param Unidad the Unidad to set
     */
    public void setUnidad(String Unidad) {
        this.Unidad = Unidad;
    }
}
