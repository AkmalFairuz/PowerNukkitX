package cn.nukkit.network.protocol.types.inventory;

import cn.nukkit.item.Item;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreativeContentEntry {

    private int entryId;
    private Item item;

    public void write(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(entryId);
        byteBuf.writeSlot(item, true);
    }

    public static CreativeContentEntry read(HandleByteBuf byteBuf) {
        int entryId = byteBuf.readUnsignedVarInt();
        Item item = byteBuf.readSlot();
        return new CreativeContentEntry(entryId, item);
    }
}
