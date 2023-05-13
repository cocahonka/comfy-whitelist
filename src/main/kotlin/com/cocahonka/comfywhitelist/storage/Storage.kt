package com.cocahonka.comfywhitelist.storage

/**
 * WhitelistStorage provides an abstraction for accessing and manipulating whitelist data.
 * Implementations of this interface can store the data in different sources, such as files or databases.
 */
interface Storage {

    /**
     * Adds a player to the whitelist.
     * @param username The username of the player to be added.
     * @return true if the player was successfully added, false otherwise.
     */
    fun addPlayer(username: String): Boolean

    /**
     * Removes a player from the whitelist.
     * @param username The username of the player to be removed.
     * @return true if the player was successfully removed, false otherwise.
     */
    fun removePlayer(username: String): Boolean

    /**
     * Removes all players from the whitelist. This method is useful if you need to completely clear
     * the whitelist and start populating it from scratch.
     */
    fun clear(): Boolean

    /**
     * Checks if a player is in the whitelist.
     * @param username The username of the player to check.
     * @return true if the player is in the whitelist, false otherwise.
     */
    fun isPlayerWhitelisted(username: String): Boolean

    /**
     * Retrieves all whitelisted players.
     * @return A set of usernames of all players in the whitelist.
     */
    fun getAllWhitelistedPlayers(): Set<String>

    /**
     * Loads whitelist data from the data source.
     * @return true if the data was successfully loaded, false otherwise.
     */
    fun load(): Boolean

    /**
     * Saves the current whitelist data to the data source.
     * @return true if the data was successfully saved, false otherwise.
     */
    fun save(): Boolean

}