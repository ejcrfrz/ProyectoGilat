package com.gilat.proyectogilat.Entidades;

public class Usuario {

    private String admi;

    public String getAdmi() {
        return admi;
    }

    public void setAdmi(String admi) {
        this.admi = admi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String email;
    private String name;
    private String pass;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    private String iduser;

}
