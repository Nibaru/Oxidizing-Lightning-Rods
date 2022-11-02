package games.twinhead.oxidizingrods.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin extends RodBlock implements Waterloggable, Oxidizable {

    public LightningRodBlockMixin(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        this.tickDegradation(blockState, serverWorld, blockPos, random);
    }

    public boolean hasRandomTicks(BlockState blockState) {
        return Oxidizable.getIncreasedOxidationBlock(blockState.getBlock()).isPresent();
    }

    public Oxidizable.OxidationLevel getDegradationLevel() {
        return OxidationLevel.UNAFFECTED;
    }
}
