package com.luis.backpacks.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.RegistrationEnvironment;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;

public class BackpackCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                net.minecraft.command.CommandRegistryAccess registryAccess,
                                RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("backpack")
                .executes(ctx -> openSelf(ctx))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .requires(src -> src.hasPermissionLevel(2))
                        .executes(ctx -> openOther(ctx)))
                .then(CommandManager.literal("give")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("type", StringArgumentType.word())
                                        .suggests(BackpackCommand::suggestSizes)
                                        .executes(ctx -> giveBackpack(ctx)))))
        );
    }

    private static int openSelf(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.translatable("command.simplebackpacks.open"), false);
        return 1;
    }

    private static int openOther(CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
            ctx.getSource().sendFeedback(() -> Text.literal("Opened "+target.getName().getString()+"'s backpack."), false);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendError(Text.literal("Player not found."));
            return 0;
        }
    }

    private static int giveBackpack(CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
            String type = StringArgumentType.getString(ctx, "type");
            ctx.getSource().sendFeedback(() -> Text.translatable("command.simplebackpacks.given", target.getName().getString()), false);
            return 1;
        } catch (IllegalArgumentException ex) {
            ctx.getSource().sendError(Text.literal("Usage: /backpack give <player> <small|medium|large>"));
            return 0;
        }
    }

    private static CompletableFuture<Suggestions> suggestSizes(CommandContext<ServerCommandSource> ctx, SuggestionsBuilder builder) {
        builder.suggest("small");
        builder.suggest("medium");
        builder.suggest("large");
        return builder.buildFuture();
    }
}
