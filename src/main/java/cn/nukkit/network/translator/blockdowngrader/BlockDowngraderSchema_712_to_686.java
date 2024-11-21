package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

public class BlockDowngraderSchema_712_to_686 implements BlockDowngraderSchema {

    @Override
    public Pair<String, CompoundTag> downgrade(String name, CompoundTag states) {
        if (name.endsWith("_slab")) {
            boolean isDouble = name.endsWith("_double_slab");
            var slabType = name.substring(10, name.length() - (isDouble ? 12 : 5));
            int typeNum = switch (slabType) {
                case "prismarine", "dark_prismarine", "smooth_sandstone", "purpur", "red_nether_brick", "prismarine_brick", "mossy_cobblestone", "red_sandstone" ->
                        2;
                case "smooth_red_sandstone", "polished_granite", "granite", "polished_diorite", "andesite", "polished_andesite", "diorite", "end_stone_brick" ->
                        3;
                case "smooth_quartz", "cut_sandstone", "cut_red_sandstone", "normal_stone", "mossy_stone_brick" -> 4;
                default -> -1;
            };

            if (typeNum == -1) {
                // Unknown slab type
                states.putString("stone_slab_type", "cobblestone");
                return Pair.of("minecraft:" + (isDouble ? "double_" : "") + "stone_block_slab", states);
            }

            states.putString("stone_slab_type_" + typeNum, slabType);
            return Pair.of("minecraft:" + (isDouble ? "double_" : "") + "stone_block_slab" + typeNum, states);
        }

        if (name.startsWith("minecraft:light_block_")) {
            states.putString("block_light_level", name.substring(22));
            return Pair.of("minecraft:light_block", states);
        }

        if (name.endsWith("coral_wall_fan")) {
            // TODO: hide
            return Pair.of("minecraft:info_update", new CompoundTag());
        }

        return switch (name) {
            case "minecraft:dirt" -> {
                states.putString("dirt_type", "normal");
                yield Pair.of("minecraft:dirt", states);
            }
            case "minecraft:coarse_dirt" -> {
                states.putString("dirt_type", "coarse");
                yield Pair.of("minecraft:dirt", states);
            }
            case "minecraft:damaged_anvil" -> {
                states.putString("damage", "broken");
                yield Pair.of("minecraft:anvil", states);
            }
            case "minecraft:chipped_anvil" -> {
                states.putString("damage", "slightly_damaged");
                yield Pair.of("minecraft:anvil", states);
            }
            case "minecraft:deprecated_anvil" -> {
                states.putString("damage", "very_damaged");
                yield Pair.of("minecraft:anvil", states);
            }
            case "minecraft:anvil" -> {
                states.putString("damage", "undamaged");
                yield Pair.of("minecraft:anvil", states);
            }
            case "minecraft:dandelion" -> Pair.of("minecraft:yellow_flower", states);
            case "minecraft:red_sand" -> {
                states.putString("sand_type", "red");
                yield Pair.of("minecraft:sand", states);
            }
            case "minecraft:sand" -> {
                states.putString("sand_type", "normal");
                yield Pair.of("minecraft:sand", states);
            }
            case "minecraft:chiseled_quartz_block" -> {
                states.putString("chisel_type", "chiseled");
                yield Pair.of("minecraft:quartz_block", states);
            }
            case "minecraft:quartz_pillar" -> {
                states.putString("chisel_type", "lines");
                yield Pair.of("minecraft:quartz_block", states);
            }
            case "minecraft:smooth_quartz" -> {
                states.putString("chisel_type", "smooth");
                yield Pair.of("minecraft:quartz_block", states);
            }
            case "minecraft:quartz_block" -> {
                states.putString("chisel_type", "default");
                yield Pair.of("minecraft:quartz_block", states);
            }
            case "minecraft:infested_cobblestone" -> {
                states.putString("monster_egg_stone_type", "cobblestone");
                yield Pair.of("minecraft:monster_egg", states);
            }
            case "minecraft:infested_stone_bricks" -> {
                states.putString("monster_egg_stone_type", "stone_brick");
                yield Pair.of("minecraft:monster_egg", states);
            }
            case "minecraft:infested_mossy_stone_bricks" -> {
                states.putString("monster_egg_stone_type", "mossy_stone_brick");
                yield Pair.of("minecraft:monster_egg", states);
            }
            case "minecraft:infested_cracked_stone_bricks" -> {
                states.putString("monster_egg_stone_type", "cracked_stone_brick");
                yield Pair.of("minecraft:monster_egg", states);
            }
            case "minecraft:infested_chiseled_stone_bricks" -> {
                states.putString("monster_egg_stone_type", "chiseled_stone_brick");
                yield Pair.of("minecraft:monster_egg", states);
            }
            case "minecraft:dark_prismarine" -> {
                states.putString("prismarine_block_type", "dark");
                yield Pair.of("minecraft:prismarine", states);
            }
            case "minecraft:prismarine_bricks" -> {
                states.putString("prismarine_block_type", "bricks");
                yield Pair.of("minecraft:prismarine", states);
            }
            case "minecraft:prismarine" -> {
                states.putString("prismarine_block_type", "default");
                yield Pair.of("minecraft:prismarine", states);
            }
            case "minecraft:cut_red_sandstone" -> {
                states.putString("sand_stone_type", "cut");
                yield Pair.of("minecraft:red_sandstone", states);
            }
            case "minecraft:chiseled_red_sandstone" -> {
                states.putString("sand_stone_type", "heiroglyphs");
                yield Pair.of("minecraft:red_sandstone", states);
            }
            case "minecraft:smooth_red_sandstone" -> {
                states.putString("sand_stone_type", "smooth");
                yield Pair.of("minecraft:red_sandstone", states);
            }
            case "minecraft:red_sandstone" -> {
                states.putString("sand_stone_type", "default");
                yield Pair.of("minecraft:red_sandstone", states);
            }
            case "minecraft:sandstone" -> {
                states.putString("sand_stone_type", "default");
                yield Pair.of("minecraft:sandstone", states);
            }
            case "minecraft:cut_sandstone" -> {
                states.putString("sand_stone_type", "cut");
                yield Pair.of("minecraft:sandstone", states);
            }
            case "minecraft:chiseled_sandstone" -> {
                states.putString("sand_stone_type", "heiroglyphs");
                yield Pair.of("minecraft:sandstone", states);
            }
            case "minecraft:smooth_sandstone" -> {
                states.putString("sand_stone_type", "smooth");
                yield Pair.of("minecraft:sandstone", states);
            }
            case "minecraft:mossy_stone_bricks" -> {
                states.putString("stone_brick_type", "mossy");
                yield Pair.of("minecraft:stone_bricks", states);
            }
            case "minecraft:cracked_stone_bricks" -> {
                states.putString("stone_brick_type", "cracked");
                yield Pair.of("minecraft:stone_bricks", states);
            }
            case "minecraft:chiseled_stone_bricks" -> {
                states.putString("stone_brick_type", "chiseled");
                yield Pair.of("minecraft:stone_bricks", states);
            }
            case "minecraft:stone_bricks" -> {
                states.putString("stone_brick_type", "default");
                yield Pair.of("minecraft:stone_bricks", states);
            }
            default -> Pair.of(name, states);
        };

    }

    @Override
    public int getTargetProtocolVersion() {
        return ProtocolInfo.PROTOCOL_686;
    }

    @Override
    public int getSourceProtocolVersion() {
        return ProtocolInfo.PROTOCOL_712;
    }
}
