package com.fisherl.customadvancements.paper;

import com.fisherl.customadvancements.paper.listener.TestListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomAdvancements extends JavaPlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new TestListener(), this);
    }

}
