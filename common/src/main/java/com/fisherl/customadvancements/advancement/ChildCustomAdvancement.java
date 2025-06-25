package com.fisherl.customadvancements.advancement;

import com.fisherl.customadvancements.advancement.data.CustomAdvancementData;
import com.fisherl.customadvancements.advancement.display.CustomAdvancementDisplay;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public final class ChildCustomAdvancement implements CustomAdvancement {

    private final Key parentId;
    private final Key id;
    private final CustomAdvancementDisplay display;
    private final boolean sendTelemetry;
    private final @Unmodifiable List<CustomAdvancement> children;

    ChildCustomAdvancement(Key parentId, Key id, CustomAdvancementDisplay display, boolean sendTelemetry, List<CustomAdvancement> children) {
        this.parentId = parentId;
        this.id = id;
        this.display = display;
        this.sendTelemetry = sendTelemetry;
        this.children = List.copyOf(children);
    }

    @Override
    public CustomAdvancementData toData(@Nullable Long completionDate, boolean alreadyShowedToast) {
        return new CustomAdvancementData(this, completionDate, alreadyShowedToast);
    }

    @Override
    public CustomAdvancementData createInitialData() {
        return new CustomAdvancementData(this, null, false);
    }

    @Override
    public @Nullable Key parentId() {
        return this.parentId;
    }

    @Override
    public Key id() {
        return this.id;
    }

    @Override
    public CustomAdvancementDisplay display() {
        return this.display;
    }

    @Override
    public boolean sendTelemetry() {
        return this.sendTelemetry;
    }

    @Override
    public @Unmodifiable List<CustomAdvancement> children() {
        return this.children;
    }

}
