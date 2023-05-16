package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getMessageWithDefault
import net.kyori.adventure.text.Component
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
        // General messages
        lateinit var noPermission: Component internal set
        lateinit var invalidUsage: Component private set
        lateinit var unknownSubcommand: Component private set
        lateinit var invalidPlayerName: Component private set
        lateinit var pluginReloaded: Component private set

        // Whitelist status messages
        lateinit var whitelistEnabled: Component private set
        lateinit var whitelistDisabled: Component private set
        lateinit var whitelistAlreadyEnabled: Component private set
        lateinit var whitelistAlreadyDisabled: Component private set

        // Player management messages
        lateinit var notWhitelisted: Component private set
        lateinit var playerAdded: Component private set
        lateinit var playerRemoved: Component private set
        lateinit var nonExistentPlayerName: Component private set

        // Whitelist display messages
        lateinit var whitelistedPlayersList: Component private set
        lateinit var emptyWhitelistedPlayersList: Component private set
        lateinit var whitelistCleared: Component private set

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
        // General messages
        noPermission = config.getMessageWithDefault(Message.General.NoPermission, locale)
        invalidUsage = config.getMessageWithDefault(Message.General.InvalidUsage, locale)
        unknownSubcommand = config.getMessageWithDefault(Message.General.UnknownSubcommand, locale)
        invalidPlayerName = config.getMessageWithDefault(Message.General.InvalidPlayerName, locale)
        pluginReloaded = config.getMessageWithDefault(Message.General.PluginReloaded, locale)

        // Whitelist status messages
        whitelistEnabled = config.getMessageWithDefault(Message.WhitelistStatus.WhitelistEnabled, locale)
        whitelistDisabled = config.getMessageWithDefault(Message.WhitelistStatus.WhitelistDisabled, locale)
        whitelistAlreadyEnabled = config.getMessageWithDefault(Message.WhitelistStatus.WhitelistAlreadyEnabled, locale)
        whitelistAlreadyDisabled = config.getMessageWithDefault(Message.WhitelistStatus.WhitelistAlreadyDisabled, locale)

        // Player management messages
        notWhitelisted = config.getMessageWithDefault(Message.PlayerManagement.NotWhitelisted, locale)
        playerAdded = config.getMessageWithDefault(Message.PlayerManagement.PlayerAdded, locale)
        playerRemoved = config.getMessageWithDefault(Message.PlayerManagement.PlayerRemoved, locale)
        nonExistentPlayerName = config.getMessageWithDefault(Message.PlayerManagement.NonExistentPlayerName, locale)

        // Whitelist display messages
        whitelistedPlayersList = config.getMessageWithDefault(Message.WhitelistDisplay.WhitelistedPlayersList, locale)
        emptyWhitelistedPlayersList = config.getMessageWithDefault(Message.WhitelistDisplay.EmptyWhitelistedPlayersList, locale)
        whitelistCleared = config.getMessageWithDefault(Message.WhitelistDisplay.WhitelistCleared, locale)
    }

}
