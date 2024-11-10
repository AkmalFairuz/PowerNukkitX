package cn.nukkit.network.connection.netty;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BedrockPacketWrapper extends AbstractReferenceCounted {
    private int protocol = ProtocolInfo.CURRENT_PROTOCOL;
    private int packetId;
    private int senderSubClientId;
    private int targetSubClientId;
    private int headerLength;
    private DataPacket packet;
    private ByteBuf packetBuffer;

    public BedrockPacketWrapper(int packetId, int senderSubClientId, int targetSubClientId, DataPacket packet, ByteBuf packetBuffer) {
        this(ProtocolInfo.CURRENT_PROTOCOL, packetId, senderSubClientId, targetSubClientId, 0, packet, packetBuffer);
    }

    public BedrockPacketWrapper(int protocol, int packetId, int senderSubClientId, int targetSubClientId, DataPacket packet, ByteBuf packetBuffer) {
        this.protocol = protocol;
        this.packetId = packetId;
        this.senderSubClientId = senderSubClientId;
        this.targetSubClientId = targetSubClientId;
        this.packet = packet;
        this.packetBuffer = packetBuffer;
    }

    @Override
    protected void deallocate() {
        ReferenceCountUtil.safeRelease(this.packetBuffer);
        this.packet=null;
    }

    @Override
    public BedrockPacketWrapper touch(Object hint) {
        ReferenceCountUtil.touch(this.packetBuffer);
        return this;
    }

    @Override
    public BedrockPacketWrapper retain() {
        return (BedrockPacketWrapper) super.retain();
    }
}
