package com.davi.messenger.client.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Popup extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH_WINDOW = 600;
	private static final int HEIGHT_WINDOW = 400;

	private JButton buttonSend;
	private JButton buttonClear;
	private JPanel panelMessages;
	private JScrollPane scrollPane;
	private JTextField textFieldName;
	private JTextField textFieldMessage;

	private final List<ViewUser> lista = new ArrayList<>();

	public Popup() {
		setTitle("MVC - Messenger");
		setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		init();
		// setVisible(true)
	}

	private void init() {
		panelMessages = new JPanel();
		panelMessages.setLayout(new BoxLayout(panelMessages, BoxLayout.Y_AXIS));

		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(panelMessages, BorderLayout.NORTH);

		scrollPane = new JScrollPane(wrapper);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		textFieldName = new JTextField(10);
		textFieldMessage = new JTextField(25);
		buttonSend = new JButton("Enviar");
		buttonClear = new JButton("Limpar");
		buttonClear.addActionListener(e -> clear());

		JPanel form = new JPanel(new GridBagLayout());
		form.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 4, 2, 4);
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = 0; c.gridx = 0; c.weightx = 0;
		form.add(new JLabel("Name"), c);
		c.gridx = 1; c.weightx = 1; c.gridwidth = 3;
		form.add(textFieldName, c);

		c.gridy = 1; c.gridx = 0; c.weightx = 0; c.gridwidth = 1;
		form.add(new JLabel("Message"), c);
		c.gridx = 1; c.weightx = 1;
		form.add(textFieldMessage, c);
		c.gridx = 2; c.weightx = 0;
		form.add(buttonSend, c);
		c.gridx = 3;
		form.add(buttonClear, c);

		add(form, BorderLayout.SOUTH);
	}

	public ViewUser addMessage(String user, String message) {
		ViewUser item = new ViewUser(user, message);
		lista.add(item);
		panelMessages.add(item);
		panelMessages.add(Box.createVerticalStrut(2));
		refresh();
		scrollToBottom();
		return item;
	}

	public void removeMessage(ViewUser item) {
		int index = panelMessages.getComponentZOrder(item);
		if (index < 0) return;
		panelMessages.remove(index);                 
		if (index < panelMessages.getComponentCount()) {
			panelMessages.remove(index);           
		}
		lista.remove(item);
		refresh();
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void clearMessageField() {
		textFieldMessage.setText("");
		textFieldMessage.requestFocusInWindow();
	}

	public void clear() {
		textFieldMessage.setText("");
	}

	public void addListenerSendButton(ActionListener listener) {
		for (ActionListener al : buttonSend.getActionListeners()) {
			buttonSend.removeActionListener(al);
		}
		for (ActionListener al : textFieldMessage.getActionListeners()) {
			textFieldMessage.removeActionListener(al);
		}
		buttonSend.addActionListener(listener);
		textFieldMessage.addActionListener(listener);
	}

	public String getUserName() {
		return textFieldName.getText().trim();
	}

	public String getMessageText() {
		return textFieldMessage.getText().trim();
	}

	public JButton getButtonSend() {
		return buttonSend;
	}

	public List<ViewUser> getComponentList() {
		return lista;
	}

	private void refresh() {
		panelMessages.revalidate();
		panelMessages.repaint();
	}

	private void scrollToBottom() {
		SwingUtilities.invokeLater(() ->
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
	}

	public void setUserName(String name) {
		textFieldName.setText(name);
		textFieldName.setEditable(false);
	}
}
