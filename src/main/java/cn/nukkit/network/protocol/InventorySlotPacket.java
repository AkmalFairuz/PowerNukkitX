package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.inventory.FullContainerName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventorySlotPacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.INVENTORY_SLOT_PACKET;

    public int inventoryId;
    public int slot;
    public FullContainerName fullContainerName;
    public Item storageItem = Item.AIR; // is air if the item is not a bundle
    public Item item;


    @Override
    public int pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.inventoryId = byteBuf.readUnsignedVarInt();
        this.slot = byteBuf.readUnsignedVarInt();
        this.fullContainerName = byteBuf.readFullContainerName();
        if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_748) {
            this.storageItem = byteBuf.readSlot();
        }else{
            byteBuf.readUnsignedVarInt(); // dynamic container size
        }
        this.item = byteBuf.readSlot();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(this.inventoryId);
        byteBuf.writeUnsignedVarInt(this.slot);
        byteBuf.writeFullContainerName(this.fullContainerName);
        if(byteBuf.protocol >= ProtocolInfo.PROTOCOL_748) {
            byteBuf.writeSlot(this.storageItem);
        }else{
            byteBuf.writeUnsignedVarInt(0); // dynamic container size
        }
        byteBuf.writeSlot(this.item);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
