package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class FishingHookEventListener {
    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        // 这个事件是只有服务端才发起的
        // 客户端会不知道debt变化，需要等下次同步
        Player player = event.getEntity();
        if (DebtManager.getDebt(player) > 0) {
            List<ItemStack> stacks = event.getDrops();
            for (ItemStack stack : stacks) {
                int m;
                if (stack.getMaxStackSize() >= 64) m = Config.deductionFish64;
                else if (stack.getMaxStackSize() >= 16) m = Config.deductionFish16;
                else if (stack.getMaxStackSize() == 1) m = Config.deductionFish1;
                else m = Config.deductionFishOther;
                int x = m * stack.getCount();
                if (x <= 0) x = 2;
                DebtManager.addDebt(player, -x);
            }
        }
    }
}
