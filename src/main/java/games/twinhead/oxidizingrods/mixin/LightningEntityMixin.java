package games.twinhead.oxidizingrods.mixin;

import games.twinhead.oxidizingrods.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LightningEntity.class)
public class LightningEntityMixin extends Entity {
    public LightningEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected void initDataTracker() {}

    @Shadow
    protected void readCustomDataFromNbt(NbtCompound nbtCompound) {}

    @Shadow
    protected void writeCustomDataToNbt(NbtCompound nbtCompound) {}

    @Shadow
    public Packet<?> createSpawnPacket() {
        return null;
    }

    @Inject(method = "powerLightningRod", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onPowerLightningRod(CallbackInfo ci) {
        BlockPos blockPos = getAffectedBlockPos();
        BlockState blockState = ((LightningEntity)(Object)this).world.getBlockState(blockPos);

        if (blockState.isOf(BlockRegistry.EXPOSED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.WEATHERED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.OXIDIZED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.WAXED_UNAFFECTED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.WAXED_OXIDIZED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.WAXED_WEATHERED_LIGHTING_ROD) ||
                blockState.isOf(BlockRegistry.WAXED_EXPOSED_LIGHTING_ROD)
        ) {
            ((LightningRodBlock)blockState.getBlock()).setPowered(blockState, this.world, blockPos);
        }
    }


    private BlockPos getAffectedBlockPos() {
        Vec3d vec3d = this.getPos();
        return new BlockPos(vec3d.x, vec3d.y - 1.0E-6, vec3d.z);
    }



}
