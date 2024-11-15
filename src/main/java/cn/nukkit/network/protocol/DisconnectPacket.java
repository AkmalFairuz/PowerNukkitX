package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.DisconnectFailReason;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @since 15-10-12
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DisconnectPacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.DISCONNECT_PACKET;

    public DisconnectFailReason reason = DisconnectFailReason.UNKNOWN;
    public boolean hideDisconnectionScreen = false;
    public String message;
    private String filteredMessage = "";

    @Override
    public int pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.reason = DisconnectFailReason.values()[byteBuf.readVarInt()];
        this.hideDisconnectionScreen = byteBuf.readBoolean();
        if(!this.hideDisconnectionScreen) {
            this.message = byteBuf.readString();
            if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_712) {
                this.filteredMessage = byteBuf.readString();
            }
        }
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVarInt(this.reason.ordinal());
        byteBuf.writeBoolean(this.hideDisconnectionScreen);
        if (!this.hideDisconnectionScreen) {
            byteBuf.writeString(this.message);
            if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_712) {
                byteBuf.writeString(this.filteredMessage);
            }
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
