package io.github.cgau3.debtinventory.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class DebtSyncMessage {
    private final UUID player;
    private final int debt;

    public DebtSyncMessage(UUID player, int debt)  {
        this.player = player;
        this.debt = debt;

    }

    public DebtSyncMessage(FriendlyByteBuf buf) {
        this.player = buf.readUUID();
        this.debt = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.player);
        buf.writeInt(this.debt);
    }

    public static class Handler {
        public static void onMessage(
            DebtSyncMessage message,
            Supplier<NetworkEvent.Context> ctx
        ) {
            ctx.get().enqueueWork(() -> {
                // 如果需要：ClientLevel level = Minecraft.getInstance().level;
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.getUUID().equals(message.player)) {
                    DebtManager.directSetDebt(player, message.debt);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
