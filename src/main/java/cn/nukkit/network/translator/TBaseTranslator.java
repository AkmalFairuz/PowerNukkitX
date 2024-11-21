package cn.nukkit.network.translator;

import cn.nukkit.network.protocol.ProtocolInfo;

import java.util.Map;

public abstract class TBaseTranslator<T> {

    protected final Map<Integer, Map<T, T>> oldToLatestMapping;
    protected final Map<Integer, Map<T, T>> latestToOldMapping;
    protected final Map<Integer, T> fallbackMapping;

    public TBaseTranslator(Map<Integer, Map<T, T>> oldToLatestMapping, Map<Integer, Map<T, T>> latestToOldMapping, Map<Integer, T> fallbackMapping) {
        this.oldToLatestMapping = oldToLatestMapping;
        this.latestToOldMapping = latestToOldMapping;
        this.fallbackMapping = fallbackMapping;
    }

    public T getOldId(int protocol, T latestId) {
        T ret = getOldIdNullable(protocol, latestId);
        if(ret == null){
            return fallbackMapping.get(protocol);
        }
        return ret;
    }

    public T getOldIdNullable(int protocol, T latestId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL){
            return latestId;
        }
        return latestToOldMapping.get(protocol).get(latestId);
    }

    public T getLatestId(int protocol, T oldId) {
        if(protocol == ProtocolInfo.CURRENT_PROTOCOL){
            return oldId;
        }
        T ret = oldToLatestMapping.get(protocol).get(oldId);
        if(ret == null){
            return getFallbackLatestId(protocol);
        }
        return ret;
    }

    protected abstract T getFallbackLatestId(int protocol);
}
