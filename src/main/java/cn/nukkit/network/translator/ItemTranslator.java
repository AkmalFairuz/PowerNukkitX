package cn.nukkit.network.translator;

import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.registry.ItemRegistry;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTranslator {

    private static ItemTranslator instance = null;

    public static ItemTranslator getInstance() {
        return instance;
    }

    private static final Map<Integer, String> mappingFiles = Map.ofEntries(
            Map.entry(ProtocolInfo.PROTOCOL_729, "-1.21.30")
    );

    public static void init(){
        if(instance == null){
            new ItemTranslator();
        }
    }

    private final Map<Integer, Map<Integer, Integer>> oldToLatestMapping = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Map<Integer, Integer>> latestToOldMapping = new Int2ObjectOpenHashMap<>();
    private final Map<Integer, Integer> nullMapping = new Int2IntOpenHashMap();

    private ItemTranslator() {
        instance = this;

        for (Map.Entry<Integer, String> entry : mappingFiles.entrySet()) {
            int protocol = entry.getKey();
            String suffix = entry.getValue();
            Map<Integer, Integer> oldToLatest = new Int2IntOpenHashMap();
            Map<Integer, Integer> latestToOld = new Int2IntOpenHashMap();
            int nullId = 0;

            Config data = new Config(Config.JSON);
            try (InputStream stream = ItemRegistry.class.getClassLoader().getResourceAsStream("runtime_item_states" + suffix + ".json")){
                assert stream != null;

                data.load(stream);

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> items = (List<Map<String, Object>>) data.getList("items");

                for (var item : items) {
                    var name = item.get("name").toString();
                    var id = ((Number) item.get("id")).intValue();

                    if(name.equals("minecraft:air")){
                        nullId = id;
                    }

                    int latestId = Registries.ITEM_RUNTIMEID.get(name);

                    oldToLatest.put(id, latestId);
                    latestToOld.put(latestId, id);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            oldToLatestMapping.put(protocol, oldToLatest);
            latestToOldMapping.put(protocol, latestToOld);
            nullMapping.put(protocol, nullId);
        }
    }

    public int getOldId(int protocol, int latestId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL) {
            return latestId;
        }
        var ret = latestToOldMapping.get(protocol).get(latestId);
        if(ret == null) {
            return nullMapping.get(protocol);
        }
        return ret;
    }

    public int getLatestId(int protocol, int oldId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL) {
            return oldId;
        }
        var ret = oldToLatestMapping.get(protocol).get(oldId);
        if(ret == null) {
            return Registries.ITEM_RUNTIMEID.get("minecraft:air");
        }
        return ret;
    }

    public int getOldFullId(int protocol, int latestFullId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL) {
            return latestFullId;
        }
        return getOldId(protocol, latestFullId >> 16) << 16 | latestFullId & 0xffff;
    }

    public int getLatestFullId(int protocol, int oldFullId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL) {
            return oldFullId;
        }
        return getLatestId(protocol, oldFullId >> 16) << 16 | oldFullId & 0xffff;
    }
}
