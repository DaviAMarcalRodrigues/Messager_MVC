package com.davi.messenger.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message implements Serializable{

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String id;
    private final String sender;
    private final MessageType type;
    private final String text;
    private final byte[] payload;
    private final String fileName;
    private final LocalDateTime timestamp;

    
    private Message(String sender, MessageType type, String text, byte[] payload, String fileName) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.type = type;
        this.text = text;
        this.payload = payload;
        this.fileName = fileName;
        this.timestamp = LocalDateTime.now();
    }

    public static Message text(String sender, String text) {
        return new Message(sender, MessageType.TEXT, text, null, null);
    }

    public static Message image(String sender, byte[] bytes, String fileName) {
        return new Message(sender, MessageType.IMAGE, null, bytes, fileName);
    }

    public static Message audio(String sender, byte[] bytes, String fileName) {
        return new Message(sender, MessageType.AUDIO, null, bytes, fileName);
    }

    public String getId()                 { 
        return id; 
    }
    public String getSender()             { 
        return sender; 
    }
    public MessageType getType()          {
        return type; 
    }
    public String getText()               { 
        return text; 
    }
    public byte[] getPayload()            { 
        return payload; 
    }
    public String getFileName()           { 
        return fileName; 
    }
    public LocalDateTime getTimestamp()   { 
        return timestamp; 
    }

    public String getTimestampFormatado() {
        return timestamp.format(FORMATO);
    }

}
