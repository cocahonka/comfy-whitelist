package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.Message
import org.bukkit.configuration.file.FileConfiguration
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class ClearCommandTest : CommandTestBase() {
    
    private lateinit var clearCommand: ClearCommand
    private lateinit var label: String

    private lateinit var addedPlayer: PlayerMock
    private lateinit var addedPlayerSecond: PlayerMock

    private lateinit var generalFileConfiguration: FileConfiguration
    private lateinit var generalConfigFile: File
    private lateinit var clearCommandKey: String

    
    @BeforeEach
    override fun setUp() {
        super.setUp()
        clearCommand = ClearCommand(storage)
        label = clearCommand.identifier

        addedPlayer = server.addPlayer()
        addedPlayerSecond = server.addPlayer()

        storage.addPlayer(addedPlayer.name)
        storage.addPlayer(addedPlayerSecond.name)

        playerWithPermission.addAttachment(plugin, clearCommand.permission, true)

        generalFileConfiguration = ConfigManager::class.java
            .getDeclaredField("config")
            .apply { isAccessible = true }
            .get(generalConfig) as FileConfiguration

        generalConfigFile = ConfigManager::class.java
            .getDeclaredField("configFile")
            .apply { isAccessible = true }
            .get(generalConfig) as File

        clearCommandKey = GeneralConfig::class.java
            .getDeclaredField("clearCommandKey")
            .apply { isAccessible = true }
            .get(GeneralConfig.Companion::class.java) as String

        generalFileConfiguration.set(clearCommandKey, true)
        generalFileConfiguration.save(generalConfigFile)
        generalConfig.loadConfig()
    }

    private fun assertOnlyWhitelistClearedMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.WhitelistCleared.getDefault(locale))
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyInactiveCommandMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.InactiveCommand.getDefault(locale))
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertOnlyWhitelistClearedMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertWhitelisted(addedPlayerSecond)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertOnlyWhitelistClearedMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier, clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertWhitelisted(addedPlayerSecond)
        assertOnlyInvalidUsageMessage(console, clearCommand.usage)
    }

    @Test
    fun `when command is off`() {
        generalFileConfiguration.set(clearCommandKey, false)
        generalFileConfiguration.save(generalConfigFile)
        generalConfig.loadConfig()

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertWhitelisted(addedPlayerSecond)
        assertOnlyInactiveCommandMessage(console)
    }

}