package com.fisherl.customadvancements.packet;

import java.util.Arrays;
import java.util.Comparator;

public enum PacketFrameType {

    TASK(0),
    CHALLENGE(1),
    GOAL(2);

    private static final PacketFrameType[] VALUES = Arrays.stream(values())
            .sorted(Comparator.comparingInt(a -> a.id))
            .toArray(PacketFrameType[]::new);

    private final int id;

    PacketFrameType(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public static PacketFrameType fromId(int id) {
        return VALUES[id];
    }

}
