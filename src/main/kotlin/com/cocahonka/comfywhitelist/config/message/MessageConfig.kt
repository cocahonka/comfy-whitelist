package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getFormattedWithDefault
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
        noPermission = config.getFormattedWithDefault(Message.NoPermission, locale)
        invalidUsage = config.getFormattedWithDefault(Message.InvalidUsage, locale)
        unknownSubcommand = config.getFormattedWithDefault(Message.UnknownSubcommand, locale)
        invalidPlayerName = config.getFormattedWithDefault(Message.InvalidPlayerName, locale)
        pluginReloaded = config.getFormattedWithDefault(Message.PluginReloaded, locale)

        // Whitelist status messages
        whitelistEnabled = config.getFormattedWithDefault(Message.WhitelistEnabled, locale)
        whitelistDisabled = config.getFormattedWithDefault(Message.WhitelistDisabled, locale)
        whitelistAlreadyEnabled = config.getFormattedWithDefault(Message.WhitelistAlreadyEnabled, locale)
        whitelistAlreadyDisabled = config.getFormattedWithDefault(Message.WhitelistAlreadyDisabled, locale)

        // Player management messages
        notWhitelisted = config.getFormattedWithDefault(Message.NotWhitelisted, locale)
        playerAdded = config.getFormattedWithDefault(Message.PlayerAdded, locale)
        playerRemoved = config.getFormattedWithDefault(Message.PlayerRemoved, locale)
        nonExistentPlayerName = config.getFormattedWithDefault(Message.NonExistentPlayerName, locale)

        // Whitelist display messages
        whitelistedPlayersList = config.getFormattedWithDefault(Message.WhitelistedPlayersList, locale)
        emptyWhitelistedPlayersList = config.getFormattedWithDefault(Message.EmptyWhitelistedPlayersList, locale)
        whitelistCleared = config.getFormattedWithDefault(Message.WhitelistCleared, locale)
    }

}
