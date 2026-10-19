package com.davi.messenger.client.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.davi.messenger.client.model.Mensagem;
import com.davi.messenger.client.model.User;
import com.davi.messenger.client.view.Popup;
import com.davi.messenger.client.view.ViewUser;


public class UserController {

    private static final String ARQUIVO_HISTORICO = "mensagens.txt";

    private final Popup view;
    private final Map<String, User> users = new LinkedHashMap<>();

    public UserController(Popup view) {
        this.view = view;
    }

    public void iniciar() {
        view.addListenerSendButton(e -> enviarMensagem());

        adicionarMensagem("John Doe", "Hello, World!");

        view.setVisible(true);
    }

    private void enviarMensagem() {
        String nome = view.getUserName();
        String texto = view.getMessageText();

        if (nome.isEmpty() || texto.isEmpty()) {
            view.showMessage("Preencha o nome e a mensagem.");
            return;
        }

        adicionarMensagem(nome, texto);
        view.clearMessageField();
    }

    private void adicionarMensagem(String nome, String texto) {
        User user = users.computeIfAbsent(nome, n -> new User(n, "password123"));
        Mensagem mensagem = user.addMessage(texto);

        System.out.println("Enviando de " + user.getUsername() + ": " + texto);

        ViewUser item = view.addMessage(user.getUsername(), texto);
        item.addListenerExcludeButton(e -> {
            view.removeMessage(item);
            user.removeMessage(mensagem);
            salvarHistorico();
        });

        salvarHistorico();
    }

    private void salvarHistorico() {
        try (PrintWriter out = new PrintWriter(new FileWriter(ARQUIVO_HISTORICO))) {
            for (User user : users.values()) {
                for (Mensagem m : user.getMessages()) {
                    out.println("Enviado por " + user.getUsername() + ": " + m.getMensagem()
                            + " (Enviado às " + m.getHorarioFormatado() + ")");
                }
            }
        } catch (IOException e) {
            view.showMessage("Erro ao salvar o histórico: " + e.getMessage());
        }
    }
}
