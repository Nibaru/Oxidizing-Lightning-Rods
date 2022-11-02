package games.twinhead.oxidizingrods;

import games.twinhead.oxidizingrods.registry.BlockRegistry;
import net.fabricmc.api.ModInitializer;


public class OxidizingRods implements ModInitializer {

    public static final String mod_id="oxidizing_rods";

    @Override
    public void onInitialize() {
        new BlockRegistry();
    }
}
