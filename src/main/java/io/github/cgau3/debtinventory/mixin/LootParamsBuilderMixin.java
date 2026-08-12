package io.github.cgau3.debtinventory.mixin;

import io.github.cgau3.debtinventory.Config;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(targets = "net.minecraft.world.level.storage.loot.LootParams$Builder")
public abstract class LootParamsBuilderMixin {
    @Shadow
    public abstract <T> T getParameter(LootContextParam<T> p_287646_);

    @Shadow
    @Nullable
    public abstract <T> T getOptionalParameter(LootContextParam<T> p_287759_);

    @Shadow
    public abstract ServerLevel getLevel();

    @Inject(
        method = "create",
        at = @At("TAIL")
    ) void onCreateLootParams(LootContextParamSet set, CallbackInfoReturnable<LootParams> cir) {
        if (set == LootContextParamSets.CHEST) {
            Entity e = getOptionalParameter(LootContextParams.THIS_ENTITY);
            if (e instanceof Player player) {
                if (DebtManager.getDebt(player) > 0) {
                    Vec3 pos = getParameter(LootContextParams.ORIGIN);
                    Entity e1 = getOptionalParameter(LootContextParams.KILLER_ENTITY);
                    if (e1 != null) {
                        DebtManager.addDebt(player, -Config.deductionChestLoot);
                    }
                    else {
                        BlockPos bp = BlockPos.containing(pos);
                        if (getLevel().getBlockEntity(bp) instanceof RandomizableContainerBlockEntity) {
                            DebtManager.addDebt(player, -Config.deductionChestLoot);
                        } else if (getLevel().getBlockEntity(bp) instanceof BrushableBlockEntity) {
                            DebtManager.addDebt(player, -Config.deductionArcheologyLoot);
                        }
                    }
                }
            }
        }
    }
}
