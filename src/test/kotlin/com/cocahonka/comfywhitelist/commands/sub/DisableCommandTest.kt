package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DisableCommandTest : CommandTestBase() {

    private lateinit var disableCommand: DisableCommand
    private lateinit var joiner: PlayerMock
    private lateinit var label: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        disableCommand = DisableCommand(generalConfig)
        label = disableCommand.identifier
        generalConfig.enableWhitelist()
        TODO("Register listener to prevent connection")
        joiner = server.addPlayer()
    }

    private fun assertOnlyDisableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistDisabled.getDefault(locale)
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

        val reconnectedPlayer = server.addPlayer(joiner.name)

        assertTrue(result)
        assertWhitelistDisabled()
        assertConnectedTrue(reconnectedPlayer)
        assertOnlyDisableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        val reconnectedPlayer = server.addPlayer(joiner.name)

        assertFalse(result)
        assertWhitelistEnabled()
        assertConnectedFalse(reconnectedPlayer)
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        player.addAttachment(plugin, disableCommand.permission, true)
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        val reconnectedPlayer = server.addPlayer(joiner.name)

        assertTrue(result)
        assertWhitelistDisabled()
        assertConnectedTrue(reconnectedPlayer)
        assertOnlyDisableMessage(player)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier, joiner.name)
        )

        val reconnectedPlayer = server.addPlayer(joiner.name)

        assertFalse(result)
        assertWhitelistEnabled()
        assertConnectedFalse(reconnectedPlayer)
        assertOnlyInvalidUsageMessage(console, disableCommand.usage)
    }

}