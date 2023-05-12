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
    }

    private fun assertOnlyEnableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistEnabled.getDefault(locale)
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

        val joiner = server.addPlayer()

        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(joiner)
        assertOnlyEnableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        val joiner = server.addPlayer()

        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(joiner)
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        player.addAttachment(plugin, enableCommand.permission, true)
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        val joiner = server.addPlayer()

        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(joiner)
        assertOnlyEnableMessage(player)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier, player.name)
        )

        val joiner = server.addPlayer()

        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(joiner)
        assertOnlyInvalidUsageMessage(console, enableCommand.usage)
    }

}