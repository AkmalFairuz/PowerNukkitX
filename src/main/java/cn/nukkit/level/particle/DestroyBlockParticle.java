package cn.nukkit.level.particle;

import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.network.translator.BlockTranslator;

/**
 * @author xtypr
 * @since 2015/11/21
 */
public class DestroyBlockParticle extends ProtocolParticle {

    protected final int data;

    public DestroyBlockParticle(Vector3 pos, Block block) {
        super(pos.x, pos.y, pos.z);
        this.data = block.getRuntimeId();
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket pk = new LevelEventPacket();
        pk.evid = LevelEventPacket.EVENT_PARTICLE_DESTROY_BLOCK;
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.data = BlockTranslator.getInstance().getOldId(protocol, this.data);

        return new DataPacket[]{pk};
    }
}
