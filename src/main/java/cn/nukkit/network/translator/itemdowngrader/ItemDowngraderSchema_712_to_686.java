package cn.nukkit.network.translator.itemdowngrader;

import cn.nukkit.network.protocol.ProtocolInfo;
import it.unimi.dsi.fastutil.Pair;

public class ItemDowngraderSchema_712_to_686 implements ItemDowngraderSchema{

    @Override
    public Pair<String, Integer> downgrade(String name, Integer meta) {
        return switch (name) {
            // minecraft:anvil
            case "minecraft:damaged_anvil" -> Pair.of("minecraft:anvil", 8);
            case "minecraft:chipped_anvil" -> Pair.of("minecraft:anvil", 4);

            // minecraft:dirt
            case "minecraft:coarse_dirt" -> Pair.of("minecraft:dirt", 1);

            // minecraft:double_stone_block_slab
            case "minecraft:smooth_stone_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 0);
            case "minecraft:sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 1);
            case "minecraft:petrified_oak_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 2);
            case "minecraft:cobblestone_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 3);
            case "minecraft:brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 4);
            case "minecraft:stone_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 5);
            case "minecraft:quartz_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 6);
            case "minecraft:nether_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab", 7);

            // minecraft:double_stone_block_slab2
            case "minecraft:red_sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 0);
            case "minecraft:purpur_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 1);
            case "minecraft:prismarine_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 2);
            case "minecraft:dark_prismarine_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 3);
            case "minecraft:prismarine_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 4);
            case "minecraft:mossy_cobblestone_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 5);
            case "minecraft:smooth_sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 6);
            case "minecraft:red_nether_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab2", 7);

            // minecraft:double_stone_block_slab3
            case "minecraft:end_stone_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 0);
            case "minecraft:smooth_red_sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 1);
            case "minecraft:polished_andesite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 2);
            case "minecraft:andesite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 3);
            case "minecraft:diorite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 4);
            case "minecraft:polished_diorite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 5);
            case "minecraft:granite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 6);
            case "minecraft:polished_granite_double_slab" -> Pair.of("minecraft:double_stone_block_slab3", 7);

