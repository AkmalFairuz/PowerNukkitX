package cn.nukkit.network.translator;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.registry.ItemRegistry;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTranslator extends TBaseTranslator<Integer> {

    private static ItemTranslator instance = null;

    public static ItemTranslator getInstance() {
        return instance;
    }

    private static final Map<Integer, String> mappingFiles = Map.ofEntries(
            Map.entry(ProtocolInfo.PROTOCOL_729, "-1.21.30"),
            Map.entry(ProtocolInfo.PROTOCOL_712, "-1.21.20"),
            Map.entry(ProtocolInfo.PROTOCOL_686, "-1.21.2")
    );

    public static void init(){
        if(instance == null){
            new ItemTranslator();
        }
    }

    private final Map<Integer, Object2IntOpenHashMap<String>> itemRegistries = new Int2ObjectOpenHashMap<>();

    private ItemTranslator() {
        super(new Int2ObjectOpenHashMap<>(), new Int2ObjectOpenHashMap<>(), new Int2ObjectOpenHashMap<>());
        instance = this;

        for (Map.Entry<Integer, String> entry : mappingFiles.entrySet()) {
            int protocol = entry.getKey();
            var itemRegistry = new Object2IntOpenHashMap<String>();

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
                    itemRegistry.put(name, id);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            oldToLatestMapping.put(protocol, oldToLatest);
            latestToOldMapping.put(protocol, latestToOld);
            fallbackMapping.put(protocol, nullId);
            itemRegistries.put(protocol, itemRegistry);
        }
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

    public Object2IntOpenHashMap<String> getItemRegistry(int protocol) {
        return itemRegistries.get(protocol);
    }

    @Override
    protected Integer getFallbackLatestId(int protocol) {
        return Registries.ITEM_RUNTIMEID.get("minecraft:air");
    }
}
