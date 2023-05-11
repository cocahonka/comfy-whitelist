package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AddCommandTest : CommandTestBase() {

    @Test
    fun `when console is sender`() {
        val addCommand = AddCommand(storage)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = addCommand.identifier,
            args = arrayOf(addCommand.identifier, player.name),
        )

        assertTrue(result)
        assertWhitelisted(player.name)
        assertEquals(
            console.nextMessage(),
            Message.PlayerAdded.getDefault(locale).replace("%s", player.name)
        )
        console.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender without permission`() {
        val addCommand = AddCommand(storage)
        val anotherPlayer = server.addPlayer()
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = addCommand.identifier,
            args = arrayOf(addCommand.identifier, anotherPlayer.name),
        )

        assertFalse(result)
        assertNotWhitelisted(anotherPlayer.name)
        assertEquals(player.nextMessage(), Message.NoPermission.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender with permission`() {
        val addCommand = AddCommand(storage)
        val anotherPlayer = server.addPlayer()
        player.addAttachment(plugin, addCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = addCommand.identifier,
            args = arrayOf(addCommand.identifier, anotherPlayer.name),
        )

        assertTrue(result)
        assertWhitelisted(anotherPlayer.name)
        assertEquals(
            player.nextMessage(),
            Message.PlayerAdded.getDefault(locale).replace("%s", player.name)
        )
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player name not provided`() {
        val addCommand = AddCommand(storage)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = addCommand.identifier,
            args = arrayOf(addCommand.identifier),
        )

        assertFalse(result)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", addCommand.usage)
        )
        console.assertNoMoreSaid()
    }

    @Test
    fun `when to many arguments`() {
        val addCommand = AddCommand(storage)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = addCommand.identifier,
            args = arrayOf(addCommand.identifier, player.name, player.name),
        )

        assertFalse(result)
        assertNotWhitelisted(player.name)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", addCommand.usage)
        )
        console.assertNoMoreSaid()
    }

}