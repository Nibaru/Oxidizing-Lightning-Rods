package games.twinhead.oxidizingrods.mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import games.twinhead.oxidizingrods.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(PointOfInterestTypes.class)
public class PointOfInterestTypesMixin {


//    @Final
//    @Shadow
//    public static final Map<BlockState, RegistryEntry<PointOfInterestType>> POI_STATES_TO_TYPE = new HashMap<>();

    @Inject(method = "of", at = @At("HEAD"), cancellable = true)
    private static void of(String string, CallbackInfoReturnable<RegistryKey<PointOfInterestType>> cir) {
        if(Objects.equals(string, "lightning_rod")){
            cir.setReturnValue(RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(string+"_old")));
        }
    }

    @Inject(method = "registerAndGetDefault", at = @At("RETURN"), cancellable = true)
    private static void getDefault(Registry<PointOfInterestType> registry, CallbackInfoReturnable<PointOfInterestType> cir) {
        PointOfInterestType pointOfInterestType = new PointOfInterestType(getStatesOfBlock(Blocks.END_ROD), 0, 1);
        cir.setReturnValue(pointOfInterestType);
    }

    @Inject(method = "register", at = @At("RETURN"), cancellable = true)
    private static void register(Registry<PointOfInterestType> registry, RegistryKey<PointOfInterestType> registryKey, Set<BlockState> set, int i, int j, CallbackInfoReturnable<PointOfInterestType> cir) {
        if(registryKey == PointOfInterestTypes.LIGHTNING_ROD){
            System.out.println("Running");
            PointOfInterestType pointOfInterestType = new PointOfInterestType(getStatesOfBlock(Blocks.END_ROD), i, j);
            //Registry.POINT_OF_INTEREST_TYPE.register(registry, (RegistryKey)registryKey, pointOfInterestType);
            //registerStates(registry.entryOf(registryKey));
            cir.setReturnValue(pointOfInterestType);
        }
    }

    private static Set<BlockState> getStatesOfBlock(Block block) {
        return ImmutableSet.copyOf((Collection)block.getStateManager().getStates());
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static void registerStates(RegistryEntry<PointOfInterestType> registryEntry) {
        ((PointOfInterestType)registryEntry.comp_349()).blockStates().forEach((blockState) -> {
            if(!getStatesOfBlock(Blocks.LIGHTNING_ROD).contains(blockState)){
                RegistryEntry<PointOfInterestType> registryEntry2 = (RegistryEntry) PointOfInterestTypes.POI_STATES_TO_TYPE.put(blockState, registryEntry);
                if (registryEntry2 != null) {
                    throw (IllegalStateException) Util.throwOrPause(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", blockState)));
                }
            }
        });
    }


}
