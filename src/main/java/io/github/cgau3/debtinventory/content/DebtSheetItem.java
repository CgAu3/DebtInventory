package io.github.cgau3.debtinventory.content;

import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebtSheetItem extends Item {
    public DebtSheetItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(
        @NotNull ItemStack itemStack,
        @Nullable Level level,
        @NotNull List<Component> components,
        @NotNull TooltipFlag tooltipFlag) {
        if (Minecraft.getInstance().player != null) {
            components.add(
                Component.translatable(
                    "tooltip.debt_inventory.debt_sheet.info",
                        DebtManager.getDebt(Minecraft.getInstance().player)
                    )
                    .withStyle(ChatFormatting.AQUA)
            );
        }
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.keep")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.debt")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.count")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.penalty")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.payment")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.payment2")
                .withStyle(ChatFormatting.GRAY)
        );
        components.add(
            Component.translatable("tooltip.debt_inventory.debt_sheet.payment3")
                .withStyle(ChatFormatting.GRAY)
        );
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
