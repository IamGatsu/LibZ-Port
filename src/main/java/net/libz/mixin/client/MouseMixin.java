package net.libz.mixin.client;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.fabricmc.api.Environment;
import net.libz.access.MouseAccessor;
import net.fabricmc.api.EnvType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;

@Environment(EnvType.CLIENT)
@Mixin(MouseHandler.class)
public class MouseMixin implements MouseAccessor {

    @Shadow
    private double xpos;
    @Shadow
    private double ypos;
    @Shadow
    private Minecraft minecraft;

    @Override
    public void setMousePosition(int xPos, int yPos) {
        this.xpos = xPos;
        this.ypos = yPos;
        // NOTE: replaces Yarn's InputUtil.setCursorParameters helper with the underlying GLFW calls directly
        // (mode 212993 == GLFW_CURSOR_NORMAL) to avoid depending on a mapping-specific convenience method.
        long windowHandle = this.minecraft.getWindow().handle();
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        GLFW.glfwSetCursorPos(windowHandle, this.xpos, this.ypos);
    }
}
