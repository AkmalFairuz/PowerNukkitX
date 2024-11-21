package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class BlockDowngraderSchema_729_to_712 implements BlockDowngraderSchema {

    @Override
    public Pair<String, CompoundTag> downgrade(String name, CompoundTag states) {
        if (name.endsWith("_wall")) {
            String wallBlockType = name.substring(10, name.length() - 5);
            states.putString("wall_block_type", wallBlockType);
            return Pair.of("minecraft:cobblestone_wall", states);
        }

        return switch (name) {
            case "minecraft:wet_sponge" -> {
                states.putString("sponge_type", "wet");
                yield Pair.of("minecraft:sponge", states);
            }
            case "minecraft:purpur_pillar" -> {
                states.putString("chisel_type", "lines");
                yield Pair.of("minecraft:purpur_block", states);
            }
            case "minecraft:purpur_block" -> {
                states.putString("chisel_type", "normal");
                yield Pair.of("minecraft:purpur_block", states);
            }
            case "minecraft:underwater_tnt" -> {
                states.putByte("allow_underwater_bit", 1);
                yield Pair.of("minecraft:tnt", states);
            }
            case "minecraft:tnt" -> {
                states.putByte("allow_underwater_bit", 0);
                yield Pair.of("minecraft:tnt", states);
            }
            case "minecraft:compound_creator" -> {
                states.putString("chemistry_table_type", "compound_creator");
                yield Pair.of("minecraft:chemistry_table", states);
            }
            case "minecraft:element_constructor" -> {
                states.putString("chemistry_table_type", "element_constructor");
                yield Pair.of("minecraft:chemistry_table", states);
            }
            case "minecraft:lab_table" -> {
                states.putString("chemistry_table_type", "lab_table");
                yield Pair.of("minecraft:chemistry_table", states);
            }
            case "minecraft:material_reducer" -> {
                states.putString("chemistry_table_type", "material_reducer");
                yield Pair.of("minecraft:chemistry_table", states);
            }
            case "minecraft:structure_void" -> {
                states.putString("structure_void_type", "air");
                yield Pair.of("minecraft:structure_void", states);
            }
            case "minecraft:colored_torch_blue" -> {
                states.putByte("color_bit", 0);
                yield Pair.of("minecraft:colored_torch_bp", states);
            }
            case "minecraft:colored_torch_purple" -> {
                states.putByte("color_bit", 1);
                yield Pair.of("minecraft:colored_torch_bp", states);
            }
            case "minecraft:colored_torch_red" -> {
                states.putByte("color_bit", 0);
                yield Pair.of("minecraft:colored_torch_rg", states);
            }
            case "minecraft:colored_torch_green" -> {
                states.putByte("color_bit", 1);
                yield Pair.of("minecraft:colored_torch_rg", states);
            }
            default -> Pair.of(name, states);
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
