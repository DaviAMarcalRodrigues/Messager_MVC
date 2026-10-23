package com.davi.messenger.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.davi.messenger.common.Packet;

public class ServerConnection {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final PacketListener listener;

    public ServerConnection(String host, int port, PacketListener listener) throws IOException {
        this.listener = listener;
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void start() {
        Thread reader = new Thread(this::readLoop, "server-reader");
        reader.setDaemon(true); 
        reader.start();
    }

    private void readLoop() {
        try {
            while (true) {
                Packet packet = (Packet) in.readObject(); 
                listener.onPacket(packet);
            }
        } catch (IOException | ClassNotFoundException e) {
            listener.onDisconnected(e);
        }
    }

    public synchronized void send(Packet packet) throws IOException {
        out.writeObject(packet);
        out.flush();
        out.reset();
    }

    public void close() {
        try { socket.close(); } catch (IOException ignored) {}
    }
}
