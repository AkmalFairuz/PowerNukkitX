package cn.nukkit.network.translator;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockState;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.translator.blockdowngrader.BlockDowngrader;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.HashUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class BlockTranslator extends TBaseTranslator<Integer> {

    private static BlockTranslator instance = null;

    public static BlockTranslator getInstance() {
        return instance;
    }

    public static void init() {
        if (instance == null) {
            new BlockTranslator();
        }
    }

    public BlockTranslator() {
        super(new Int2ObjectOpenHashMap<>(), new Int2ObjectOpenHashMap<>(), new Int2ObjectOpenHashMap<>());
        instance = this;

        Set<BlockState> latestStates = Registries.BLOCKSTATE.getAllState();

        BlockDowngrader downgrader = BlockDowngrader.INSTANCE;

        for(var proto : ProtocolInfo.COMPATIBLE_PROTOCOLS){
            if(proto == ProtocolInfo.CURRENT_PROTOCOL){
                continue;
            }

            Int2IntOpenHashMap oldToLatest = new Int2IntOpenHashMap();
            Int2IntOpenHashMap latestToOld = new Int2IntOpenHashMap();

            Integer infoUpdateHash = null;

            for(var state : latestStates){
                Pair<String, CompoundTag> downgradedState = downgrader.downgrade(state.getIdentifier(), state.getBlockStateTag().getCompound("states").copy(), ProtocolInfo.CURRENT_PROTOCOL, proto);

                var tag = new CompoundTag();
                tag.putString("name", downgradedState.left());
                tag.putCompound("states", downgradedState.right());

                var latestHash = state.blockStateHash();
                var oldHash = HashUtils.fnv1a_32_nbt(tag);

                oldToLatest.put(oldHash, latestHash);
                latestToOld.put(latestHash, oldHash);

                if(downgradedState.left().equals("minecraft:info_update")){
                    infoUpdateHash = latestHash;
                }
            }

            oldToLatestMapping.put(proto, oldToLatest);
            latestToOldMapping.put(proto, latestToOld);
            fallbackMapping.put(proto, infoUpdateHash);
        }
    }

    @Override
    protected Integer getFallbackLatestId(int protocol) {
        return Block.get(BlockID.INFO_UPDATE).getBlockState().blockStateHash();
    }
}
