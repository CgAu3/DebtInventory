package io.github.cgau3.debtinventory.client;

import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class DebtHud {
    private static final ResourceLocation HUD_TEXTURE =
        ResourceLocation.fromNamespaceAndPath(
            DebtInventory.MODID,
            "textures/gui/debt_hud.png"
        );

    public static final IGuiOverlay DEBT_HUD = (
        gui,
        guiGraphics,
        partialTick,
        screenWidth,
        screenHeight) -> {
        Minecraft mc = gui.getMinecraft();
        Font font = mc.font;

        int wholeX = 10;
        int wholeY = screenHeight - 30;
        int iconOffsetX = 10;
        int iconOffsetY = 5;
        int textOffsetX = 20;
        int textOffsetY = 6;
        int currentValue = 0;

        if (mc.player != null) {
            currentValue = DebtManager.getDebt(mc.player);
        }

        if (currentValue <= 0) return;

        float x = (int)(currentValue / 6.4f);
        x = x / 10f;

        // 绘制一个背景
        guiGraphics.fill(wholeX, wholeY, wholeX + 50, wholeY + 20, 0x8000C0FF);

        // 绘制贴图 (9x9)
        guiGraphics.blit(
            HUD_TEXTURE,
            wholeX + iconOffsetX,
            wholeY + iconOffsetY,
            0,
            0,
            9,
            9,
            9,
            9
        );

        if (x < 1000) {
            guiGraphics.drawString(
                font,
                String.format("%.1f", x),
                wholeX + textOffsetX,
                wholeY + textOffsetY,
                0xFFFFFF
            );
        }
        else {
            guiGraphics.drawString(
                font,
                "999+",
                wholeX + textOffsetX,
                wholeY + textOffsetY,
                0xFFFFFF
            );
        }
    };

}
