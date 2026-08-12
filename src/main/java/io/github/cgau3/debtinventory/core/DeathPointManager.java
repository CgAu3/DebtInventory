package io.github.cgau3.debtinventory.core;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.DebtInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class DeathPointManager {

    public static String DEATH_POINT_ID = ResourceLocation.fromNamespaceAndPath(
        DebtInventory.MODID, "death_point"
    ).toString();
    public static String HAS_BEEN_TO_DEATH_POINT_ID = ResourceLocation.fromNamespaceAndPath(
        DebtInventory.MODID, "has_been_to_death_point"
    ).toString();
    public static String DEBT_OF_LAST_DEATH_ID = ResourceLocation.fromNamespaceAndPath(
        DebtInventory.MODID, "debt_of_last_death"
    ).toString();

    public static CompoundTag getDeathPointInfo(Player player) {
        CompoundTag tag = new CompoundTag();
        CompoundTag data = player.getPersistentData();
        tag.put(DEATH_POINT_ID, data.getCompound(DEATH_POINT_ID));
        tag.putBoolean(HAS_BEEN_TO_DEATH_POINT_ID, data.getBoolean(HAS_BEEN_TO_DEATH_POINT_ID));
        tag.putInt(DEBT_OF_LAST_DEATH_ID, data.getInt(DEBT_OF_LAST_DEATH_ID));
        return tag;
    }

    public static void saveDeathPointInfo(Player player) {
        CompoundTag data = player.getPersistentData();
        data.putBoolean(HAS_BEEN_TO_DEATH_POINT_ID, false);
        data.putInt(DEBT_OF_LAST_DEATH_ID, DebtManager.countDebt(player.getInventory()));
        CompoundTag axes = NbtUtils.writeBlockPos(BlockPos.containing(player.position()));
        data.put(DEATH_POINT_ID, axes);
    }

    public static void cloneDeathPointInfo(Player old, Player player) {
        CompoundTag data = player.getPersistentData();
        CompoundTag oldData = old.getPersistentData();
        data.putBoolean(HAS_BEEN_TO_DEATH_POINT_ID, oldData.getBoolean(HAS_BEEN_TO_DEATH_POINT_ID));
        data.putInt(DEBT_OF_LAST_DEATH_ID, oldData.getInt(DEBT_OF_LAST_DEATH_ID));
        data.put(DEATH_POINT_ID, oldData.getCompound(DEATH_POINT_ID));
    }

    public static void processPotentialDeathPointArrival(Player player) {
        CompoundTag data = player.getPersistentData();
        if (
            !data.contains(HAS_BEEN_TO_DEATH_POINT_ID)
                || data.getBoolean(HAS_BEEN_TO_DEATH_POINT_ID)
        ) return;
        CompoundTag axes = data.getCompound(DEATH_POINT_ID);
        BlockPos bp = NbtUtils.readBlockPos(axes);
        double d2 = player.distanceToSqr(bp.getX(), bp.getY(), bp.getZ());
        // 五格之内，认为抵达
        if (d2 <= 25) {
            data.putBoolean(HAS_BEEN_TO_DEATH_POINT_ID, true);
            int x = Math.round(
                -data.getInt(DEBT_OF_LAST_DEATH_ID) * Config.deductionDeathPoint
            );
            DebtManager.addDebt(player, x);
        }
    }

}
