package io.github.cgau3.debtinventory.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class DebtChangedEvent extends Event {
    private final Player player;
    private final int oldDebt;
    private int newDebt;

    public DebtChangedEvent(Player player, int oldDebt, int newDebt) {
        this.player = player;
        this.oldDebt = oldDebt;
        this.newDebt = newDebt;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOldDebt() {
        return oldDebt;
    }

    public int getNewDebt() {
        return newDebt;
    }

    public void setNewDebt(int value) {
        newDebt = value;
    }

}
