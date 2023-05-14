@file:Suppress("removal")

package com.cocahonka.comfywhitelist

import com.cocahonka.comfywhitelist.commands.CommandHandler
import com.cocahonka.comfywhitelist.commands.CommandTabCompleter
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.listeners.PlayerPreLoginEvent
import com.cocahonka.comfywhitelist.storage.YamlStorage
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

@Suppress("unused")
class ComfyWhitelist : JavaPlugin {
    companion object {
        const val DISPLAY_NAME = "ComfyWhitelist"
    }

    constructor() : super() {
        isUnitTest = false
    }

    /**
     * Constructor ONLY for unit tests
     */
    @Suppress("removal", "DEPRECATION")
    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File,
    ) : super(loader, description, dataFolder, file) {
        isUnitTest = true
    }

    private val isUnitTest: Boolean
    private lateinit var generalConfig: GeneralConfig
    private lateinit var messageConfig: MessageConfig
    private lateinit var storage: YamlStorage

    override fun onEnable() {
        if (isUnitTest) onUnitTest()
        else onPluginEnable()
    }

    override fun onDisable() {}

    private fun onUnitTest() {
        loadConfigs()
    }

    private fun onPluginEnable() {
        loadConfigs()
        loadStorage()
        registerEvents()
        registerCommands()
    }

    private fun loadConfigs() {
        generalConfig = GeneralConfig(this).apply { loadConfig() }

        messageConfig = MessageConfig(this, GeneralConfig.locale).apply { loadConfig() }
    }

    fun reloadConfigs() {
        generalConfig.loadConfig()
        messageConfig = MessageConfig(this, GeneralConfig.locale).apply { loadConfig() }
        storage.load()
    }

    private fun loadStorage() {
        storage = YamlStorage(dataFolder).apply { load() }
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerPreLoginEvent(storage), this)
    }

    private fun registerCommands() {
        val commandHandler = CommandHandler(storage, generalConfig, this)
        getCommand(CommandHandler.identifier)!!.setExecutor(commandHandler)
        getCommand(CommandHandler.identifier)!!.tabCompleter = CommandTabCompleter(storage, commandHandler.subCommands)

    }
}