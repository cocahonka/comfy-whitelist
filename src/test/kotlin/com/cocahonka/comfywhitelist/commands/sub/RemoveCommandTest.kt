package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveCommandTest : CommandTestBase() {

    private lateinit var removeCommand: RemoveCommand
    private lateinit var label: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        removeCommand = RemoveCommand(storage)
        label = removeCommand.identifier

        playerWithPermission.addAttachment(plugin, removeCommand.permission, true)
    }

    private fun assertOnlyPlayerRemovedMessage(sender: MessageTarget, player: PlayerMock) {
        assertEquals(
            sender.nextMessage(),
            Message.PlayerRemoved.getDefault(locale).replace("%s", player.name)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        storage.addPlayer(playerWithPermission.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, playerWithPermission.name),
        )

        assertTrue(result)
        assertNotWhitelisted(playerWithPermission)
        assertOnlyPlayerRemovedMessage(console, playerWithPermission)
    }

    @Test
    fun `when player is sender without permission`() {
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, anotherPlayer.name),
        )

        assertFalse(result)
        assertWhitelisted(anotherPlayer)
        assertOnlyNoPermissionMessage(playerWithPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(anotherPlayer.name)

        playerWithPermission.addAttachment(plugin, removeCommand.permission, true)

        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, anotherPlayer.name),
        )

        assertTrue(result)
        assertNotWhitelisted(anotherPlayer)
        assertOnlyPlayerRemovedMessage(playerWithPermission, anotherPlayer)
    }

    @Test
    fun `when player name not provided`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(console, removeCommand.usage)
    }

    @Test
    fun `when to many arguments`() {
        storage.addPlayer(playerWithPermission.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, playerWithPermission.name, playerWithPermission.name),
        )

        assertFalse(result)
        assertWhitelisted(playerWithPermission)
        assertOnlyInvalidUsageMessage(console, removeCommand.usage)
    }

}