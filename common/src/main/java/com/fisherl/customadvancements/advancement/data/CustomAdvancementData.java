package com.fisherl.customadvancements.advancement.data;

import com.fisherl.customadvancements.advancement.CustomAdvancement;
import com.fisherl.customadvancements.packet.PacketAdvancement;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomAdvancementData {

    private final CustomAdvancement advancement;
    private @Nullable Long completionDate;
    private boolean remove;
    private boolean alreadyShowedToast;

    public CustomAdvancementData(CustomAdvancement advancement, @Nullable Long completionDate, boolean alreadyShowedToast) {
        this.advancement = advancement;
        this.completionDate = completionDate;
        this.remove = false;
        this.alreadyShowedToast = alreadyShowedToast;
    }

    public PacketAdvancement toPacket(boolean showToast) {
        final Key parent = this.advancement.parentId();
        return new PacketAdvancement(
                parent != null ? new ResourceLocation(parent.namespace(), parent.value()) : null,
                this.advancement.display().toPacket(showToast),
                List.of(List.of(this.advancement.id().toString())),
                this.advancement.sendTelemetry()
        );
    }

    public boolean markCompleted() {
        if (this.completionDate != null) {
            return false;
        }
        this.completionDate = System.currentTimeMillis();
        return true;
    }

    public CustomAdvancement advancement() {
        return this.advancement;
    }

    public @Nullable Long completionDate() {
        return this.completionDate;
    }

    public boolean alreadyShowedToast() {
        return this.alreadyShowedToast;
    }

    public void setCompletionDate(@Nullable Long completionDate) {
        this.completionDate = completionDate;
    }

    public boolean remove() {
        return this.remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void setAlreadyShowedToast(boolean alreadyShowedToast) {
        this.alreadyShowedToast = alreadyShowedToast;
    }

}
