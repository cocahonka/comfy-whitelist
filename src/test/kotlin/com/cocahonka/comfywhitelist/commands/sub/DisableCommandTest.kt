package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.listeners.PlayerPreLoginEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DisableCommandTest : CommandTestBase() {

    private lateinit var disableCommand: DisableCommand
    private lateinit var label: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        disableCommand = DisableCommand(generalConfig)
        label = disableCommand.identifier

        generalConfig.enableWhitelist()

        playerWithPermission.addAttachment(plugin, disableCommand.permission, true)
    }

    private fun assertOnlyDisableMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistDisabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyAlreadyDisableMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistAlreadyDisabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistDisabled()
        assertOnlyDisableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        assertFalse(result)
        assertWhitelistEnabled()
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistDisabled()
        assertOnlyDisableMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier, disableCommand.identifier)
        )

        assertFalse(result)
        assertWhitelistEnabled()
        assertOnlyInvalidUsageMessage(console, disableCommand.usage)
    }

    @Test
    fun `when whitelist already disabled`() {
        generalConfig.disableWhitelist()
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistDisabled()
        assertOnlyAlreadyDisableMessage(console)
    }

}