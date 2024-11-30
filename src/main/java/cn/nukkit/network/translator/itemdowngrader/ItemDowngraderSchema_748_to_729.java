package cn.nukkit.network.translator.itemdowngrader;

import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class ItemDowngraderSchema_748_to_729 implements ItemDowngraderSchema{

    @Override
    public Pair<String, Integer> downgrade(String name, Integer meta) {
        if(name.endsWith("_bundle")){
            return Pair.of("minecraft:air", 0); // unsupported item
        }

        return switch (name) {
            case "minecraft:skeleton_skull" -> Pair.of("minecraft:skull", 0);
            case "minecraft:wither_skeleton_skull" -> Pair.of("minecraft:skull", 1);
            case "minecraft:zombie_head" -> Pair.of("minecraft:skull", 2);
            case "minecraft:player_head" -> Pair.of("minecraft:skull", 3);
            case "minecraft:creeper_head" -> Pair.of("minecraft:skull", 4);
            case "minecraft:dragon_head" -> Pair.of("minecraft:skull", 5);
            case "minecraft:piglin_head" -> Pair.of("minecraft:skull", 6);
            default -> Pair.of(name, meta);
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
