package io.github.cgau3.debtinventory.events;

import io.github.cgau3.debtinventory.DebtInventory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabEventListener {
    @SubscribeEvent
    public static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(DebtInventory.DEBT_SHEET.get());
        }
    }
}
