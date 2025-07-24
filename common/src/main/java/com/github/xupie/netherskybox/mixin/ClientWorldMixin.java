package com.github.xupie.netherskybox.mixin;

import com.github.xupie.netherskybox.NetherSkybox;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Inject(method = "getDimensionEffects", at = @At("HEAD"), cancellable = true)
    private void onGetDimensionEffects(CallbackInfoReturnable<DimensionEffects> cir) {
        RegistryKey<World> key = ((ClientWorld)(Object)this).getRegistryKey();
        Identifier dimId = key.getValue();

        if (dimId.equals(Identifier.of("minecraft", "the_nether"))) {
            cir.setReturnValue(NetherSkybox.getCustomEffect());
            cir.cancel();
        }
    }
}
