package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddCommandTest : CommandTestBase() {
    
    private lateinit var addCommand: AddCommand
    private lateinit var label: String
    
    @BeforeEach
    override fun setUp() {
        super.setUp()
        addCommand = AddCommand(storage)
        label = addCommand.identifier
    }

    private fun assertOnlyPlayerAddedMessage(sender: MessageTarget, player: PlayerMock) {
        assertEquals(
            sender.nextMessage(),
            Message.PlayerAdded.getDefault(locale).replace("%s", player.name)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, player.name),
        )

        assertTrue(result)
        assertWhitelisted(player)
        assertOnlyPlayerAddedMessage(console, player)
    }

    @Test
    fun `when player is sender without permission`() {
        val anotherPlayer = server.addPlayer()
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, anotherPlayer.name),
        )

        assertFalse(result)
        assertNotWhitelisted(anotherPlayer)
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        val anotherPlayer = server.addPlayer()
        player.addAttachment(plugin, addCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, anotherPlayer.name),
        )

        assertTrue(result)
        assertWhitelisted(anotherPlayer)
        assertOnlyPlayerAddedMessage(player, anotherPlayer)
    }

    @Test
    fun `when player name not provided`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(player, addCommand.usage)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, player.name, player.name),
        )
        assertFalse(result)
        assertNotWhitelisted(player)
        assertOnlyInvalidUsageMessage(console, addCommand.usage)
    }

}