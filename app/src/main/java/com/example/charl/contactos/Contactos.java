package com.example.charl.contactos;

import java.io.Serializable;

public class Contactos implements Serializable {

    private String name;
    private String Lname;
    private String numero;
    private String ID;
    private int img;
    private int cumple;
    private Boolean Check;

    public Contactos(String name) {
        this.name = name;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getCumple() {
        return cumple;
    }

    public void setCumple(int cumple) {
        this.cumple = cumple;
    }

    public Boolean getCheck() {
        return Check;
    }

    public void setCheck(Boolean check) {
        Check = check;
    }
}