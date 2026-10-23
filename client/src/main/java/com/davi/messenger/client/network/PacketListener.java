package com.davi.messenger.client.network;

import com.davi.messenger.common.Packet;

public interface PacketListener {
    void onPacket(Packet packet);
    void onDisconnected(Exception cause);
}
