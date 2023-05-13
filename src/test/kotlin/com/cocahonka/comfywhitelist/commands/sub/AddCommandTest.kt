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

    private lateinit var addedPlayer: PlayerMock
    
    @BeforeEach
    override fun setUp() {
        super.setUp()
        addCommand = AddCommand(storage)
        label = addCommand.identifier
        addedPlayer = server.addPlayer()

        playerWithPermission.addAttachment(plugin, addCommand.permission, true)
    }

    private fun assertOnlyPlayerAddedMessage(sender: MessageTarget, player: PlayerMock) {
        assertEquals(
            sender.nextMessage(),
            Message.PlayerAdded.getDefault(locale).replace("%s", player.name)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyInvalidPlayerNameMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.InvalidPlayerName.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, addedPlayer.name),
        )

        assertTrue(result)
        assertWhitelisted(addedPlayer)
        assertOnlyPlayerAddedMessage(console, addedPlayer)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, addedPlayer.name),
        )

        assertFalse(result)
        assertNotWhitelisted(addedPlayer)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, addedPlayer.name),
        )

        assertTrue(result)
        assertWhitelisted(addedPlayer)
        assertOnlyPlayerAddedMessage(playerWithPermission, addedPlayer)
    }

    @Test
    fun `when player name not provided`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(playerWithPermission, addCommand.usage)
    }

    @Test
    fun `when to many arguments`() {
        val addedPlayerSecond = server.addPlayer()
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, addedPlayer.name, addedPlayerSecond.name),
        )

        assertFalse(result)
        assertNotWhitelisted(addedPlayer)
        assertNotWhitelisted(addedPlayerSecond)
        assertOnlyInvalidUsageMessage(console, addCommand.usage)
    }

    @Test
    fun `when player name is invalid`() {
        val invalidPlayer = server.addPlayer("мотузок")
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(addCommand.identifier, invalidPlayer.name),
        )

        assertFalse(result)
        assertNotWhitelisted(invalidPlayer)
        assertOnlyInvalidPlayerNameMessage(playerWithPermission)
    }

}