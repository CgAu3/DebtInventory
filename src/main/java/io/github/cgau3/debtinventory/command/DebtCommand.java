package io.github.cgau3.debtinventory.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cgau3.debtinventory.DebtInventory;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DebtInventory.MODID)
public class DebtCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder =
            Commands.literal("debt")
                .then(GetCommand.register())
                .then(AddCommand.register())
                .then(ClearCommand.register())
                .then(SetCommand.register())
                .then(GetDeathPointCommand.register());
        dispatcher.register(builder);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        DebtCommand.register(event.getDispatcher());
    }

}
