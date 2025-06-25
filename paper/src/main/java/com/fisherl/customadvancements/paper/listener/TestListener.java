package com.fisherl.customadvancements.paper.listener;

import com.fisherl.customadvancements.advancement.data.CustomAdvancementTabData;
import com.fisherl.customadvancements.advancement.display.CustomAdvancementDisplay;
import com.fisherl.customadvancements.advancement.tab.CustomAdvancementTab;
import com.fisherl.customadvancements.paper.CustomAdvancements;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final CustomAdvancementTabData tabData = create();
        try {
            Bukkit.getScheduler().runTaskLater(CustomAdvancements.getPlugin(CustomAdvancements.class), () -> {
                tabData.sendTo(event.getPlayer(), true, false);
                event.getPlayer().sendMessage("Advancement sent!");
            }, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabData.getAdvancement(Key.key("customadvancements", "test"))
                .markCompleted();
        try {
            Bukkit.getScheduler().runTaskLater(CustomAdvancements.getPlugin(CustomAdvancements.class), () -> {
                tabData.sendTo(event.getPlayer(), true, false);
                event.getPlayer().sendMessage("Advancement sent!");
            }, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CustomAdvancementTabData create() {
        final CustomAdvancementTab tab = CustomAdvancementTab.create(
                Key.key("customadvancements", "test"),
                new CustomAdvancementDisplay(
                        Component.text("First Advancement"),
                        Component.text("First advancement description"),
                        ItemStack.builder().type(ItemTypes.DIAMOND).build(),
                        CustomAdvancementDisplay.Frame.CHALLENGE,
                        new CustomAdvancementDisplay.Flags(true, true, false),
                        Key.key("minecraft:block/enchanting_table_top"),
                        0f,
                        0f
                ),
                false,
                builder -> {
                    builder.advancement(
                            Key.key("customadvancements:test2"),
                            new CustomAdvancementDisplay(
                                    Component.text("Second Advancement"),
                                    Component.text("Second advancement description"),
                                    ItemStack.builder().type(ItemTypes.EMERALD).build(),
                                    CustomAdvancementDisplay.Frame.GOAL,
                                    new CustomAdvancementDisplay.Flags(false, true, false),
                                    null,
                                    0.5f,
                                    0.5f
                            ),
                            false
                    );
                }
        );
        return tab.createData();
    }


}
