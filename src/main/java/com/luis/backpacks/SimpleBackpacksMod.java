package com.luis.backpacks;

import com.luis.backpacks.commands.BackpackCommand;
import com.luis.backpacks.items.BackpackItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleBackpacksMod implements ModInitializer {
    public static final String MOD_ID = "simplebackpacks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item BACKPACK_SMALL = new BackpackItem(new Item.Settings(), BackpackItem.Size.SMALL);
    public static final Item BACKPACK_MEDIUM = new BackpackItem(new Item.Settings(), BackpackItem.Size.MEDIUM);
    public static final Item BACKPACK_LARGE = new BackpackItem(new Item.Settings(), BackpackItem.Size.LARGE);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SimpleBackpacks");
        registerItems();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                BackpackCommand.register(dispatcher, registryAccess, environment));
    }

    private void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "backpack_small"), BACKPACK_SMALL);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "backpack_medium"), BACKPACK_MEDIUM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "backpack_large"), BACKPACK_LARGE);
    }
}
