package cn.nukkit.network.process.processor;

import cn.nukkit.Player;
import cn.nukkit.PlayerHandle;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFood;
import cn.nukkit.item.ItemGoldenApple;
import cn.nukkit.network.process.DataPacketProcessor;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.translator.ItemNetworkInfo;
import cn.nukkit.network.translator.ItemTranslator;
import cn.nukkit.network.translator.ProtocolUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class EntityEventProcessor extends DataPacketProcessor<EntityEventPacket> {
    @Override
    public void handle(@NotNull PlayerHandle playerHandle, @NotNull EntityEventPacket pk) {
        Player player = playerHandle.player;
        if (!player.spawned || !player.isAlive()) {
            return;
        }

        if (pk.event == EntityEventPacket.EATING_ITEM) {
            if (pk.data == 0 || pk.eid != player.getId()) {
                return;
            }

            Item hand = player.getInventory().getItemInHand();
            if(!(hand instanceof ItemFood)) {
                return;
            }

            var clientItem = ItemTranslator.getInstance().oldToLatest(new ItemNetworkInfo(pk.data >> 16, pk.data & 0xffff), player.getSession().getProtocol());
            var clientItemRuntimeID = clientItem.id();
            var clientItemDamage = clientItem.meta();

            int predictedData = (hand.getRuntimeId() << 16) | hand.getDamage();
            if(((clientItemRuntimeID << 16) | clientItemDamage) != predictedData) {
                Server.getInstance().getLogger().debug("Client sent invalid eating item event for player " + player.getName() + " (expected " + predictedData + ", got " + pk.data + ")");
                return;
            }

            broadcastEating(player, hand);
        } else if (pk.event == EntityEventPacket.ENCHANT) {
            if (pk.eid != player.getId()) {
                return;
            }
        }
    }

    private void broadcastEating(Player player, Item item) {
        Map<Integer, Player> targets = player.getViewers();
        targets.put(player.getLoaderId(), player);

        var grouped = ProtocolUtils.groupByProtocol(new ObjectArrayList<>(targets.values()));
        for (var entry : grouped.entrySet()) {
            EntityEventPacket pk = new EntityEventPacket();
            pk.eid = player.getId();
            pk.event = EntityEventPacket.EATING_ITEM;
            pk.data = ItemTranslator.getInstance().latestToOld(new ItemNetworkInfo(item.getRuntimeId(), item.getDamage()), entry.getKey()).join();
            Server.broadcastPacket(entry.getValue(), pk);
        }
    }

    @Override
    public int getPacketId() {
        return ProtocolInfo.ENTITY_EVENT_PACKET;
    }
}
