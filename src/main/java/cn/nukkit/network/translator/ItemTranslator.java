package cn.nukkit.network.translator;

import cn.nukkit.level.updater.item.ItemUpdaters;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.translator.itemdowngrader.ItemDowngrader;
import cn.nukkit.registry.ItemRegistry;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.logging.log4j.core.net.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ItemTranslator {

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

    private final Int2ObjectOpenHashMap<Object2ObjectOpenHashMap<ItemNetworkInfo, ItemNetworkInfo>> oldToLatestCache = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectOpenHashMap<Object2ObjectOpenHashMap<ItemNetworkInfo, ItemNetworkInfo>> latestToOldCache = new Int2ObjectOpenHashMap<>();

    private final Int2ObjectOpenHashMap<Object2IntOpenHashMap<String>> itemRegistries = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectOpenHashMap<Int2ObjectOpenHashMap<String>> itemRegistriesReverse = new Int2ObjectOpenHashMap<>();

    private ItemTranslator() {
        instance = this;

        for (Map.Entry<Integer, String> entry : mappingFiles.entrySet()) {
            int protocol = entry.getKey();
            var itemRegistry = new Object2IntOpenHashMap<String>();

            String suffix = entry.getValue();

            Config data = new Config(Config.JSON);
            try (InputStream stream = ItemRegistry.class.getClassLoader().getResourceAsStream("runtime_item_states" + suffix + ".json")){
                assert stream != null;

                data.load(stream);

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> items = (List<Map<String, Object>>) data.getList("items");
                for(var item : items){
                    String name = item.get("name").toString();
                    int id = ((Number) item.get("id")).intValue();
                    itemRegistry.put(name, id);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            itemRegistries.put(protocol, itemRegistry);

            var itemRegistryReverse = new Int2ObjectOpenHashMap<String>();
            for(var entry2 : itemRegistry.object2IntEntrySet()){
                itemRegistryReverse.put(entry2.getIntValue(), entry2.getKey());
            }

            itemRegistriesReverse.put(protocol, itemRegistryReverse);

            oldToLatestCache.put(protocol, new Object2ObjectOpenHashMap<>());
            latestToOldCache.put(protocol, new Object2ObjectOpenHashMap<>());
        }
    }

    public ItemNetworkInfo latestToOld(ItemNetworkInfo latest, int protocol){
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL){
            return latest;
        }

        var cached = latestToOldCache.get(protocol).get(latest);
        if(cached != null){
            return cached;
        }

        var ret = uncachedLatestToOld(latest, protocol);
        latestToOldCache.get(protocol).put(latest, ret);
        return ret;
    }

    public ItemNetworkInfo uncachedLatestToOld(ItemNetworkInfo latest, int protocol){
        var identifier = Registries.ITEM_RUNTIMEID.getIdentifier(latest.id());
        if(identifier == null){
            return new ItemNetworkInfo(Registries.ITEM_RUNTIMEID.get("minecraft:air"), 0);
        }

        var downgraded = ItemDowngrader.INSTANCE.downgrade(identifier, latest.meta(), ProtocolInfo.CURRENT_PROTOCOL, protocol);

        var downgradedRuntimeId = itemRegistries.get(protocol).getOrDefault(downgraded.left(), -1);
        if(downgradedRuntimeId == -1){
            return new ItemNetworkInfo(Registries.ITEM_RUNTIMEID.get("minecraft:air"), 0);
        }

        return new ItemNetworkInfo(downgradedRuntimeId, downgraded.right());
    }

    public ItemNetworkInfo oldToLatest(ItemNetworkInfo old, int protocol){
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL){
            return old;
        }

        var cached = oldToLatestCache.get(protocol).get(old);
        if(cached != null){
            return cached;
        }

        var ret = uncachedOldToLatest(old, protocol);
        oldToLatestCache.get(protocol).put(old, ret);
        return ret;
    }

    public ItemNetworkInfo uncachedOldToLatest(ItemNetworkInfo old, int protocol){
        var identifier = itemRegistriesReverse.get(protocol).get(old.id());
        if(identifier == null){
            return new ItemNetworkInfo(Registries.ITEM_RUNTIMEID.get("minecraft:air"), 0);
        }

        var tag = new CompoundTag();
        tag.putString("Name", identifier);
        if(old.meta() != 0) {
            tag.putShort("Damage", old.meta());
        }

        var upgradedTag = ItemUpdaters.updateItem(tag, ProtocolInfo.MINECRAFT_BIT_VERSIONS.get(protocol));
        if(upgradedTag == null){
            return new ItemNetworkInfo(Registries.ITEM_RUNTIMEID.get("minecraft:air"), 0);
        }

        var upgradedIdentifier = upgradedTag.getString("Name");
        var upgradedMeta = upgradedTag.containsShort("Damage") ? upgradedTag.getShort("Damage") : 0;

        var upgradedRuntimeId = Registries.ITEM_RUNTIMEID.get(upgradedIdentifier);
        if(upgradedRuntimeId == null){
            return new ItemNetworkInfo(Registries.ITEM_RUNTIMEID.get("minecraft:air"), 0);
        }

        return new ItemNetworkInfo(upgradedRuntimeId, upgradedMeta);
    }

    public Object2IntOpenHashMap<String> getItemRegistry(int protocol) {
        return itemRegistries.get(protocol);
    }
}
