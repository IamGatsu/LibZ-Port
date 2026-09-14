package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;

// Confirmed against the 26.2 client jar: Yarn's "IntegratedServerLoader" / "tryLoad" do not
// exist under Mojang's own names. The equivalent class is WorldOpenFlows, and the equivalent
// static method (same parameter types: Minecraft, CreateWorldScreen, Lifecycle, Runnable, boolean)
// is called confirmWorldCreation.
@Environment(EnvType.CLIENT)
@Mixin(value = WorldOpenFlows.class, priority = 999)
public class IntegratedServerLoaderMixin {

    @ModifyVariable(method = "confirmWorldCreation", at = @At("HEAD"), ordinal = 0)
    private static boolean startMixin(boolean original) {
        return false;
    }

}
