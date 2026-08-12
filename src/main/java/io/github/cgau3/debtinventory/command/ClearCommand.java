package io.github.cgau3.debtinventory.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cgau3.debtinventory.core.DebtManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ClearCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("clear").requires(
                cs -> cs.hasPermission(2))
            .then(Commands.argument("target", EntityArgument.player())
                .executes(ClearCommand::run)
            );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "target");
        String name = targetPlayer.getName().getString();
        DebtManager.setDebt(targetPlayer, 0);
        ctx.getSource().sendSuccess(
            () -> Component.translatable("commands.debt.clear_success", name),
            true
        );
        return Command.SINGLE_SUCCESS;
    }
}
