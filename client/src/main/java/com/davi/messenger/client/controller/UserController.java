package com.davi.messenger.client.controller;

import javax.swing.SwingUtilities;
import java.util.ArrayList;

import com.davi.messenger.client.model.User;
import com.davi.messenger.client.view.TestMain;
import com.davi.messenger.client.view.ViewUser;
import com.davi.messenger.client.view.Popup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserController {


    public static void main(String[] args) {
		SwingUtilities.invokeLater(UserController::start);
	}

	private static void start() {
		Popup popup = new Popup();
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("John Doe", "password123");
        users.add(user1);

		addWithExclude(popup, users.get(0).getUsername(), "Hello, World!", users);

		popup.addListenerSendButton(e -> {
			User user = new User(popup.getUserName(), "password123");
			user.addMessage(popup.getMessageText());

			if (user.getUsername().isEmpty() || user.getMessages().isEmpty()) {
				popup.showMessage("Preencha o nome e a mensagem.");
				return;
			}

			System.out.println("Enviando de " + user + ": " + user.getMessages().get(0).getMensagem());
			addWithExclude(popup, user.getUsername(), user.getMessages().get(0).getMensagem(), users);
			popup.clearMessageField();
		});

		popup.setVisible(true);
	}

	private static void addWithExclude(Popup popup, String user, String message, ArrayList<User> users) {
		ViewUser item = popup.addMessage(user, message);

		item.addListenerExcludeButton(e -> popup.removeMessage(item));
        
	}
}
