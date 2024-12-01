package cn.nukkit.network.translator;

import cn.nukkit.item.Item;

public record ItemNetworkInfo(
        int id,
        int meta
) {

    public static ItemNetworkInfo fromItem(Item item) {
        return new ItemNetworkInfo(item.getRuntimeId(), item.getDamage());
    }

    public int join() {
        return (id << 16) | meta;
    }

    public int fullId() {
        return (((short) id) << 16) | ((meta & 0x7fff) << 1);
    }

    public boolean isNull(){
        return id == 0;
    }
}
