package com.fisherl.customadvancements.advancement.tab;

import com.fisherl.customadvancements.advancement.CustomAdvancement;
import com.fisherl.customadvancements.advancement.data.CustomAdvancementTabData;
import com.fisherl.customadvancements.advancement.display.CustomAdvancementDisplay;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class CustomAdvancementTab {

    private final CustomAdvancement rootAdvancement;
    private final @Unmodifiable Map<Key, CustomAdvancement> advancements;

    public CustomAdvancementTab(CustomAdvancement rootAdvancement, Map<Key, CustomAdvancement> advancements) {
        this.rootAdvancement = rootAdvancement;
        this.advancements = Map.copyOf(advancements);
    }

    public CustomAdvancementTabData createData() {
        return new CustomAdvancementTabData(
                this,
                this.advancements.values()
                        .stream()
                        .map(CustomAdvancement::createInitialData)
                        .collect(Collectors.toMap(
                                entry -> entry.advancement().id(),
                                entry -> entry
                        ))
        );
    }

    public CustomAdvancement rootAdvancement() {
        return this.rootAdvancement;
    }

    public @Unmodifiable Map<Key, CustomAdvancement> advancements() {
        return this.advancements;
    }

    public static CustomAdvancementTab create(
            Key id,
            CustomAdvancementDisplay display,
            boolean sendTelemetry
    ) {
        return create(id, display, sendTelemetry, null);
    }

    public static CustomAdvancementTab create(
            Key id,
            CustomAdvancementDisplay display,
            boolean sendTelemetry,
            @Nullable Consumer<CustomAdvancement.Builder> childrenConsumer
    ) {
        final CustomAdvancement.Builder builder = CustomAdvancement.builder(id, display, sendTelemetry);
        if (childrenConsumer != null) {
            childrenConsumer.accept(builder);
        }
        final CustomAdvancement rootAdvancement = builder.build();
        final Map<Key, CustomAdvancement> advancements = new HashMap<>();
        advancements.put(id, rootAdvancement);
        populateChildren(advancements, rootAdvancement);
        return new CustomAdvancementTab(
                rootAdvancement,
                advancements
        );
    }

    private static void populateChildren(Map<Key, CustomAdvancement> advancements, CustomAdvancement advancement) {
        advancements.put(advancement.id(), advancement);
        for (CustomAdvancement child : advancement.children()) {
            populateChildren(advancements, child);
        }
    }

}
