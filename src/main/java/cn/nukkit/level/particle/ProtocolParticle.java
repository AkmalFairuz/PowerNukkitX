package cn.nukkit.level.particle;

import cn.nukkit.network.protocol.ProtocolInfo;
import lombok.Getter;
import lombok.Setter;

public abstract class ProtocolParticle extends Particle{

    @Getter
    @Setter
    protected int protocol = ProtocolInfo.CURRENT_PROTOCOL;

    public ProtocolParticle(double x, double y, double z) {
        super(x, y, z);
    }
}