            // minecraft:double_stone_block_slab4
            case "minecraft:mossy_stone_brick_double_slab" -> Pair.of("minecraft:double_stone_block_slab4", 0);
            case "minecraft:smooth_quartz_double_slab" -> Pair.of("minecraft:double_stone_block_slab4", 1);
            case "minecraft:normal_stone_double_slab" -> Pair.of("minecraft:double_stone_block_slab4", 2);
            case "minecraft:cut_sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab4", 3);
            case "minecraft:cut_red_sandstone_double_slab" -> Pair.of("minecraft:double_stone_block_slab4", 4);

            // minecraft:stone_block_slab2
            case "minecraft:red_sandstone_slab" -> Pair.of("minecraft:stone_block_slab2", 0);
            case "minecraft:purpur_slab" -> Pair.of("minecraft:stone_block_slab2", 1);
            case "minecraft:prismarine_slab" -> Pair.of("minecraft:stone_block_slab2", 2);
            case "minecraft:dark_prismarine_slab" -> Pair.of("minecraft:stone_block_slab2", 3);
            case "minecraft:prismarine_brick_slab" -> Pair.of("minecraft:stone_block_slab2", 4);
            case "minecraft:mossy_cobblestone_slab" -> Pair.of("minecraft:stone_block_slab2", 5);
            case "minecraft:smooth_sandstone_slab" -> Pair.of("minecraft:stone_block_slab2", 6);
            case "minecraft:red_nether_brick_slab" -> Pair.of("minecraft:stone_block_slab2", 7);

            // minecraft:stone_block_slab3
            case "minecraft:end_stone_brick_slab" -> Pair.of("minecraft:stone_block_slab3", 0);
            case "minecraft:smooth_red_sandstone_slab" -> Pair.of("minecraft:stone_block_slab3", 1);
            case "minecraft:polished_andesite_slab" -> Pair.of("minecraft:stone_block_slab3", 2);
            case "minecraft:andesite_slab" -> Pair.of("minecraft:stone_block_slab3", 3);
            case "minecraft:diorite_slab" -> Pair.of("minecraft:stone_block_slab3", 4);
            case "minecraft:polished_diorite_slab" -> Pair.of("minecraft:stone_block_slab3", 5);
            case "minecraft:granite_slab" -> Pair.of("minecraft:stone_block_slab3", 6);
            case "minecraft:polished_granite_slab" -> Pair.of("minecraft:stone_block_slab3", 7);

            // minecraft:stone_block_slab4
            case "minecraft:mossy_stone_brick_slab" -> Pair.of("minecraft:stone_block_slab4", 0);
            case "minecraft:smooth_quartz_slab" -> Pair.of("minecraft:stone_block_slab4", 1);
            case "minecraft:normal_stone_slab" -> Pair.of("minecraft:stone_block_slab4", 2);
            case "minecraft:cut_sandstone_slab" -> Pair.of("minecraft:stone_block_slab4", 3);
            case "minecraft:cut_red_sandstone_slab" -> Pair.of("minecraft:stone_block_slab4", 4);

            // minecraft:light_block
            case "minecraft:light_block_0" -> Pair.of("minecraft:light_block", 0);
            case "minecraft:light_block_1" -> Pair.of("minecraft:light_block", 1);
            case "minecraft:light_block_2" -> Pair.of("minecraft:light_block", 2);
            case "minecraft:light_block_3" -> Pair.of("minecraft:light_block", 3);
            case "minecraft:light_block_4" -> Pair.of("minecraft:light_block", 4);
            case "minecraft:light_block_5" -> Pair.of("minecraft:light_block", 5);
            case "minecraft:light_block_6" -> Pair.of("minecraft:light_block", 6);
            case "minecraft:light_block_7" -> Pair.of("minecraft:light_block", 7);
            case "minecraft:light_block_8" -> Pair.of("minecraft:light_block", 8);
            case "minecraft:light_block_9" -> Pair.of("minecraft:light_block", 9);
            case "minecraft:light_block_10" -> Pair.of("minecraft:light_block", 10);
            case "minecraft:light_block_11" -> Pair.of("minecraft:light_block", 11);
            case "minecraft:light_block_12" -> Pair.of("minecraft:light_block", 12);
            case "minecraft:light_block_13" -> Pair.of("minecraft:light_block", 13);
            case "minecraft:light_block_14" -> Pair.of("minecraft:light_block", 14);
            case "minecraft:light_block_15" -> Pair.of("minecraft:light_block", 15);

            // minecraft:monster_egg
            case "minecraft:infested_stone" -> Pair.of("minecraft:monster_egg", 0);
            case "minecraft:infested_cobblestone" -> Pair.of("minecraft:monster_egg", 1);
            case "minecraft:infested_stone_bricks" -> Pair.of("minecraft:monster_egg", 2);
            case "minecraft:infested_mossy_stone_bricks" -> Pair.of("minecraft:monster_egg", 3);
            case "minecraft:infested_cracked_stone_bricks" -> Pair.of("minecraft:monster_egg", 4);
            case "minecraft:infested_chiseled_stone_bricks" -> Pair.of("minecraft:monster_egg", 5);

            // minecraft:prismarine
            case "minecraft:dark_prismarine" -> Pair.of("minecraft:prismarine", 1);
            case "minecraft:prismarine_bricks" -> Pair.of("minecraft:prismarine", 2);

            // minecraft:quartz_block
            case "minecraft:chiseled_quartz_block" -> Pair.of("minecraft:quartz_block", 1);
            case "minecraft:quartz_pillar" -> Pair.of("minecraft:quartz_block", 2);
            case "minecraft:smooth_quartz" -> Pair.of("minecraft:quartz_block", 3);

            // minecraft:red_sandstone
            case "minecraft:chiseled_red_sandstone" -> Pair.of("minecraft:red_sandstone", 1);
            case "minecraft:cut_red_sandstone" -> Pair.of("minecraft:red_sandstone", 2);
            case "minecraft:smooth_red_sandstone" -> Pair.of("minecraft:red_sandstone", 3);

            // minecraft:sand
            case "minecraft:red_sand" -> Pair.of("minecraft:sand", 1);

            // minecraft:sandstone
            case "minecraft:chiseled_sandstone" -> Pair.of("minecraft:sandstone", 1);
            case "minecraft:cut_sandstone" -> Pair.of("minecraft:sandstone", 2);
            case "minecraft:smooth_sandstone" -> Pair.of("minecraft:sandstone", 3);

            // minecraft:stonebrick
            case "minecraft:stone_bricks" -> Pair.of("minecraft:stonebrick", 0);
            case "minecraft:mossy_stone_bricks" -> Pair.of("minecraft:stonebrick", 1);
            case "minecraft:cracked_stone_bricks" -> Pair.of("minecraft:stonebrick", 2);
            case "minecraft:chiseled_stone_bricks" -> Pair.of("minecraft:stonebrick", 3);

            // Renamed IDs
            case "minecraft:dandelion" -> Pair.of("minecraft:yellow_flower", meta);

            default -> Pair.of(name, meta);
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
