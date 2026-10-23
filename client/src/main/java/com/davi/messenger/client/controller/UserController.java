package com.davi.messenger.client.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.davi.messenger.client.network.PacketListener;
import com.davi.messenger.client.network.ServerConnection;
import com.davi.messenger.client.view.Popup;
import com.davi.messenger.client.view.ViewUser;
import com.davi.messenger.common.Message;
import com.davi.messenger.common.Packet;
import com.davi.messenger.common.Protocol;

public class UserController implements PacketListener {

    private static final String ARQUIVO_HISTORICO = "mensagens.txt";

    private final Popup view;
    private final Map<String, Message> mensagens = new LinkedHashMap<>(); // id -> mensagem
    private final Map<String, ViewUser> itens = new HashMap<>();          // id -> componente na tela

    private ServerConnection conexao;
    private String username;

    public UserController(Popup view) {
        this.view = view;
    }

    public void iniciar() {
        username = JOptionPane.showInputDialog(view, "Seu nome:");
        if (username == null || username.isBlank()) {
            System.exit(0);
        }
        username = username.trim();

        try {
            conexao = new ServerConnection(Protocol.HOST, Protocol.PORT, this);
            conexao.send(Packet.login(username));
            conexao.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Não foi possível conectar ao servidor: " + e.getMessage());
            System.exit(1);
        }

        view.setUserName(username);
        view.addListenerSendButton(e -> enviarMensagem());
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                enviar(Packet.logout());
                conexao.close();
            }
        });
        view.setVisible(true);
    }

    private void enviarMensagem() {
        String texto = view.getMessageText();
        if (texto.isEmpty()) {
            return;
        }
        enviar(Packet.send(Message.text(username, texto)));
        view.clearMessageField();
    }

    private void pedirExclusao(String messageId) {
        enviar(Packet.delete(messageId));
    }

    private void enviar(Packet packet) {
        try {
            conexao.send(packet);
        } catch (IOException e) {
            view.showMessage("Erro ao enviar: " + e.getMessage());
        }
    }

    @Override
    public void onPacket(Packet packet) {
        // Volta para a EDT antes de mexer na tela
        SwingUtilities.invokeLater(() -> tratar(packet));
    }

    @Override
    public void onDisconnected(Exception cause) {
        SwingUtilities.invokeLater(() -> view.showMessage("Conexão com o servidor perdida."));
    }

    private void tratar(Packet packet) {
        switch (packet.getCommand()) {
            case BROADCAST -> mostrar(packet.getMessage());
            case DELETED   -> remover(packet.getString());
            case ERROR     -> view.showMessage(packet.getString());
            default        -> System.err.println("Comando inesperado: " + packet.getCommand());
        }
    }

    private void mostrar(Message m) {
        String conteudo = switch (m.getType()) {
            case TEXT  -> m.getText();
            case IMAGE -> "[imagem: " + m.getFileName() + "]"; 
            case AUDIO -> "[áudio: " + m.getFileName() + "]";
        };

        ViewUser item = view.addMessage(m.getSender(), conteudo);

        if (m.getSender().equals(username)) {
            item.addListenerExcludeButton(e -> pedirExclusao(m.getId()));
        } else {
            item.getButtonExclude().setEnabled(false); 
        }

        mensagens.put(m.getId(), m);
        itens.put(m.getId(), item);
        salvarHistorico();
    }

    private void remover(String messageId) {
        ViewUser item = itens.remove(messageId);
        if (item != null) {
            view.removeMessage(item);
        }
        mensagens.remove(messageId);
        salvarHistorico();
    }

    private void salvarHistorico() {
        try (PrintWriter out = new PrintWriter(new FileWriter(ARQUIVO_HISTORICO))) {
            for (Message m : mensagens.values()) {
                out.println("Enviado por " + m.getSender() + ": "
                        + (m.getText() != null ? m.getText() : "[" + m.getFileName() + "]")
                        + " (Enviado às " + m.getTimestampFormatado() + ")");
            }
        } catch (IOException e) {
            view.showMessage("Erro ao salvar o histórico: " + e.getMessage());
        }
    }
}