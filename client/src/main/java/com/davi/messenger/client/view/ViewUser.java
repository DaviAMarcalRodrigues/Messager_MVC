package com.davi.messenger.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewUser extends JPanel {

	private static final long serialVersionUID = 2L;

	private final JLabel labelUser;
	private final JLabel labelMessage;
	private final JButton buttonExclude;

	public ViewUser(String user, String message) {
		setLayout(new BorderLayout(8, 0));
		setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		labelUser = new JLabel(user + ":");
		labelUser.setFont(labelUser.getFont().deriveFont(Font.BOLD));

		labelMessage = new JLabel(message);

		buttonExclude = new JButton("Delete");

		add(labelUser, BorderLayout.WEST);
		add(labelMessage, BorderLayout.CENTER);
		add(buttonExclude, BorderLayout.EAST);

		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
	}

	public String getUser() {
		String text = labelUser.getText();
		return text.substring(0, text.length() - 1); 
	}

	public String getMessage() {
		return labelMessage.getText();
	}

	public void setMessage(String message) {
		labelMessage.setText(message);
	}

	public void addListenerExcludeButton(ActionListener listener) {
		for (ActionListener al : buttonExclude.getActionListeners()) {
			buttonExclude.removeActionListener(al);
		}
		buttonExclude.addActionListener(listener);
	}

	public JButton getButtonExclude() {
		return buttonExclude;
	}
}
