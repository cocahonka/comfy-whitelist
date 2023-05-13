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

    private lateinit var addedPlayer: PlayerMock

    @BeforeEach
    override fun setUp() {
        super.setUp()
        removeCommand = RemoveCommand(storage)
        label = removeCommand.identifier

        addedPlayer = server.addPlayer()
        storage.addPlayer(addedPlayer.name)

        playerWithPermission.addAttachment(plugin, removeCommand.permission, true)
    }

    private fun assertOnlyPlayerRemovedMessage(sender: MessageTarget, player: PlayerMock) {
        assertEquals(
            sender.nextMessage(),
            Message.PlayerRemoved.getDefault(locale).replace("%s", player.name)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyNonExistentPlayerName(sender: MessageTarget, player: PlayerMock) {
        assertEquals(
            sender.nextMessage(),
            Message.NonExistentPlayerName.getDefault(locale).replace("%s", player.name)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, addedPlayer.name),
        )

        assertTrue(result)
        assertNotWhitelisted(addedPlayer)
        assertOnlyPlayerRemovedMessage(console, addedPlayer)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, addedPlayer.name),
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertOnlyNoPermissionMessage(playerWithPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, addedPlayer.name),
        )

        assertTrue(result)
        assertNotWhitelisted(addedPlayer)
        assertOnlyPlayerRemovedMessage(playerWithPermission, addedPlayer)
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
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, addedPlayer.name, addedPlayer.name),
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertOnlyInvalidUsageMessage(console, removeCommand.usage)
    }

    @Test
    fun `when player name is invalid`() {
        val invalidPlayer = server.addPlayer("мотузок")
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, invalidPlayer.name),
        )

        assertFalse(result)
        assertOnlyInvalidPlayerNameMessage(playerWithPermission)
    }

    @Test
    fun `when player does not exist in whitelist`() {
        val notAddedPlayer = server.addPlayer()
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(removeCommand.identifier, notAddedPlayer.name),
        )

        assertFalse(result)
        assertNotWhitelisted(notAddedPlayer)
        assertOnlyNonExistentPlayerName(playerWithPermission, notAddedPlayer)
    }

}