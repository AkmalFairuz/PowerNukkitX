package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class BlockDowngraderSchema_748_to_729 implements BlockDowngraderSchema{

    @Override
    public Pair<String, CompoundTag> downgrade(String name, CompoundTag states) {
        if(name.endsWith("_skull")){
            return Pair.of("minecraft:skull", states);
        }

        return switch (name) {
            case "minecraft:cherry_wood", "minecraft:mangrove_wood" -> {
                states.putByte("stripped_bit", 0);
                yield Pair.of(name, states);
            }
            default -> Pair.of(name, states);
        };

    }

    @Override
    public int getTargetProtocolVersion() {
        return ProtocolInfo.PROTOCOL_729;
    }

    @Override
    public int getSourceProtocolVersion() {
        return ProtocolInfo.PROTOCOL_748;
    }
}
