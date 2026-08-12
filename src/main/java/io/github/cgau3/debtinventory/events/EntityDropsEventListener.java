package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class EntityDropsEventListener {
    @SubscribeEvent
    public static void onDropExp(LivingExperienceDropEvent event) {
        // 这个事件是只有服务端才发起的
        // 客户端会不知道debt变化，需要等下次同步
        Player player = event.getAttackingPlayer();
        if (player != null && DebtManager.getDebt(player) > 0) {
            int x = Math.round(
                event.getEntity().getMaxHealth() * Config.deductionMobHealth
            );
            x = Math.min(Config.deductionMobMax, x);
            if (event.getEntity() instanceof Phantom) {
                x += Config.deductionPhantom;
            }
            DebtManager.addDebt(player, -x);
            if (DebtManager.getDebt(player) > 0) {
                int exp = event.getDroppedExperience();
                exp = Math.round(
                    exp * (1.0f - Config.punishMobExp)
                );
                event.setDroppedExperience(exp);
            }
        }
    }
}
