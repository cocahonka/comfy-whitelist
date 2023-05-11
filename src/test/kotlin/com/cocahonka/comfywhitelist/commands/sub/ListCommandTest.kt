package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ListCommandTest : CommandTestBase() {

    @Test
    fun `when console is sender and storage not empty`() {
        val listCommand = ListCommand(storage)
        val anotherPlayer = server.addPlayer()

        storage.addPlayer(player.name)
        storage.addPlayer(anotherPlayer.name)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = listCommand.identifier,
            args = arrayOf(listCommand.identifier),
        )

        val whitelistedPlayers = storage.getAllWhitelistedPlayers()

        assertTrue(result)
        assertEquals(
            console.nextMessage(),
            Message.WhitelistedPlayersList.getDefault(locale)
                .replace("%s", whitelistedPlayers.joinToString())
        )
        console.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender and storage empty`() {
        val listCommand = ListCommand(storage)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = listCommand.identifier,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertEquals(console.nextMessage(), Message.EmptyWhitelistedPlayersList.getDefault(locale))
        console.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender without permission`() {
        val listCommand = ListCommand(storage)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = listCommand.identifier,
            args = arrayOf(listCommand.identifier),
        )

        assertFalse(result)
        assertEquals(player.nextMessage(), Message.NoPermission.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender with permission`() {
        val listCommand = ListCommand(storage)

        player.addAttachment(plugin, listCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = listCommand.identifier,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertEquals(player.nextMessage(), Message.EmptyWhitelistedPlayersList.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when to many arguments`() {
        val listCommand = ListCommand(storage)

        val result = handler.onCommand(
            sender = console,
            command = command,
            label = listCommand.identifier,
            args = arrayOf(listCommand.identifier, player.name),
        )

        assertFalse(result)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", listCommand.usage)
        )
        console.assertNoMoreSaid()
    }

}