package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.inventory.CreativeContentEntry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreativeContentPacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.CREATIVE_CONTENT_PACKET;


    public List<CreativeContentEntry> entries;

    @Override
    public int pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(entries.size());
        for (CreativeContentEntry entry : entries) {
            entry.write(byteBuf);
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
