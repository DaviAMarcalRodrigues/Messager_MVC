package com.davi.messenger.common;

import java.io.Serializable;

public class Packet implements Serializable{
    private static final long serialVersionUID = 1L;

    private final Command command;
    private final Serializable body;

    private Packet(Command command, Serializable body) {
        this.command = command;
        this.body = body;
    }

    // Cliente -> Servidor 
    public static Packet login(String username)  { return new Packet(Command.LOGIN, username); }
    public static Packet send(Message message)   { return new Packet(Command.SEND, message); }
    public static Packet delete(String messageId){ return new Packet(Command.DELETE, messageId); }
    public static Packet logout()                { return new Packet(Command.LOGOUT, null); }

    // Servidor -> Cliente
    public static Packet broadcast(Message message) { return new Packet(Command.BROADCAST, message); }
    public static Packet deleted(String messageId)  { return new Packet(Command.DELETED, messageId); }
    public static Packet error(String reason)       { return new Packet(Command.ERROR, reason); }

    public Command getCommand() { return command; }

    // Getters tipados: quem lê não precisa fazer cast na mão
    public Message getMessage() { return (Message) body; }
    public String getString()   { return (String) body; }
}
