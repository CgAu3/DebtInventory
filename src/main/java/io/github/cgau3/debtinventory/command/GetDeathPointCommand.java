package io.github.cgau3.debtinventory.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cgau3.debtinventory.core.DeathPointManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GetDeathPointCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("get_death_point_info").requires(
                cs -> cs.hasPermission(1))
            .then(Commands.argument("target", EntityArgument.player())
                .executes(GetDeathPointCommand::run)
            );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "target");
        String name = targetPlayer.getName().getString();
        CompoundTag tag = DeathPointManager.getDeathPointInfo(targetPlayer);
        ctx.getSource().sendSuccess(
            () -> Component.translatable("commands.debt.get_death_point_info", name, tag.toString()),
            false
        );
        return Command.SINGLE_SUCCESS;
    }
}
