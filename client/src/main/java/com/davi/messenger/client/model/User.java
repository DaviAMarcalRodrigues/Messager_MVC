package com.davi.messenger.client.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private final List<String> contacts = new ArrayList<>();
    private final List<Mensagem> messages = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public List<String> getContacts() {
        return contacts;
    }

    public List<Mensagem> getMessages() {
        return messages;
    }

    public void addContact(String contact) {
        contacts.add(contact);
    }

    public void removeContact(String contact) {
        contacts.remove(contact);
    }

    public Mensagem addMessage(String texto) {
        Mensagem mensagem = new Mensagem(texto);
        messages.add(mensagem);
        return mensagem;
    }

    public void removeMessage(Mensagem message) {
        messages.remove(message);
    }

    @Override
    public String toString() {
        return username;
    }
}
