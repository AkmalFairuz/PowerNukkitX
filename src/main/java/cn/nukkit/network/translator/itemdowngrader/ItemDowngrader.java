package cn.nukkit.network.translator.itemdowngrader;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class ItemDowngrader {

    public static final ItemDowngrader INSTANCE = new ItemDowngrader();

    private final List<ItemDowngraderSchema> schemas = new ObjectArrayList<>();

    public ItemDowngrader() {
        addSchema(new ItemDowngraderSchema_748_to_729());
        addSchema(new ItemDowngraderSchema_729_to_712());
        addSchema(new ItemDowngraderSchema_712_to_686());
    }

    public Pair<String, Integer> downgrade(String name, Integer meta, int sourceProtocol, int targetProtocol){
        if(sourceProtocol == targetProtocol){
            return Pair.of(name, meta);
        }

        var downgraded = Pair.of(name, meta);
        for (ItemDowngraderSchema schema : schemas) {
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

    private void addSchema(ItemDowngraderSchema schema) {
        this.schemas.add(schema);
    }
}
