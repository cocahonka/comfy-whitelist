package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
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
        TODO("Register listener to prevent connection")

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

        val joiningPlayer = server.addPlayer()

        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(joiningPlayer)
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

        val joiningPlayer = server.addPlayer()

        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(joiningPlayer)
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

        val joiningPlayer = server.addPlayer()

        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(joiningPlayer)
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

        val joiningPlayer = server.addPlayer()

        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(joiningPlayer)
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

        val joiningPlayer = server.addPlayer()

        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(joiningPlayer)
        assertOnlyAlreadyEnableMessage(console)
    }

}