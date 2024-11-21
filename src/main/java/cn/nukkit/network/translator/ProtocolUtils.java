package cn.nukkit.network.translator;

import cn.nukkit.Player;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;

public class ProtocolUtils {

    public static Map<Integer, List<Player>> groupByProtocol(List<Player> players) {
        Map<Integer, List<Player>> grouped = new Int2ObjectOpenHashMap<>();
        for (Player player : players) {
            int protocol = player.getSession().getProtocol();
            if (!grouped.containsKey(protocol)) {
                grouped.put(protocol, new ObjectArrayList<>());
            }
            grouped.get(protocol).add(player);
        }
        return grouped;
    }
}
