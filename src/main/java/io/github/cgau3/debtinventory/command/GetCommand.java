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

public class GetCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("get").requires(
            cs -> cs.hasPermission(0))
            .then(Commands.argument("target", EntityArgument.player())
            .executes(GetCommand::run)
        );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "target");
        String name = targetPlayer.getName().getString();
        int debt = DebtManager.getDebt(targetPlayer);
        ctx.getSource().sendSuccess(
            () -> Component.translatable("commands.debt.get_success", name, debt),
            false
        );
        return Command.SINGLE_SUCCESS;
    }
}
