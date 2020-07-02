package com.gilat.proyectogilat.Entidades;

public class ConfAntena {

    private String nombre;
    private String frecuencia;
    private String polarizacion;
    private String potenciaRx;
    private String potenciaRt;
    private double latitud;
    private double longitud;
    private String flag;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getPolarizacion() {
        return polarizacion;
    }

    public void setPolarizacion(String polarizacion) {
        this.polarizacion = polarizacion;
    }

    public String getPotenciaRx() {
        return potenciaRx;
    }

    public void setPotenciaRx(String potenciaRx) {
        this.potenciaRx = potenciaRx;
    }

    public String getPotenciaRt() {
        return potenciaRt;
    }

    public void setPotenciaRt(String potenciaRt) {
        this.potenciaRt = potenciaRt;
    }


}
