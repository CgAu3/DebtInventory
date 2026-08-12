package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.ModAttributeModifiers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class DebtEventListener {

    @SubscribeEvent
    public static void onDebtChanged(DebtChangedEvent event) {
        // 这个事件实际上并不一定是服务端才发起的，客户端和服务端会各算各的
        Player player = event.getPlayer();
        int newDebt = event.getNewDebt();
        int oldDebt = event.getOldDebt();
        int d = newDebt - oldDebt;
        if (d < 0) {
            if (player.getMainHandItem().is(Items.RECOVERY_COMPASS)
                || player.getOffhandItem().is(Items.RECOVERY_COMPASS)) {
                event.setNewDebt(
                    oldDebt + Math.round(d * (1.0f + Config.deductionBuffCompass))
                );
            }
        }
        ModAttributeModifiers.processPotentialModifiers(player, event.getNewDebt());
        // processPotential是只有服务端才起作用的
    }
}
