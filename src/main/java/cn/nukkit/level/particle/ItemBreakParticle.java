package cn.nukkit.level.particle;

import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.network.translator.ItemTranslator;

/**
 * @author xtypr
 * @since 2015/11/21
 */
public class ItemBreakParticle extends ProtocolParticle {

    private final Item item;

    public ItemBreakParticle(Vector3 pos, Item item) {
        super(pos.x, pos.y, pos.z);
        this.item = item;
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket packet = new LevelEventPacket();
        packet.evid = (short) (LevelEventPacket.EVENT_ADD_PARTICLE_MASK | Particle.TYPE_ICON_CRACK);
        packet.x = (float) this.x;
        packet.y = (float) this.y;
        packet.z = (float) this.z;
        packet.data = (ItemTranslator.getInstance().getOldId(protocol, item.getRuntimeId()) << 16) | item.getDamage();
        return new DataPacket[]{packet};
    }
}
