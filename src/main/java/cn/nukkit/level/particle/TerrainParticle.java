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
public class TerrainParticle extends ProtocolParticle {
    
    protected final int blockRuntimeId;

    public TerrainParticle(Vector3 pos, int blockRuntimeId) {
        super(pos.x, pos.y, pos.z);
        this.blockRuntimeId = blockRuntimeId;
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket pk = new LevelEventPacket();
        pk.evid = (short) (LevelEventPacket.EVENT_ADD_PARTICLE_MASK | Particle.TYPE_TERRAIN);
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.data = BlockTranslator.getInstance().getOldId(protocol, this.blockRuntimeId);

        return new DataPacket[]{pk};
    }
}
