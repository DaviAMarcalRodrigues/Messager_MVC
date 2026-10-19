package com.davi.messenger.client.model;

public class Mensagem {
    private String messagem;
    private int horario;

    public Mensagem(String messagem, int horario) {
        this.messagem = messagem;
        this.horario = horario;
    }

    public String getMensagem() {
        return messagem;
    }

    public int getHorario() {
        return horario;
    }
}
