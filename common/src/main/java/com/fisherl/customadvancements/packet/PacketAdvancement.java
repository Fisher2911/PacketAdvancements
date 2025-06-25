package com.fisherl.customadvancements.packet;

import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PacketAdvancement {

    // prefixed optional
    private @Nullable ResourceLocation parentId;
    // prefixed optional
    private @Nullable PacketAdvancementDisplay display;
    // prefixed array
    private List<List<String>> nestedRequirements;
    private boolean sendTelemetry;

    public PacketAdvancement(@Nullable ResourceLocation parentId, @Nullable PacketAdvancementDisplay display, List<List<String>> nestedRequirements, boolean sendTelemetry) {
        this.parentId = parentId;
        this.display = display;
        this.nestedRequirements = nestedRequirements;
        this.sendTelemetry = sendTelemetry;
    }

    public static PacketAdvancement read(PacketWrapper<?> wrapper) {
        final ResourceLocation parentId = wrapper.readOptional(PacketWrapper::readIdentifier);
        final PacketAdvancementDisplay display = wrapper.readOptional(PacketAdvancementDisplay::read);
        final List<List<String>> nestedRequirements = wrapper.readList(w -> w.readList(PacketWrapper::readString));
        final boolean sendTelemetry = wrapper.readBoolean();
        return new PacketAdvancement(parentId, display, nestedRequirements, sendTelemetry);
    }

    public static void write(PacketWrapper<?> wrapper, PacketAdvancement advancement) {
        wrapper.writeOptional(advancement.parentId, PacketWrapper::writeIdentifier);
        wrapper.writeOptional(advancement.display, PacketAdvancementDisplay::write);
        wrapper.writeVarInt(advancement.nestedRequirements.size());
        for (List<String> list : advancement.nestedRequirements) {
            wrapper.writeList(list, PacketWrapper::writeString);
        }
        wrapper.writeBoolean(advancement.sendTelemetry);
    }

    public PacketAdvancement copy() {
        return new PacketAdvancement(
                this.parentId,
                this.display == null ? null : this.display.copy(),
                List.copyOf(this.nestedRequirements),
                this.sendTelemetry
        );
    }

    public @Nullable ResourceLocation parentId() {
        return this.parentId;
    }

    public @Nullable PacketAdvancementDisplay display() {
        return this.display;
    }

    public List<List<String>> nextRequirements() {
        return this.nestedRequirements;
    }

    public boolean sendTelemetry() {
        return this.sendTelemetry;
    }

    public void setParentId(@Nullable ResourceLocation parentId) {
        this.parentId = parentId;
    }

    public void setDisplay(@Nullable PacketAdvancementDisplay display) {
        this.display = display;
    }

    public void setNestedRequirements(List<List<String>> nestedRequirements) {
        this.nestedRequirements = nestedRequirements;
    }

    public void setSendTelemetry(boolean sendTelemetry) {
        this.sendTelemetry = sendTelemetry;
    }

    @Override
    public String toString() {
        return "PacketAdvancement{" +
                "parentId=" + this.parentId +
                ", display=" + this.display +
                ", nestedRequirements=" + this.nestedRequirements +
                ", sendTelemetry=" + this.sendTelemetry +
                '}';
    }
}
