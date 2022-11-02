package games.twinhead.oxidizingrods.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import games.twinhead.oxidizingrods.OxidizingRods;
import games.twinhead.oxidizingrods.block.OxidizingLightningRod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BlockRegistry {
    //public static final Block UNAFFECTED_LIGHTING_ROD;
    public static final Block EXPOSED_LIGHTING_ROD;
    public static final Block WEATHERED_LIGHTING_ROD;
    public static final Block OXIDIZED_LIGHTING_ROD;

    public static final Block WAXED_UNAFFECTED_LIGHTING_ROD;
    public static final Block WAXED_EXPOSED_LIGHTING_ROD;
    public static final Block WAXED_WEATHERED_LIGHTING_ROD;
    public static final Block WAXED_OXIDIZED_LIGHTING_ROD;

    public static final Set<BlockState> LIGHTNING_RODS;

    public static PointOfInterestType pointOfInterestType;

    public static RegistryKey<PointOfInterestType> LIGHTNING_ROD;

    public static boolean POI_SET = false;


    public BlockRegistry() {}

    public static void registerOxidizable(){
        OxidizableBlocksRegistry.registerOxidizableBlockPair(Blocks.LIGHTNING_ROD, EXPOSED_LIGHTING_ROD);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_LIGHTING_ROD, WEATHERED_LIGHTING_ROD);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_LIGHTING_ROD, OXIDIZED_LIGHTING_ROD);

        OxidizableBlocksRegistry.registerWaxableBlockPair(Blocks.LIGHTNING_ROD, WAXED_UNAFFECTED_LIGHTING_ROD);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_LIGHTING_ROD, WAXED_EXPOSED_LIGHTING_ROD);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_LIGHTING_ROD, WAXED_WEATHERED_LIGHTING_ROD);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_LIGHTING_ROD, WAXED_OXIDIZED_LIGHTING_ROD);

        LIGHTNING_ROD = of("lightning_rod");


        register(Registry.POINT_OF_INTEREST_TYPE, LIGHTNING_ROD, LIGHTNING_RODS, 0, 1);
        PointOfInterestTypes.LIGHTNING_ROD = LIGHTNING_ROD;

    }

    private static RegistryKey<PointOfInterestType> of(String string) {
        return RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(string));
    }

    private static PointOfInterestType register(Registry<PointOfInterestType> registry, RegistryKey<PointOfInterestType> registryKey, Set<BlockState> set, int i, int j) {
        PointOfInterestType pointOfInterestType = new PointOfInterestType(set, i, j);
        BlockRegistry.pointOfInterestType = pointOfInterestType;
        Registry.POINT_OF_INTEREST_TYPE.register(registry, (RegistryKey)registryKey, pointOfInterestType);
        registerStates(registry.entryOf(registryKey));
        return pointOfInterestType;
    }

    private static void registerStates(RegistryEntry<PointOfInterestType> registryEntry) {
        ((PointOfInterestType)registryEntry.comp_349()).blockStates().forEach((blockState) -> {
            RegistryEntry<PointOfInterestType> registryEntry2 = (RegistryEntry) PointOfInterestTypes.POI_STATES_TO_TYPE.put(blockState, registryEntry);
            if (registryEntry2 != null) {
                throw (IllegalStateException)Util.throwOrPause(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", blockState)));
            }
        });
    }


    private static Block register(String name, Block block) {
        Registry.register(Registry.ITEM, new Identifier(OxidizingRods.mod_id, name), new BlockItem(block, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        return (Block) Registry.register(Registry.BLOCK, new Identifier(OxidizingRods.mod_id, name), block);
    }

    static {
        //UNAFFECTED_LIGHTING_ROD = register("unaffected_lightning_rod", new OxidizingLightningRod(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        EXPOSED_LIGHTING_ROD = register("exposed_lightning_rod", new OxidizingLightningRod(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        WEATHERED_LIGHTING_ROD = register("weathered_lightning_rod", new OxidizingLightningRod(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        OXIDIZED_LIGHTING_ROD = register("oxidized_lightning_rod", new OxidizingLightningRod(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;

        WAXED_UNAFFECTED_LIGHTING_ROD = register("waxed_unaffected_lightning_rod", new LightningRodBlock(FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        WAXED_EXPOSED_LIGHTING_ROD = register("waxed_exposed_lightning_rod", new LightningRodBlock(FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        WAXED_WEATHERED_LIGHTING_ROD = register("waxed_weathered_lightning_rod", new LightningRodBlock(FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;
        WAXED_OXIDIZED_LIGHTING_ROD = register("waxed_oxidized_lightning_rod", new LightningRodBlock(FabricBlockSettings.copyOf(Blocks.LIGHTNING_ROD))) ;

        LIGHTNING_RODS = (Set) ImmutableList.of(
                Blocks.LIGHTNING_ROD,
                EXPOSED_LIGHTING_ROD,
                WEATHERED_LIGHTING_ROD,
                OXIDIZED_LIGHTING_ROD,
                WAXED_UNAFFECTED_LIGHTING_ROD,
                WAXED_EXPOSED_LIGHTING_ROD,
                WAXED_WEATHERED_LIGHTING_ROD,
                WAXED_OXIDIZED_LIGHTING_ROD

        ).stream().flatMap((block) -> {
            return block.getStateManager().getStates().stream();
        }).collect(ImmutableSet.toImmutableSet());
        registerOxidizable();
    }
}
