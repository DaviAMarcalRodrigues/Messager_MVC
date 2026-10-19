package com.davi.messenger.client.model;
import java.util.ArrayList;

public class User {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
    private ArrayList<String> contacts = new ArrayList<>();
    private ArrayList<Mensagem> messages = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Mensagem> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Mensagem> messages) {
        this.messages = messages;
    }

    public void addContact(String contact) {
        contacts.add(contact);
    }

    public void addMessage(String message) {
        messages.add(new Mensagem(message, (int) System.currentTimeMillis()));
    }

    public void removeContact(String contact) {
        contacts.remove(contact);
    }

    public void removeMessage(Mensagem message) {
        messages.remove(message);
    }
    
}
