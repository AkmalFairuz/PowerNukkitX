package cn.nukkit.network.translator.itemdowngrader;

import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class ItemDowngraderSchema_729_to_712 implements ItemDowngraderSchema{

    @Override
    public Pair<String, Integer> downgrade(String name, Integer meta) {
        return switch (name) {
            case "minecraft:compound_creator" -> Pair.of("minecraft:chemistry_table", 0);
            case "minecraft:element_constructor" -> Pair.of("minecraft:chemistry_table", 8);
            case "minecraft:lab_table" -> Pair.of("minecraft:chemistry_table", 12);
            case "minecraft:material_reducer" -> Pair.of("minecraft:chemistry_table", 4);
            case "minecraft:mossy_cobblestone_wall" -> Pair.of("minecraft:cobblestone_wall", 1);
            case "minecraft:end_stone_brick_wall" -> Pair.of("minecraft:cobblestone_wall", 10);
            case "minecraft:prismarine_wall" -> Pair.of("minecraft:cobblestone_wall", 11);
            case "minecraft:red_sandstone_wall" -> Pair.of("minecraft:cobblestone_wall", 12);
            case "minecraft:red_nether_brick_wall" -> Pair.of("minecraft:cobblestone_wall", 13);
            case "minecraft:granite_wall" -> Pair.of("minecraft:cobblestone_wall", 2);
            case "minecraft:diorite_wall" -> Pair.of("minecraft:cobblestone_wall", 3);
            case "minecraft:andesite_wall" -> Pair.of("minecraft:cobblestone_wall", 4);
            case "minecraft:sandstone_wall" -> Pair.of("minecraft:cobblestone_wall", 5);
            case "minecraft:brick_wall" -> Pair.of("minecraft:cobblestone_wall", 6);
            case "minecraft:stone_brick_wall" -> Pair.of("minecraft:cobblestone_wall", 7);
            case "minecraft:mossy_stone_brick_wall" -> Pair.of("minecraft:cobblestone_wall", 8);
            case "minecraft:nether_brick_wall" -> Pair.of("minecraft:cobblestone_wall", 9);
            case "minecraft:colored_torch_blue" -> Pair.of("minecraft:colored_torch_bp", 0);
            case "minecraft:colored_torch_purple" -> Pair.of("minecraft:colored_torch_bp", 8);
            case "minecraft:colored_torch_red" -> Pair.of("minecraft:colored_torch_rg", 0);
            case "minecraft:colored_torch_green" -> Pair.of("minecraft:colored_torch_rg", 8);
            case "minecraft:deprecated_purpur_block_1" -> Pair.of("minecraft:purpur_block", 1);
            case "minecraft:purpur_pillar" -> Pair.of("minecraft:purpur_block", 2);
            case "minecraft:deprecated_purpur_block_2" -> Pair.of("minecraft:purpur_block", 3);
            case "minecraft:wet_sponge" -> Pair.of("minecraft:sponge", 1);
            case "minecraft:underwater_tnt" -> Pair.of("minecraft:tnt", 2);
            default -> Pair.of(name, meta);
        };
    }

    @Override
    public int getTargetProtocolVersion() {
        return ProtocolInfo.PROTOCOL_712;
    }

    @Override
    public int getSourceProtocolVersion() {
        return ProtocolInfo.PROTOCOL_729;
    }
}
