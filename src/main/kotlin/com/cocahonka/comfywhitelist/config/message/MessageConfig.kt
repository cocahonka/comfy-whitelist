package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getFormattedMessage
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
        noPermission = config.getFormattedMessage(Message.General.NoPermission, locale)
        invalidUsage = config.getFormattedMessage(Message.General.InvalidUsage, locale)
        unknownSubcommand = config.getFormattedMessage(Message.General.UnknownSubcommand, locale)
        invalidPlayerName = config.getFormattedMessage(Message.General.InvalidPlayerName, locale)
        pluginReloaded = config.getFormattedMessage(Message.General.PluginReloaded, locale)

        // Whitelist status messages
        whitelistEnabled = config.getFormattedMessage(Message.WhitelistStatus.WhitelistEnabled, locale)
        whitelistDisabled = config.getFormattedMessage(Message.WhitelistStatus.WhitelistDisabled, locale)
        whitelistAlreadyEnabled = config.getFormattedMessage(Message.WhitelistStatus.WhitelistAlreadyEnabled, locale)
        whitelistAlreadyDisabled = config.getFormattedMessage(Message.WhitelistStatus.WhitelistAlreadyDisabled, locale)

        // Player management messages
        notWhitelisted = config.getFormattedMessage(Message.PlayerManagement.NotWhitelisted, locale)
        playerAdded = config.getFormattedMessage(Message.PlayerManagement.PlayerAdded, locale)
        playerRemoved = config.getFormattedMessage(Message.PlayerManagement.PlayerRemoved, locale)
        nonExistentPlayerName = config.getFormattedMessage(Message.PlayerManagement.NonExistentPlayerName, locale)

        // Whitelist display messages
        whitelistedPlayersList = config.getFormattedMessage(Message.WhitelistDisplay.WhitelistedPlayersList, locale)
        emptyWhitelistedPlayersList = config.getFormattedMessage(Message.WhitelistDisplay.EmptyWhitelistedPlayersList, locale)
        whitelistCleared = config.getFormattedMessage(Message.WhitelistDisplay.WhitelistCleared, locale)
    }

}
