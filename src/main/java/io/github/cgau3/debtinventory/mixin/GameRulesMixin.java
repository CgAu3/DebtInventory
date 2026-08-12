package io.github.cgau3.debtinventory.mixin;

import io.github.cgau3.debtinventory.core.IGameRulesMixinAccessor;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public abstract class GameRulesMixin implements IGameRulesMixinAccessor {

    @Unique
    public boolean debtInventory$getRealKeepInventoryFlag = false;

    @Shadow
    public abstract boolean getBoolean(GameRules.Key<GameRules.BooleanValue> p_46208_);

    @Inject(
        method = "getBoolean",
        at = @At("HEAD"),
        cancellable = true)
    void onGetBoolean(
        GameRules.Key<GameRules.BooleanValue> key,
        CallbackInfoReturnable<Boolean> cir) {
        if (key == GameRules.RULE_KEEPINVENTORY && !debtInventory$getRealKeepInventoryFlag) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public boolean debtInventory$getRealKeepInventory() {
        debtInventory$getRealKeepInventoryFlag = true;
        boolean x = this.getBoolean(GameRules.RULE_KEEPINVENTORY);
        debtInventory$getRealKeepInventoryFlag = false;
        return x;
    }

}
