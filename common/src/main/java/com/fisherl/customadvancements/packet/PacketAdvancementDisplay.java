package com.fisherl.customadvancements.packet;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PacketAdvancementDisplay {

    private Component title;
    private Component description;
    private ItemStack slot;
    private PacketFrameType frameType;
    private int flags;
    private @Nullable ResourceLocation backgroundTexture;
    private float xCord;
    private float yCord;

    public PacketAdvancementDisplay(Component title, Component description, ItemStack slot, PacketFrameType frameType, int flags, @Nullable ResourceLocation backgroundTexture, float xCord, float yCord) {
        this.title = title;
        this.description = description;
        this.slot = slot;
        this.frameType = frameType;
        this.flags = flags;
        this.backgroundTexture = backgroundTexture;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public PacketAdvancementDisplay(Component title, Component description, ItemStack slot, PacketFrameType frameType, int flags, float xCord, float yCord) {
        this(title, description, slot, frameType, flags, null, xCord, yCord);
    }

    public static PacketAdvancementDisplay read(PacketWrapper<?> wrapper) {
        final Component title = wrapper.readComponent();
        final Component description = wrapper.readComponent();
        final ItemStack slot = wrapper.readItemStack();
        final PacketFrameType frameType = PacketFrameType.fromId(wrapper.readVarInt());
        final int flags = wrapper.readInt();
        final ResourceLocation backgroundTexture = hasBackgroundTexture(flags) ? wrapper.readIdentifier() : null;
        final float xCord = wrapper.readFloat();
        final float yCord = wrapper.readFloat();
        return new PacketAdvancementDisplay(title, description, slot, frameType, flags, backgroundTexture, xCord, yCord);
    }

    public static void write(PacketWrapper<?> wrapper, PacketAdvancementDisplay display) {
        wrapper.writeComponent(display.title);
        wrapper.writeComponent(display.description);
        wrapper.writeItemStack(display.slot);
        wrapper.writeVarInt(display.frameType.id());
        wrapper.writeInt(display.flags);
        if (hasBackgroundTexture(display.flags)) {
            wrapper.writeIdentifier(Objects.requireNonNull(display.backgroundTexture, "Background texture is required when flags indicate it has a background texture"));
        }
        wrapper.writeFloat(display.xCord);
        wrapper.writeFloat(display.yCord);
    }

    public PacketAdvancementDisplay copy() {
        return new PacketAdvancementDisplay(
                this.title,
                this.description,
                this.slot.copy(),
                this.frameType,
                this.flags,
                this.backgroundTexture,
                this.xCord,
                this.yCord
        );
    }

    public void setHasBackgroundTexture(boolean hasBackgroundTexture) {
        if (hasBackgroundTexture) {
            this.flags |= Flags.HAS_BACKGROUND_TEXTURE;
        } else {
            this.flags &= ~Flags.HAS_BACKGROUND_TEXTURE;
        }
    }

    public void setShowToast(boolean showToast) {
        if (showToast) {
            this.flags |= Flags.SHOW_TOAST;
        } else {
            this.flags &= ~Flags.SHOW_TOAST;
        }
    }

    public void setHidden(boolean hidden) {
        if (hidden) {
            this.flags |= Flags.HIDDEN;
        } else {
            this.flags &= ~Flags.HIDDEN;
        }
    }

    public boolean hasBackgroundTexture() {
        return hasBackgroundTexture(this.flags);
    }

    public boolean isShowToast() {
        return isShowToast(this.flags);
    }

    public boolean isHidden() {
        return isHidden(this.flags);
    }

    public static boolean hasBackgroundTexture(int flags) {
        return (flags & 0x01) != 0;
    }

    public static boolean isShowToast(int flags) {
        return (flags & 0x02) != 0;
    }

    public static boolean isHidden(int flags) {
        return (flags & 0x04) != 0;
    }

    public Component title() {
        return this.title;
    }

    public Component description() {
        return this.description;
    }

    public ItemStack slot() {
        return this.slot;
    }

    public PacketFrameType frameType() {
        return this.frameType;
    }

    public int flags() {
        return this.flags;
    }

    public @Nullable  ResourceLocation backgroundTexture() {
        return this.backgroundTexture;
    }

    public float xCord() {
        return this.xCord;
    }

    public float yCord() {
        return this.yCord;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public void setDescription(Component description) {
        this.description = description;
    }

    public void setSlot(ItemStack slot) {
        this.slot = slot;
    }

    public void setFrameType(PacketFrameType frameType) {
        this.frameType = frameType;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setBackgroundTexture(@Nullable  ResourceLocation backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setXCord(int xCord) {
        this.xCord = xCord;
    }

    public void setYCord(int yCord) {
        this.yCord = yCord;
    }

    public static class Flags {

        public static final int HAS_BACKGROUND_TEXTURE = 0x01;
        public static final int SHOW_TOAST = 0x02;
        public static final int HIDDEN = 0x04;

        private Flags() {
        }

        public static boolean hasBackgroundTexture(int flags) {
            return (flags & HAS_BACKGROUND_TEXTURE) != 0;
        }

        public static boolean isShowToast(int flags) {
            return (flags & SHOW_TOAST) != 0;
        }

        public static boolean isHidden(int flags) {
            return (flags & HIDDEN) != 0;
        }

        public static int createFlags(boolean hasBackgroundTexture, boolean showToast, boolean hidden) {
            int flags = 0;
            if (hasBackgroundTexture) {
                flags |= HAS_BACKGROUND_TEXTURE;
            }
            if (showToast) {
                flags |= SHOW_TOAST;
            }
            if (hidden) {
                flags |= HIDDEN;
            }
            return flags;
        }

    }

    @Override
    public String toString() {
        return "PacketAdvancementDisplay{" +
                "title=" + this.title +
                ", description=" + this.description +
                ", slot=" + this.slot +
                ", frameType=" + this.frameType +
                ", hasBackgroundTexture=" + hasBackgroundTexture(this.flags) +
                ", showToast=" + isShowToast(this.flags) +
                ", hidden=" + isHidden(this.flags) +
                ", backgroundTexture=" + this.backgroundTexture +
                ", xCord=" + this.xCord +
                ", yCord=" + this.yCord +
                '}';
    }
}
