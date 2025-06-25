package com.fisherl.customadvancements.packet;

import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class PacketAdvancementProgress {

    private final Map<String, AchieveDate> progress;

    public PacketAdvancementProgress(Map<String, AchieveDate> progress) {
        this.progress = progress;
    }

    public static PacketAdvancementProgress read(PacketWrapper<?> wrapper) {
        final Map<String, AchieveDate> progress = wrapper.readMap(PacketWrapper::readString, AchieveDate::read);
        return new PacketAdvancementProgress(progress);
    }

    public static void write(PacketWrapper<?> wrapper, PacketAdvancementProgress progress) {
        wrapper.writeMap(progress.progress, PacketWrapper::writeString, AchieveDate::write);
    }

    public PacketAdvancementProgress copy() {
        return new PacketAdvancementProgress(new HashMap<>(this.progress));
    }

    public Map<String, AchieveDate> progress() {
        return this.progress;
    }

    @Override
    public String toString() {
        return "PacketAdvancementProgress{" +
                "progress=" + this.progress +
                '}';
    }

    public record AchieveDate(@Nullable Long date) {

        public static  AchieveDate read(PacketWrapper<?> wrapper) {
            return new AchieveDate(wrapper.readOptional(PacketWrapper::readLong));
        }

        public static void write(PacketWrapper<?> wrapper, AchieveDate date) {
            wrapper.writeOptional(date.date, PacketWrapper::writeLong);
        }

    }

}
