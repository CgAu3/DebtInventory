package io.github.cgau3.debtinventory.mixin;

import io.github.cgau3.debtinventory.core.IGameRulesMixinAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {
    @Redirect(
        method = "getExperienceReward",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z")
    )
    boolean inGetExpDropGetKeepInventory(GameRules instance, GameRules.Key<GameRules.BooleanValue> p_46208_) {
        return ((IGameRulesMixinAccessor)instance).debtInventory$getRealKeepInventory();
    }
}
