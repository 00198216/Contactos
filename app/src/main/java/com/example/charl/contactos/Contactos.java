package com.example.charl.contactos;

import java.io.Serializable;

public class Contactos implements Serializable {

    private String name;
    private String Lname;
    private String numero;
    private String ID;
    private int img;
    private String cumple;
    private String mail;
    private Boolean Check;

    public Contactos(String name) {
        this.name = name;

    }

    public Contactos(String name, String lname, String numero, String ID, int img, String cumple,String mail) {
        this.name = name;
        Lname = lname;
        this.numero = numero;
        this.ID = ID;
        this.img = img;
        this.cumple = cumple;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getCumple() {
        return cumple;
    }

    public void setCumple(String cumple) {
        this.cumple = cumple;
    }

    public Boolean getCheck() {
        return Check;
    }

    public void setCheck(Boolean check) {
        Check = check;
    }
}