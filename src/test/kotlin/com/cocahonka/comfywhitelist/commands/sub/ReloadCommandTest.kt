package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.base.ConfigManager
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import org.bukkit.configuration.file.FileConfiguration
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.util.UUID
import kotlin.properties.Delegates

class ReloadCommandTest : CommandTestBase() {

    private lateinit var reloadCommand: ReloadCommand
    private lateinit var messageConfig: MessageConfig
    private var newEnabled by Delegates.notNull<Boolean>()
    private lateinit var newPlayerAddedMessage: String
    private lateinit var label: String

    private fun assertOnlyPluginReloadedMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.PluginReloaded.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    @BeforeEach
    override fun setUp() {
        super.setUp()
        reloadCommand = ReloadCommand(plugin)
        label = reloadCommand.identifier
        messageConfig = MessageConfig(plugin, locale).apply { loadConfig() }
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
    }

    private fun assertEnabledUpdatedTrue() =
        assertEquals(GeneralConfig.whitelistEnabled, newEnabled)

    private fun assertEnabledUpdatedFalse() =
        assertNotEquals(GeneralConfig.whitelistEnabled, newEnabled)

    private fun assertMessageUpdatedTrue() =
        assertEquals(MessageConfig.playerAdded, newPlayerAddedMessage)

    private fun assertMessageUpdatedFalse() =
        assertNotEquals(MessageConfig.playerAdded, newPlayerAddedMessage)

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
            sender = player,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier)
        )

        assertFalse(result)
        assertEnabledUpdatedFalse()
        assertMessageUpdatedFalse()
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        player.addAttachment(plugin, reloadCommand.permission, true)
        val result = handler.onCommand(
            sender = player,
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
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(reloadCommand.identifier, player.name)
        )

        assertFalse(result)
        assertEnabledUpdatedFalse()
        assertMessageUpdatedFalse()
        assertOnlyInvalidUsageMessage(console, reloadCommand.usage)
    }

}