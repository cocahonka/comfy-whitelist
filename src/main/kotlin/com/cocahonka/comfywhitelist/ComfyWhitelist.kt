package com.cocahonka.comfywhitelist

import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class ComfyWhitelist : JavaPlugin() {
    private lateinit var generalConfig: GeneralConfig
    private lateinit var messageConfig: MessageConfig

    override fun onEnable() {
        logger.info("hello world!")

        generalConfig = GeneralConfig(this)
        generalConfig.loadConfig()

        messageConfig = MessageConfig(this, GeneralConfig.locale)
        messageConfig.loadConfig()

    }

    override fun onDisable() {}

}