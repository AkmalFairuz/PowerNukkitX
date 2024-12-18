package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.inventory.FullContainerName;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryContentPacket extends DataPacket {

    public int inventoryId;
    public Item[] slots = Item.EMPTY_ARRAY;
    public FullContainerName fullContainerName = new FullContainerName(ContainerSlotType.ANVIL_INPUT, null);
    public Item storageItem = Item.AIR; // is air if the item is not a bundle

    @Override
    public int pid() {
        return ProtocolInfo.INVENTORY_CONTENT_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(this.inventoryId);
        byteBuf.writeUnsignedVarInt(this.slots.length);
        for (Item slot : this.slots) {
            byteBuf.writeSlot(slot);
        }
        if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_729) {
            byteBuf.writeFullContainerName(this.fullContainerName);
            if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_748) {
                byteBuf.writeSlot(this.storageItem);
            }else{
                byteBuf.writeUnsignedVarInt(0); // dynamic container size
            }
        }else if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_712) {
            byteBuf.writeUnsignedVarInt(Objects.requireNonNullElse(this.fullContainerName.getDynamicId(), 0));
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
