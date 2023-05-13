package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getMessageWithDefault
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * A class responsible for managing the messages configuration of the plugin,
 * including loading and updating message properties based on the selected locale.
 *
 * @param plugin The plugin instance.
 * @param locale The locale to use for loading messages.
 */
class MessageConfig(private val plugin: Plugin, private val locale: Locale) : ConfigManager() {
    companion object {
        lateinit var notWhitelisted: String private set
        lateinit var playerAdded: String private set
        lateinit var playerRemoved: String private set
        lateinit var whitelistCleared: String private set
        lateinit var whitelistEnabled: String private set
        lateinit var whitelistDisabled: String private set
        lateinit var pluginReloaded: String private set
        lateinit var noPermission: String private set
        lateinit var invalidUsage: String private set
        lateinit var whitelistedPlayersList: String private set
        lateinit var emptyWhitelistedPlayersList: String private set
        lateinit var unknownSubcommand: String private set
        lateinit var invalidPlayerName: String private set
        lateinit var nonExistentPlayerName : String private set
    }

    override fun createConfig() {
        for (locale in Locale.values()) {
            val localeFile = File(plugin.dataFolder, locale.filePath)
            val localeDirectory = localeFile.parentFile

            if (!localeDirectory.exists()) {
                localeDirectory.mkdirs()
            }

            if (!localeFile.exists()) {
                plugin.saveResource(locale.filePath, false)
            }
        }
        configFile = File(plugin.dataFolder, locale.filePath)
    }

    override fun updateProperties() {
        notWhitelisted = config.getMessageWithDefault(Message.NotWhitelisted, locale)
        playerAdded = config.getMessageWithDefault(Message.PlayerAdded, locale)
        playerRemoved = config.getMessageWithDefault(Message.PlayerRemoved, locale)
        whitelistCleared = config.getMessageWithDefault(Message.WhitelistCleared, locale)
        whitelistEnabled = config.getMessageWithDefault(Message.WhitelistEnabled, locale)
        whitelistDisabled = config.getMessageWithDefault(Message.WhitelistDisabled, locale)
        pluginReloaded = config.getMessageWithDefault(Message.PluginReloaded, locale)
        noPermission = config.getMessageWithDefault(Message.NoPermission, locale)
        invalidUsage = config.getMessageWithDefault(Message.InvalidUsage, locale)
        whitelistedPlayersList = config.getMessageWithDefault(Message.WhitelistedPlayersList, locale)
        emptyWhitelistedPlayersList = config.getMessageWithDefault(Message.EmptyWhitelistedPlayersList, locale)
        unknownSubcommand = config.getMessageWithDefault(Message.UnknownSubcommand, locale)
        invalidPlayerName = config.getMessageWithDefault(Message.InvalidPlayerName, locale)
        nonExistentPlayerName = config.getMessageWithDefault(Message.NonExistentPlayerName, locale)
    }

}
