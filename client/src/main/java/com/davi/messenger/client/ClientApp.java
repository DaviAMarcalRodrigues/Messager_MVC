package com.davi.messenger.client;

import javax.swing.SwingUtilities;

import com.davi.messenger.client.controller.UserController;
import com.davi.messenger.client.view.Popup;

/** Ponto de entrada do cliente: monta View + Controller e inicia. */
public class ClientApp {

    public static void main(String[] args) {
        // Toda a interface Swing deve rodar na thread de eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            Popup view = new Popup();
            UserController controller = new UserController(view);
            controller.iniciar();
        });
    }
}
