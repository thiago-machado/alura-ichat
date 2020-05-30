package br.com.caelum.ichat.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mensagem implements Serializable {

    @SerializedName("text")
    private String texto;

    @SerializedName("id")
    private int id;

    public Mensagem(int id, String texto) {
        this.texto = texto;
        this.id = id;
    }

    public String getText() {
        return texto;
    }

    public int getId() {
        return id;
    }
}