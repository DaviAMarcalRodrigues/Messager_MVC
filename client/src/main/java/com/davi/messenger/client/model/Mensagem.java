package com.davi.messenger.client.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String texto;
    private final LocalDateTime horario;

    public Mensagem(String texto) {
        this(texto, LocalDateTime.now());
    }

    public Mensagem(String texto, LocalDateTime horario) {
        this.texto = texto;
        this.horario = horario;
    }

    public String getMensagem() {
        return texto;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getHorarioFormatado() {
        return horario.format(FORMATO);
    }
}
