package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import it.unimi.dsi.fastutil.Pair;

public interface BlockDowngraderSchema {

    Pair<String, CompoundTag> downgrade(String name, CompoundTag states);

    int getTargetProtocolVersion();

    int getSourceProtocolVersion();
}
