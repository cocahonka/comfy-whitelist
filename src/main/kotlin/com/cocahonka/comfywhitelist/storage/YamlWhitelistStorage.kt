package com.cocahonka.comfywhitelist.storage

import java.io.File

class YamlWhitelistStorage(private val file: File) : WhitelistStorage {
    override fun addPlayer(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePlayer(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPlayerWhitelisted(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllWhitelistedPlayers(): Set<String> {
        TODO("Not yet implemented")
    }

    override fun load(): Boolean {
        TODO("Not yet implemented")
    }

    override fun save(): Boolean {
        TODO("Not yet implemented")
    }
}