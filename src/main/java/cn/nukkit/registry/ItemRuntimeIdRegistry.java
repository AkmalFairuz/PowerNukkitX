package cn.nukkit.registry;

import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.translator.ItemTranslator;
import cn.nukkit.utils.BinaryStream;
import cn.nukkit.utils.Config;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Cool_Loong
 */
@Slf4j
public class ItemRuntimeIdRegistry implements IRegistry<String, Integer, Integer> {
    private static final AtomicBoolean isLoad = new AtomicBoolean(false);
    private static final Object2IntOpenHashMap<String> REGISTRY = new Object2IntOpenHashMap<>();
    static final Object2ObjectOpenHashMap<String, RuntimeEntry> CUSTOM_REGISTRY = new Object2ObjectOpenHashMap<>();

    static {
        REGISTRY.defaultReturnValue(Integer.MAX_VALUE);
    }

    static final Int2ObjectOpenHashMap<String> ID2NAME = new Int2ObjectOpenHashMap<>();
    private static Map<Integer, byte[]> itemPalette = new HashMap<>();

    public byte[] getItemPalette(int protocol) {
        return itemPalette.get(protocol);
    }

    private void generatePalette(Object2IntOpenHashMap<String> registry, int protocol) {
        BinaryStream paletteBuffer = new BinaryStream();
        HashMap<Integer, Boolean> verify = new HashMap<>();
        paletteBuffer.putUnsignedVarInt(registry.size() + CUSTOM_REGISTRY.size());
        for (var entry : registry.object2IntEntrySet()) {
            paletteBuffer.putString(entry.getKey());
            int rid = entry.getIntValue();
            paletteBuffer.putLShort(rid);
            if (verify.putIfAbsent(rid, true) != null) {
                throw new IllegalArgumentException("Runtime ID is already registered: " + rid);
            }
            paletteBuffer.putBoolean(false); //Vanilla Item doesnt component item
        }
        for (var entry : CUSTOM_REGISTRY.object2ObjectEntrySet()) {
            paletteBuffer.putString(entry.getKey());
            int rid = entry.getValue().runtimeId();
            paletteBuffer.putLShort(rid);
            if (verify.putIfAbsent(rid, true) != null) {
                throw new IllegalArgumentException("Runtime ID is already registered: " + rid);
            }
            paletteBuffer.putBoolean(entry.getValue().isComponent());
        }
        itemPalette.put(protocol, paletteBuffer.getBuffer());
    }

    @Override
    public void init() {
        if (isLoad.getAndSet(true))
            return;

        Config data = new Config(Config.JSON);

        try (InputStream stream = ItemRegistry.class.getClassLoader().getResourceAsStream("runtime_item_states.json")){
            assert stream != null;

            data.load(stream);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) data.getList("items");

            for (var item : items) {
                register0(item.get("name").toString(), ((Number) item.get("id")).intValue());
            }
            trim();
            ItemTranslator.init();

            for (int protocol : ProtocolInfo.COMPATIBLE_PROTOCOLS) {
                var registry = protocol == ProtocolInfo.CURRENT_PROTOCOL ? REGISTRY : ItemTranslator.getInstance().getItemRegistry(protocol);
                generatePalette(registry, protocol);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer get(String key) {
        int i = REGISTRY.getInt(key);
        if (i == Integer.MAX_VALUE) {
            RuntimeEntry runtimeEntry = CUSTOM_REGISTRY.get(key);
            if (runtimeEntry == null) return Integer.MAX_VALUE;
            else return runtimeEntry.runtimeId;
        }
        return i;
    }

    public int getInt(String key) {
        int i = REGISTRY.getInt(key);
        if (i == Integer.MAX_VALUE) {
            RuntimeEntry runtimeEntry = CUSTOM_REGISTRY.get(key);
            if (runtimeEntry == null) return Integer.MAX_VALUE;
            else return runtimeEntry.runtimeId;
        }
        return i;
    }

    public String getIdentifier(int runtimeId) {
        return ID2NAME.get(runtimeId);
    }

    @Override
    public void trim() {
        REGISTRY.trim();
        CUSTOM_REGISTRY.trim();
    }

    public void reload() {
        isLoad.set(false);
        REGISTRY.clear();
        CUSTOM_REGISTRY.clear();
        init();
    }

    @Override
    public void register(String key, Integer value) throws RegisterException {
        if (REGISTRY.putIfAbsent(key, value.intValue()) == Integer.MAX_VALUE) {
            ID2NAME.put(value.intValue(), key);
        } else {
            throw new RegisterException("The item: " + key + "runtime id has been registered!");
        }
    }

    public void registerCustomRuntimeItem(RuntimeEntry entry) throws RegisterException {
        if (CUSTOM_REGISTRY.putIfAbsent(entry.identifier, entry) == null) {
            ID2NAME.put(entry.runtimeId(), entry.identifier);
        } else {
            throw new RegisterException("The item: " + entry.identifier + " runtime id has been registered!");
        }
    }

    private void register0(String key, Integer value) {
        if (REGISTRY.putIfAbsent(key, value.intValue()) == Integer.MAX_VALUE) {
            ID2NAME.put(value.intValue(), key);
        }
    }

    public Object2IntOpenHashMap<String> getRegistry(){
        return REGISTRY;
    }

    public record RuntimeEntry(String identifier, int runtimeId, boolean isComponent) {
    }
}
