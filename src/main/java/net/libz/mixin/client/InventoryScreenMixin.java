package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.libz.api.Tab;
import net.libz.util.DrawTabHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.network.chat.Component;

// Could inject into Screen class but imo uneccessary
@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractRecipeBookScreen<InventoryMenu> implements Tab {

    public InventoryScreenMixin(InventoryMenu screenHandler, Inventory playerInventory, Component text) {
        // AbstractRecipeBookScreen has taken an explicit RecipeBookComponent<?> constructor
        // argument since MC 1.21.2 (previously built internally). This super() call is never
        // actually executed at runtime -- Mixin discards it and merges into the real
        // InventoryScreen constructor -- so null is safe here, it only needs to satisfy javac.
        super(screenHandler, null, playerInventory, text);
    }

    // InventoryScreen no longer overrides mouseClicked itself in 26.2 -- it's purely inherited
    // from AbstractContainerScreen (confirmed against the 26.2 client jar), so @Inject can't find
    // it as a target in InventoryScreen's own bytecode ("No refMap loaded" / InvalidInjectionException
    // at runtime, even though it compiles fine). Instead, declare a real override with matching
    // signature and no @Inject annotation -- Mixin merges this directly into InventoryScreen as its
    // own mouseClicked override, regardless of where the method is originally declared in the
    // hierarchy, then we delegate to super for normal behaviour.
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        DrawTabHelper.onTabButtonClick(minecraft, this, this.leftPos, this.topPos, event.x(), event.y(), this.hoveredSlot != null);
        return super.mouseClicked(event, doubleClick);
    }

    // renderBg was renamed to extractBackground and its parameters reordered to
    // (context, mouseX, mouseY, partialTick) (confirmed against the 26.2 client jar).
    @Inject(method = "extractBackground", at = @At("TAIL"))
    protected void drawBackgroundMixin(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        DrawTabHelper.drawTab(minecraft, context, this, leftPos, topPos, mouseX, mouseY);
    }
}
