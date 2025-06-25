package com.fisherl.customadvancements.advancement;

import com.fisherl.customadvancements.advancement.data.CustomAdvancementData;
import com.fisherl.customadvancements.advancement.display.CustomAdvancementDisplay;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface CustomAdvancement {

    static CustomAdvancement root(Key identifier, CustomAdvancementDisplay display, boolean sendTelemetry, List<CustomAdvancement> children) {
        return new RootCustomAdvancement(identifier, display, sendTelemetry, children);
    }

    static CustomAdvancement child(CustomAdvancement parent, Key identifier, CustomAdvancementDisplay display, boolean sendTelemetry, List<CustomAdvancement> children) {
        return new ChildCustomAdvancement(parent.id(), identifier, display, sendTelemetry, children);
    }

    @Nullable Key parentId();

    Key id();

    CustomAdvancementDisplay display();

    boolean sendTelemetry();

    @Unmodifiable
    List<CustomAdvancement> children();

    CustomAdvancementData toData(@Nullable Long completionDate, boolean alreadyShowedToast);

    CustomAdvancementData createInitialData();

    static Builder builder(Key identifier, CustomAdvancementDisplay display, boolean sendTelemetry) {
        return new Builder(null, identifier, display, sendTelemetry);
    }

    class Builder {

        private final @Nullable Key parentId;
        private final Key id;
        private final CustomAdvancementDisplay display;
        private final boolean sendTelemetry;
        private final List<CustomAdvancement> children = new ArrayList<>();

        private Builder(@Nullable Key parentId, Key id, CustomAdvancementDisplay display, boolean sendTelemetry) {
            this.parentId = parentId;
            this.id = id;
            this.display = display;
            this.sendTelemetry = sendTelemetry;
        }

        public Builder advancement(Key id, CustomAdvancementDisplay display, boolean sendTelemetry, @Nullable Consumer<Builder> childrenConsumer) {
            final Builder newBuilder = new Builder(this.id, id, display, sendTelemetry);
            if (childrenConsumer != null) {
                childrenConsumer.accept(newBuilder);
            }
            this.children.add(newBuilder.build());
            return this;
        }

        public Builder advancement(Key id, CustomAdvancementDisplay display, boolean sendTelemetry) {
            return this.advancement(id, display, sendTelemetry, null);
        }

        public CustomAdvancement build() {
            if (this.parentId == null) {
                return new RootCustomAdvancement(this.id, this.display, this.sendTelemetry, this.children);
            } else {
                return new ChildCustomAdvancement(this.parentId, this.id, this.display, this.sendTelemetry, this.children);
            }
        }

    }
}
