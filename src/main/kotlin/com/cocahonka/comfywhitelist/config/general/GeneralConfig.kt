package com.cocahonka.comfywhitelist.config.general

import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.base.Locale
import kotlin.properties.Delegates
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * A class responsible for managing the general configuration of the plugin,
 * including enabling/disabling the plugin and setting the locale.
 *
 * @param plugin The plugin instance.
 */
class GeneralConfig(private val plugin: Plugin) : ConfigManager() {
    companion object {
        var whitelistEnabled: Boolean by Delegates.notNull()
            private set
        lateinit var locale: Locale private set

        private const val filePath = "config.yml"
        private const val enabledKey = "enabled"
        private const val localeKey = "locale"
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
    }

    fun enableWhitelist() {
        whitelistEnabled = true
        config.set(enabledKey, true)
        config.save(configFile)
    }

    fun disableWhitelist() {
        whitelistEnabled = false
        config.set(enabledKey, false)
        config.save(configFile)
    }

}
