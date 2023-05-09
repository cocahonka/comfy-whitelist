package com.cocahonka.comfywhitelist.storage.yaml

import com.cocahonka.comfywhitelist.storage.Storage
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * A YAML file-based implementation of the [Storage] interface.
 *
 * This class stores the whitelisted players' usernames in a YAML configuration file.
 * The storage file is provided during the construction of the class.
 * It supports all the required operations, such as adding, removing, and checking the presence of a player in the whitelist.
 *
 * @property storageFile The file in which the whitelist data is stored.
 */
class YamlStorage(private val storageFile: File) : Storage {
    private val whitelistedPlayers: MutableSet<String> = mutableSetOf()
    private val config: YamlConfiguration = YamlConfiguration.loadConfiguration(storageFile)

    companion object {
        private const val WHITELISTED_PLAYERS_KEY = "players"
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
            whitelistedPlayers.clear()
            whitelistedPlayers.addAll(config.getStringList(WHITELISTED_PLAYERS_KEY))
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun save(): Boolean {
        return try {
            config.set(WHITELISTED_PLAYERS_KEY, whitelistedPlayers.toList())
            config.save(storageFile)
            true
        } catch (e: Exception) {
            false
        }
    }
}