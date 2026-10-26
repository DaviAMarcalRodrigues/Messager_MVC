package com.davi.messenger.server.network;

import com.davi.messenger.common.Packet;

public  interface PacketListener {
    void onPacket(Packet packet);
    void onDisconnected(Exception cause);
}
