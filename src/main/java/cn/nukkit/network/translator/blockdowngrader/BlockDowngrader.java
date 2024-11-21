package cn.nukkit.network.translator.blockdowngrader;

import cn.nukkit.nbt.tag.CompoundTag;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class BlockDowngrader {

    public static final BlockDowngrader INSTANCE = new BlockDowngrader();

    private final List<BlockDowngraderSchema> schemas = new ObjectArrayList<>();

    public BlockDowngrader() {
        addSchema(new BlockDowngraderSchema_748_to_729());
        addSchema(new BlockDowngraderSchema_729_to_712());
        addSchema(new BlockDowngraderSchema_712_to_686());
    }

    public Pair<String, CompoundTag> downgrade(String name, CompoundTag states, int sourceProtocol, int targetProtocol){
        if(sourceProtocol == targetProtocol){
            return Pair.of(name, states);
        }

        var downgraded = Pair.of(name, states);
        for (BlockDowngraderSchema schema : schemas) {
            if (schema.getSourceProtocolVersion() == sourceProtocol) {
                downgraded = schema.downgrade(downgraded.left(), downgraded.right());
                sourceProtocol = schema.getTargetProtocolVersion();
                if(sourceProtocol == targetProtocol){
                    return downgraded;
                }
            }
        }

        throw new IllegalArgumentException("No schema found to downgrade from " + sourceProtocol + " to " + targetProtocol);
    }

    private void addSchema(BlockDowngraderSchema schema) {
        this.schemas.add(schema);
    }
}
