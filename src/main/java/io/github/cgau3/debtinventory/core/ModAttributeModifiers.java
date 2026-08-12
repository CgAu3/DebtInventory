package io.github.cgau3.debtinventory.core;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class ModAttributeModifiers {
    public static final AttributeModifier LUCK_MODIFIER = new AttributeModifier(
        DebtInventory.MODID + ":has_debt",
        -1,
        AttributeModifier.Operation.ADDITION
    );

    public static void processPotentialModifiers(Player player, int debt) {
        if (!(player instanceof ServerPlayer)) return;
        AttributeInstance luck = player.getAttribute(Attributes.LUCK);
        if (luck == null) return;
        if (debt > 0 && Config.enablePunishLuck) {
            if (!luck.hasModifier(LUCK_MODIFIER))
                luck.addTransientModifier(LUCK_MODIFIER);
        }
        if (debt <= 0 || !Config.enablePunishLuck) {
            if (luck.hasModifier(LUCK_MODIFIER))
                luck.removeModifier(LUCK_MODIFIER);
        }
    }
}
