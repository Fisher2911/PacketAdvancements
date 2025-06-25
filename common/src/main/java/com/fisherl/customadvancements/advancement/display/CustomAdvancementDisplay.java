package com.fisherl.customadvancements.advancement.display;

import com.fisherl.customadvancements.packet.PacketAdvancementDisplay;
import com.fisherl.customadvancements.packet.PacketFrameType;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public class CustomAdvancementDisplay {

    private final Component title;
    private final Component description;
    private final ItemStack itemStack;
    private final Frame frame;
    private final Flags flags;
    private final @Nullable Key backgroundTexture;
    private final float x;
    private final float y;

    public CustomAdvancementDisplay(Component title, Component description, ItemStack itemStack, Frame frame, Flags flags, @Nullable Key backgroundTexture, float x, float y) {
        this.title = title;
        this.description = description;
        this.itemStack = itemStack;
        this.frame = frame;
        this.flags = flags;
        this.backgroundTexture = backgroundTexture;
        this.x = x;
        this.y = y;
    }

    public CustomAdvancementDisplay(Component title, Component description, ItemStack itemStack, Frame frame, @Nullable Key backgroundTexture, float x, float y) {
        this.title = title;
        this.description = description;
        this.itemStack = itemStack;
        this.frame = frame;
        this.flags = new Flags(backgroundTexture != null, false, false);
        this.backgroundTexture = backgroundTexture;
        this.x = x;
        this.y = y;
    }


    public PacketAdvancementDisplay toPacket(boolean showToast) {
        return new PacketAdvancementDisplay(
                this.title,
                this.description,
                this.itemStack,
                this.frame.toPacket(),
                this.flags.withShowToast(showToast).toInt(),
                this.backgroundTexture == null ? null : new ResourceLocation(this.backgroundTexture.namespace(), this.backgroundTexture.value()),
                this.x,
                this.y
        );
    }

    public Component title() {
        return this.title;
    }

    public Component description() {
        return this.description;
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }

    public Frame frame() {
        return this.frame;
    }

    public Flags flags() {
        return this.flags;
    }

    public @Nullable Key backgroundTexture() {
        return this.backgroundTexture;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public enum Frame {

        TASK,
        CHALLENGE,
        GOAL;

        public PacketFrameType toPacket() {
            return switch (this) {
                case TASK -> PacketFrameType.TASK;
                case CHALLENGE -> PacketFrameType.CHALLENGE;
                case GOAL -> PacketFrameType.GOAL;
            };
        }

    }

    public record Flags(
            boolean hasBackgroundTexture,
            boolean showToast,
            boolean hidden
    ) {

        public Flags withBackgroundTexture(boolean hasBackgroundTexture) {
            if (this.hasBackgroundTexture == hasBackgroundTexture) {
                return this;
            }
            return new Flags(hasBackgroundTexture, this.showToast, this.hidden);
        }

        public Flags withShowToast(boolean showToast) {
            if (this.showToast == showToast) {
                return this;
            }
            return new Flags(this.hasBackgroundTexture, showToast, this.hidden);
        }

        public Flags withHidden(boolean hidden) {
            if (this.hidden == hidden) {
                return this;
            }
            return new Flags(this.hasBackgroundTexture, this.showToast, hidden);
        }

        public int toInt() {
            return PacketAdvancementDisplay.Flags.createFlags(this.hasBackgroundTexture, this.showToast, this.hidden);
        }

    }

}
