package org.teamvoided.dusk_autumn.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.teamvoided.dusk_autumn.DuskAutumns.LOGGER;

@Mixin(MinecraftClient.class)
public class Template {

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo info) {
        LOGGER.info("Hello from Mixin");
    }
}
