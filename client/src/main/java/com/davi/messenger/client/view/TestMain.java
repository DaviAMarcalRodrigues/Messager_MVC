package com.davi.messenger.client.view;

import javax.swing.SwingUtilities;

public class TestMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TestMain::start);
	}

	private static void start() {
		Popup popup = new Popup();

		addWithExclude(popup, "John Doe", "Hello, World!");

		popup.addListenerSendButton(e -> {
			String user = popup.getUserName();
			String message = popup.getMessageText();

			if (user.isEmpty() || message.isEmpty()) {
				popup.showMessage("Preencha o nome e a mensagem.");
				return;
			}

			System.out.println("Enviando de " + user + ": " + message);
			addWithExclude(popup, user, message);
			popup.clearMessageField();
		});

		popup.setVisible(true);
	}

	private static void addWithExclude(Popup popup, String user, String message) {
		ViewUser item = popup.addMessage(user, message);
		item.addListenerExcludeButton(e -> popup.removeMessage(item));
	}
}
