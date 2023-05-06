package com.cocahonka.comfywhitelist

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class ComfyWhitelist : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        logger.info("hello world!")
    }

    override fun onDisable() {}

}