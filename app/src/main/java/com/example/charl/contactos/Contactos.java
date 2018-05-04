package com.example.charl.contactos;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Contactos implements Parcelable {
    private String name;
    private String Lname;
    private String numero;
    private String ID;
    private String adress;
    private String cumple;
    private String mail;
    private Boolean Check;
    private Uri imgconv;

    public Contactos(String name,Uri imgconv,String numero) {
        this.name = name;
        this.imgconv = imgconv;
        this.numero = numero;

    }


    public Contactos(String name, String lname, String numero, String ID,Uri imgconv, String cumple,String mail,String adress) {
        this.name = name;
        Lname = lname;
        this.numero = numero;
        this.ID = ID;
        this.imgconv = imgconv;
        this.cumple = cumple;
        this.mail = mail;
        this.adress = adress;
    }

    protected Contactos(Parcel in) {
        name = in.readString();
        Lname = in.readString();
        numero = in.readString();
        ID = in.readString();
        adress = in.readString();
        cumple = in.readString();
        mail = in.readString();
        byte tmpCheck = in.readByte();
        Check = tmpCheck == 0 ? null : tmpCheck == 1;
        imgconv = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Contactos> CREATOR = new Creator<Contactos>() {
        @Override
        public Contactos createFromParcel(Parcel in) {
            return new Contactos(in);
        }

        @Override
        public Contactos[] newArray(int size) {
            return new Contactos[size];
        }
    };

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCumple() {
        return cumple;
    }

    public void setCumple(String cumple) {
        this.cumple = cumple;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getCheck() {
        return Check;
    }

    public void setCheck(Boolean check) {
        Check = check;
    }

    public Uri getImgconv() {
        return imgconv;
    }

    public void setImgconv(Uri imgconv) {
        this.imgconv = imgconv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(Lname);
        dest.writeString(numero);
        dest.writeString(ID);
        dest.writeString(adress);
        dest.writeString(cumple);
        dest.writeString(mail);
        dest.writeByte((byte) (Check == null ? 0 : Check ? 1 : 2));
        dest.writeParcelable(imgconv, flags);
    }
}
