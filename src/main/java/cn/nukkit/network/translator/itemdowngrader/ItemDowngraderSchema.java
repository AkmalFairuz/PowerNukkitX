package cn.nukkit.network.translator.itemdowngrader;

import it.unimi.dsi.fastutil.Pair;

public interface ItemDowngraderSchema {

    Pair<String, Integer> downgrade(String name, Integer meta);

    int getTargetProtocolVersion();

    int getSourceProtocolVersion();
}
