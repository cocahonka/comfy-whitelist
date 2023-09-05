package com.cocahonka.comfywhitelist.storage

import com.cocahonka.comfywhitelist.api.Storage
import org.bukkit.Bukkit.getLogger
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * YamlStorage is an implementation of the Storage interface that uses a YAML file for storing
 * the whitelist data. It provides methods for adding, removing, and checking players in the whitelist,
 * as well as loading and saving the data to the YAML file.
 *
 * @param dataFolder The folder where the YAML storage file should be located. It must be a directory.
 * @throws IllegalArgumentException If the provided dataFolder is not a directory.
 */
class YamlStorage(dataFolder: File) : Storage {
    private val whitelistedPlayers: MutableSet<String> = mutableSetOf()
    private val storageFile: File
    private val config: YamlConfiguration

    init {
        require(dataFolder.isDirectory) { "provided dataFolder ($dataFolder) is not directory!" }
        storageFile = File(dataFolder, FILE_NAME)
        if(!storageFile.exists()) {
            createFile()
        }
        config = YamlConfiguration.loadConfiguration(storageFile)
    }

    companion object {
        private const val WHITELISTED_PLAYERS_KEY = "players"
        private const val FILE_NAME = "whitelist.yml"
    }

    override fun addPlayer(username: String): Boolean {
        return whitelistedPlayers.add(username).also { save() }
    }


    override fun removePlayer(username: String): Boolean {
        return whitelistedPlayers.remove(username).also { save() }
    }

    override fun clear(): Boolean {
        whitelistedPlayers.clear()
        return save()
    }

    override fun isPlayerWhitelisted(username: String): Boolean {
        return whitelistedPlayers.contains(username)
    }

    override fun getAllWhitelistedPlayers(): Set<String> {
        return whitelistedPlayers.toSet()
    }

    override fun load(): Boolean {
        return try {
            if (!storageFile.exists()) {
                storageFile.parentFile.mkdirs()
                storageFile.createNewFile()
            }
            val tempConfig = YamlConfiguration.loadConfiguration(storageFile) // Reload the config from the storage file
            whitelistedPlayers.clear()
            whitelistedPlayers.addAll(tempConfig.getStringList(WHITELISTED_PLAYERS_KEY))
            true
        } catch (e: Exception) {
            getLogger().warning(e.stackTraceToString())
            false
        }
    }

    override fun save(): Boolean {
        return try {
            config.set(WHITELISTED_PLAYERS_KEY, whitelistedPlayers.toList())
            config.save(storageFile)
            true
        } catch (e: Exception) {
            getLogger().warning(e.stackTraceToString())
            false
        }
    }

    private fun createFile() {
        storageFile.parentFile.mkdirs()
        storageFile.createNewFile()
    }

}