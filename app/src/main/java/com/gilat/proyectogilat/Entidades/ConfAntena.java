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

    private String modelAntena;
    private String modulacion;
    private String ganancia;
    private String region;

    public String getModelAntena() {
        return modelAntena;
    }

    public void setModelAntena(String modelAntena) {
        this.modelAntena = modelAntena;
    }

    public String getModulacion() {
        return modulacion;
    }

    public void setModulacion(String modulacion) {
        this.modulacion = modulacion;
    }

    public String getGanancia() {
        return ganancia;
    }

    public void setGanancia(String ganancia) {
        this.ganancia = ganancia;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }




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
