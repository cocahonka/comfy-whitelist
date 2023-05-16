package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getDefaultWithPrefix
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.configuration.file.FileConfiguration
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.properties.Delegates

class ReloadCommandTest : CommandTestBase() {

    private lateinit var reloadCommand: ReloadCommand
    private lateinit var label: String

    private var newEnabled by Delegates.notNull<Boolean>()
    private lateinit var newPlayerAddedMessage: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        reloadCommand = ReloadCommand(plugin)
        label = reloadCommand.identifier

        newEnabled = !GeneralConfig.whitelistEnabled
        newPlayerAddedMessage = UUID.randomUUID().toString()

        val generalFileConfiguration = ConfigManager::class.java
            .getDeclaredField("config")
            .apply { isAccessible = true }
            .get(generalConfig) as FileConfiguration

        val generalConfigFile = ConfigManager::class.java
            .getDeclaredField("configFile")
            .apply { isAccessible = true }
            .get(generalConfig) as File

        val enabledKey = GeneralConfig::class.java
            .getDeclaredField("enabledKey")
            .apply { isAccessible = true }
            .get(GeneralConfig.Companion::class.java) as String

        val messageFileConfiguration = ConfigManager::class.java
            .getDeclaredField("config")
            .apply { isAccessible = true }
            .get(messageConfig) as FileConfiguration

        val messageConfigFile = ConfigManager::class.java
            .getDeclaredField("configFile")
            .apply { isAccessible = true }
            .get(messageConfig) as File

        val playerAddedKey = Message.PlayerAdded.key

        generalFileConfiguration.set(enabledKey, newEnabled)
        generalFileConfiguration.save(generalConfigFile)

        messageFileConfiguration.set(playerAddedKey, newPlayerAddedMessage)
        messageFileConfiguration.save(messageConfigFile)

        playerWithPermission.addAttachment(plugin, reloadCommand.permission, true)
    }

    private fun assertOnlyPluginReloadedMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.PluginReloaded.getDefaultWithPrefix(locale))
        )
        sender.assertNoMoreSaid()
    }

    private fun assertEnabledUpdatedTrue() =
        assertEquals(GeneralConfig.whitelistEnabled, newEnabled)

    private fun assertEnabledUpdatedFalse() =
        assertNotEquals(GeneralConfig.whitelistEnabled, newEnabled)

    private fun assertMessageUpdatedTrue() =
        assertEquals(MessageConfig.playerAdded, Message.joinWithPrefix(newPlayerAddedMessage))

    private fun assertMessageUpdatedFalse() =
        assertNotEquals(MessageConfig.playerAdded, Message.joinWithPrefix(newPlayerAddedMessage))

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier)
        )

        assertTrue(result)
        assertEnabledUpdatedTrue()
        assertMessageUpdatedTrue()
        assertOnlyPluginReloadedMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier)
        )

        assertFalse(result)
        assertEnabledUpdatedFalse()
        assertMessageUpdatedFalse()
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier)
        )

        assertTrue(result)
        assertEnabledUpdatedTrue()
        assertMessageUpdatedTrue()
        assertOnlyPluginReloadedMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier, reloadCommand.identifier)
        )

        assertFalse(result)
        assertEnabledUpdatedFalse()
        assertMessageUpdatedFalse()
        assertOnlyInvalidUsageMessage(console, reloadCommand.usage)
    }

}