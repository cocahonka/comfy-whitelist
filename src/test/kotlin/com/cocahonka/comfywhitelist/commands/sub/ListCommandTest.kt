package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListCommandTest : CommandTestBase() {

    private lateinit var listCommand: ListCommand
    private lateinit var label: String

    @BeforeEach
    override fun setUp() {
        super.setUp()
        listCommand = ListCommand(storage)
        label = listCommand.identifier
    }

    private fun assertOnlyWhitelistedPlayersListMessage(sender: MessageTarget) {
        val whitelistedPlayers = storage.getAllWhitelistedPlayers()
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistedPlayersList.getDefault(locale)
                .replace("%s", whitelistedPlayers.joinToString())
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyEmptyWhitelistedPlayersListMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.EmptyWhitelistedPlayersList.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }


    @Test
    fun `when console is sender and storage not empty`() {
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertOnlyWhitelistedPlayersListMessage(console)
    }

    @Test
    fun `when console is sender and storage empty`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertOnlyEmptyWhitelistedPlayersListMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertFalse(result)
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        player.addAttachment(plugin, listCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertOnlyEmptyWhitelistedPlayersListMessage(player)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier, player.name),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(console, listCommand.usage)
    }

}