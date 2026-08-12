package io.github.cgau3.debtinventory.core;

import io.github.cgau3.debtinventory.DebtInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(DebtInventory.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(
            id++,
            DebtSyncMessage.class,
            DebtSyncMessage::encode,
            DebtSyncMessage::new,
            DebtSyncMessage.Handler::onMessage
        );
    }
}
