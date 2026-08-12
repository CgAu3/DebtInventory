package io.github.cgau3.debtinventory.core;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import io.github.cgau3.debtinventory.events.DebtChangedEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;

public class DebtManager {

    public static String DEBT_DATA_ID = ResourceLocation.fromNamespaceAndPath(
        DebtInventory.MODID, "debt_value"
    ).toString();

    public static int getDebt(Player player) {
        return player.getPersistentData().getInt(DEBT_DATA_ID);
    }

    public static void setDebt(Player player, int debt) {
        DebtChangedEvent event = new DebtChangedEvent(player, getDebt(player), debt);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {
            player.getPersistentData().putInt(DEBT_DATA_ID, event.getNewDebt());
        }
    }

    /**
     * 这里的addition可以是负数，换句话说“抵扣”也是addDebt
     * 但是它不会让debt扣成负数
     * @param player 玩家
     * @param addition 增/减的债务值
     */
    public static void addDebt(Player player, int addition) {
        CompoundTag data = player.getPersistentData();
        int debt = data.getInt(DEBT_DATA_ID) + addition;
        DebtChangedEvent event = new DebtChangedEvent(player, getDebt(player), debt);
        MinecraftForge.EVENT_BUS.post(event);
        debt = Math.max(event.getNewDebt(), 0);
        if (!event.isCanceled()){
            data.putInt(DEBT_DATA_ID, debt);
        }
    }

    public static void syncDebt(ServerPlayer player) {
        ModPacketHandler.INSTANCE
            .send(
                PacketDistributor.PLAYER.with(() -> player),
                new DebtSyncMessage(player.getUUID(), DebtManager.getDebt(player))
            );
    }

    protected static void directSetDebt(LocalPlayer player, int debt) {
        player.getPersistentData().putInt(DEBT_DATA_ID, debt);
    }

    public static int countDebt(Inventory inv) {
        NonNullList<ItemStack> allItems = NonNullList.create();
        allItems.addAll(inv.items);
        allItems.addAll(inv.armor);
        allItems.addAll(inv.offhand);
        int sum = 0;
        int counter;
        int stackSize;
        for (ItemStack stack : allItems) {
            if (!stack.isEmpty()) {
                stackSize = stack.getMaxStackSize();
                if (stackSize >= 64) counter = Config.debtStack64;
                else if (stackSize >= 16) counter = Config.debtStack16;
                else if (stackSize == 1) counter = Config.debtStack1;
                else counter = Config.debtStackOther;
                sum += counter * stack.getCount();
            }
        }
        return sum;
    }

}
