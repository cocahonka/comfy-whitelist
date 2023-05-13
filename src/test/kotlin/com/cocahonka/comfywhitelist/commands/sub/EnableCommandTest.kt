package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.listeners.PlayerPreLoginEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EnableCommandTest : CommandTestBase() {

    private lateinit var enableCommand: EnableCommand
    private lateinit var label: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        enableCommand = EnableCommand(generalConfig)
        label = enableCommand.identifier

        generalConfig.disableWhitelist()
        server.pluginManager.registerEvents(PlayerPreLoginEvent(storage), plugin)

        playerWithPermission.addAttachment(plugin, enableCommand.permission, true)
    }

    private fun assertOnlyEnableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistEnabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyAlreadyEnableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistAlreadyEnabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistEnabled()
        assertOnlyEnableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        assertFalse(result)
        assertWhitelistDisabled()
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistEnabled()
        assertOnlyEnableMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier, enableCommand.identifier)
        )

        assertFalse(result)
        assertWhitelistDisabled()
        assertOnlyInvalidUsageMessage(console, enableCommand.usage)
    }

    @Test
    fun `when whitelist already enabled`() {
        generalConfig.enableWhitelist()
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        assertTrue(result)
        assertWhitelistEnabled()
        assertOnlyAlreadyEnableMessage(console)
    }

}