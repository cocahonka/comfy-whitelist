package com.cocahonka.comfywhitelist.config.base

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * An abstract class representing a configuration manager responsible for loading and updating
 * configuration properties from a YAML configuration file.
 */
abstract class ConfigManager {
    protected lateinit var configFile: File
    protected lateinit var config: FileConfiguration

    /**
     * Loads the configuration file and updates the properties.
     */
    fun loadConfig() {
        createConfig()
        config = YamlConfiguration.loadConfiguration(configFile)
        updateProperties()
    }

    /**
     * Creates the configuration file if it does not exist.
     */
    protected abstract fun createConfig()

    /**
     * Updates the properties with values from the loaded configuration file.
     */
    protected abstract fun updateProperties()

}