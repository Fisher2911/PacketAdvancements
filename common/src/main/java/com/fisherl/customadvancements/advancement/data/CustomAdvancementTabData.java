package com.fisherl.customadvancements.advancement.data;

import com.fisherl.customadvancements.advancement.tab.CustomAdvancementTab;
import com.fisherl.customadvancements.packet.PacketAdvancement;
import com.fisherl.customadvancements.packet.PacketAdvancementProgress;
import com.fisherl.customadvancements.packet.wrapper.WrapperPlayServerUpdateAdvancements;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomAdvancementTabData {

    private final CustomAdvancementTab tab;
    private final Map<Key, CustomAdvancementData> advancements;
    private Set<Key> alreadySent = new HashSet<>();

    public CustomAdvancementTabData(CustomAdvancementTab tab, Map<Key, CustomAdvancementData> advancements) {
        this.tab = tab;
        this.advancements = Map.copyOf(advancements);
    }

    public CustomAdvancementTab tab() {
        return this.tab;
    }

    public Map<Key, CustomAdvancementData> advancements() {
        return this.advancements;
    }

    public CustomAdvancementData getAdvancement(Key identifier) {
        return this.advancements.get(identifier);
    }

    public void sendTo(Object player, Predicate<CustomAdvancementData> advancementPredicate, boolean showAdvancements, boolean clear) {
        final Map<ResourceLocation, PacketAdvancement> packetAdvancements = new HashMap<>();
        for (CustomAdvancementData advancement : this.advancements.values()) {
            if (!advancementPredicate.test(advancement) || this.alreadySent.contains(advancement.advancement().id())) {
                continue;
            }
            final ResourceLocation id = new ResourceLocation(
                    advancement.advancement().id().namespace(),
                    advancement.advancement().id().value()
            );
            this.alreadySent.add(advancement.advancement().id());
            packetAdvancements.put(id, advancement.toPacket(advancement.completionDate() != null && !advancement.alreadyShowedToast()));
        }
        final List<ResourceLocation> removedAdvancements = this.advancements.values().stream()
                .filter(CustomAdvancementData::remove)
                .map(data -> {
                    final Key key = data.advancement().id();
                    return new ResourceLocation(key.namespace(), key.value());
                })
                .toList();
        final Map<ResourceLocation, PacketAdvancementProgress> progress = this.advancements.values()
                .stream()
                .filter(advancementPredicate)
                .map(data -> {
                    final Key key = data.advancement().id();
                    final ResourceLocation id = new ResourceLocation(key.namespace(), key.value());
                    return Map.entry(id, new PacketAdvancementProgress(Map.of(
                            id.toString(),
                            new PacketAdvancementProgress.AchieveDate(data.completionDate())
                    )));
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        final WrapperPlayServerUpdateAdvancements wrapper = new WrapperPlayServerUpdateAdvancements(
                clear,
                packetAdvancements,
                removedAdvancements,
                progress,
                showAdvancements
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, wrapper);
        this.advancements.values()
                .stream()
                .filter(advancementPredicate)
                .forEach(data -> data.setAlreadyShowedToast(data.completionDate() != null));
    }

    public void sendTo(Object player, boolean showAdvancements, boolean clear) {
        this.sendTo(player, data -> true, showAdvancements, clear);
    }

}
