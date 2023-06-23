package com.cocahonka.comfywhitelist.config.general

import com.cocahonka.comfywhitelist.api.WhitelistManager
import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import org.bukkit.plugin.Plugin
import java.io.File
import kotlin.properties.Delegates

/**
 * A class responsible for managing the general configuration of the plugin,
 * including enabling/disabling the plugin and setting the locale.
 *
 * @param plugin The plugin instance.
 */
class GeneralConfig(private val plugin: Plugin) : WhitelistManager, ConfigManager() {
    companion object {
        var whitelistEnabled: Boolean by Delegates.notNull()
            private set
        lateinit var locale: Locale
            private set
        var clearCommandEnabled: Boolean by Delegates.notNull()
            private set

        private const val filePath = "config.yml"
        private const val enabledKey = "enabled"
        private const val localeKey = "locale"
        private const val clearCommandKey = "clear-command"
    }

    override fun createConfig() {
        configFile = File(plugin.dataFolder, filePath)
        if (!configFile.exists()) {
            plugin.saveResource(filePath, false)
        }
    }

    override fun updateProperties() {
        whitelistEnabled = config.getBoolean(enabledKey, true)
        locale = Locale.fromString(config.getString(localeKey))
        clearCommandEnabled = config.getBoolean(clearCommandKey, false)
    }

    override fun isWhitelistEnabled(): Boolean = whitelistEnabled

    override fun enableWhitelist() {
        whitelistEnabled = true
        config.set(enabledKey, true)
        config.save(configFile)
    }

    override fun disableWhitelist() {
        whitelistEnabled = false
        config.set(enabledKey, false)
        config.save(configFile)
    }

}
