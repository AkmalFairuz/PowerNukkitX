package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class BlockDowngraderSchema_748_to_729 implements BlockDowngraderSchema{

    @Override
    public Pair<String, CompoundTag> downgrade(String name, CompoundTag states) {
        return switch (name) {
            case "minecraft:skeleton_skull", "minecraft:wither_skeleton_skull", "minecraft:player_head", "minecraft:zombie_head", "minecraft:creeper_head", "minecraft:dragon_head", "minecraft:piglin_head" -> Pair.of("minecraft:skull", states);
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
