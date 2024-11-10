package cn.nukkit.network.connection;

import cn.nukkit.network.protocol.ProtocolInfo;

public class ProtocolContext {

    private int version;

    public static ProtocolContext current() {
        return new ProtocolContext(ProtocolInfo.CURRENT_PROTOCOL);
    }

    public ProtocolContext(int protocolVersion) {
        this.version = protocolVersion;
    }

    public int get() {
        return this.version;
    }

    public void set(int protocolVersion) {
        this.version = protocolVersion;
    }
}
