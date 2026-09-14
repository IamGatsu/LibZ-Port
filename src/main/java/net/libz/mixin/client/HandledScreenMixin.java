package net.libz.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.util.DrawTabHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
@Mixin(AbstractContainerScreen.class)
public abstract class HandledScreenMixin extends Screen {

    @Shadow
    protected int leftPos;
    @Shadow
    protected int topPos;
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    public HandledScreenMixin(Component title) {
        super(title);
    }

    // render was renamed to extractRenderState; parameter order is unchanged
    // (confirmed against the 26.2 client jar, declared directly on AbstractContainerScreen).
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void renderMixin(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        DrawTabHelper.drawTab(minecraft, context, this, leftPos, topPos, mouseX, mouseY);
    }

    // mouseClicked now bundles position and button into a single MouseButtonEvent record
    // (confirmed against the 26.2 client jar). Unlike InventoryScreen, AbstractContainerScreen
    // declares mouseClicked directly, so a plain @Inject with the corrected signature works here.
    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void mouseClickedMixin(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> info) {
        DrawTabHelper.onTabButtonClick(minecraft, this, this.leftPos, this.topPos, event.x(), event.y(), this.hoveredSlot != null);
    }
}
