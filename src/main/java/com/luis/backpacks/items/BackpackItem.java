package com.luis.backpacks.items;

import net.minecraft.item.Item;

public class BackpackItem extends Item {
    public enum Size {
        SMALL(9),
        MEDIUM(18),
        LARGE(27);

        public final int slots;
        Size(int slots) { this.slots = slots; }
    }

    private final Size size;

    public BackpackItem(Settings settings, Size size) {
        super(settings);
        this.size = size;
    }

    public Size getSize() {
        return size;
    }
}
