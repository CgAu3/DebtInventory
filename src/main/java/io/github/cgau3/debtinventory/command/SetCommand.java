package io.github.cgau3.debtinventory.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("set").requires(
                cs -> cs.hasPermission(2))
            .then(Commands.argument("target", EntityArgument.player())
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(SetCommand::run)
                )
            );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "target");
        String name = targetPlayer.getName().getString();
        int value = IntegerArgumentType.getInteger(ctx, "value");
        DebtManager.setDebt(targetPlayer, value);
        int debt = DebtManager.getDebt(targetPlayer);
        ctx.getSource().sendSuccess(
            () -> Component.translatable("commands.debt.set_success", name, debt),
            true
        );
        return Command.SINGLE_SUCCESS;
    }
}
