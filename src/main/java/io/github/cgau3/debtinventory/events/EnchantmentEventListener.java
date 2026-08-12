package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class EnchantmentEventListener {
    @SubscribeEvent
    public static void onLootingLevel(LootingLevelEvent event) {
        // 它实际上至少应该在服务端发起
        // 如果在客户端发起了，那么我认为也不应该算是我的问题
        if (!Config.enablePunishLooting) return;
        Entity e1 = null;
        if (event.getDamageSource() != null) {
            e1 = event.getDamageSource().getEntity();
        }
        if (e1 instanceof Player player)  {
            if (DebtManager.getDebt(player) > 0) {
                int x = event.getLootingLevel();
                if (x >= 1) {
                    event.setLootingLevel(x - 1);
                }
            }
        }
    }
}
