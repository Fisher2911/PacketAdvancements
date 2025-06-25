package com.fisherl.customadvancements.packet.wrapper;

import com.fisherl.customadvancements.packet.PacketAdvancement;
import com.fisherl.customadvancements.packet.PacketAdvancementProgress;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class WrapperPlayServerUpdateAdvancements extends PacketWrapper<WrapperPlayServerUpdateAdvancements> {

    private boolean clear;
    private Map<ResourceLocation, PacketAdvancement> advancements;
    private List<ResourceLocation> removedAdvancements;
    private Map<ResourceLocation, PacketAdvancementProgress> progress;
    private boolean showAdvancements;


    public WrapperPlayServerUpdateAdvancements(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerUpdateAdvancements(
            boolean clear,
            Map<ResourceLocation, PacketAdvancement> advancements,
            List<ResourceLocation> removedAdvancements,
            Map<ResourceLocation, PacketAdvancementProgress> progress,
            boolean showAdvancements
    ) {
        super(PacketType.Play.Server.UPDATE_ADVANCEMENTS);
        this.clear = clear;
        this.advancements = advancements;
        this.removedAdvancements = removedAdvancements;
        this.progress = progress;
        this.showAdvancements = showAdvancements;
    }

    @Override
    public void read() {
        this.clear = this.readBoolean();
        this.advancements = this.readMap(PacketWrapper::readIdentifier, PacketAdvancement::read);
        this.removedAdvancements = this.readList(ResourceLocation::read);
        this.progress = this.readMap(PacketWrapper::readIdentifier, PacketAdvancementProgress::read);
        this.showAdvancements = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBoolean(this.clear);
        this.writeMap(this.advancements, PacketWrapper::writeIdentifier, PacketAdvancement::write);
        this.writeList(this.removedAdvancements, ResourceLocation::write);
        this.writeMap(this.progress, PacketWrapper::writeIdentifier, PacketAdvancementProgress::write);
        this.writeBoolean(this.showAdvancements);
    }

    @Override
    public void copy(WrapperPlayServerUpdateAdvancements wrapper) {
        this.clear = wrapper.clear;
        this.advancements = wrapper.advancements.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().copy()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.removedAdvancements = new ArrayList<>(wrapper.removedAdvancements);
        this.progress = wrapper.progress.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().copy()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.showAdvancements = wrapper.showAdvancements;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("WrapperPlayServerUpdateAdvancements{");
        builder.append("clear=").append(this.clear);
        builder.append("\n, advancements=");
        builder.append(this.advancements.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue().toString())
                .collect(Collectors.joining(", ")));
        builder.append("\n, removedAdvancements=");
        builder.append(this.removedAdvancements.stream().map(ResourceLocation::toString).collect(Collectors.joining(", ")));
        builder.append("\n, progress=");
        builder.append(this.progress.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue().toString())
                .collect(Collectors.joining(", ")));
        builder.append("\n, showAdvancements=").append(this.showAdvancements);
        builder.append('}');
        return builder.toString();
    }
}
