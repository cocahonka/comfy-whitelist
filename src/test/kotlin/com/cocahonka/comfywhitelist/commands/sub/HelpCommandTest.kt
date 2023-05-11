package com.cocahonka.comfywhitelist.commands.sub

import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelpCommandTest : CommandTestBase() {

    private val commands = listOf(
        AddCommand(storage),
        RemoveCommand(storage),
        ListCommand(storage)
    )

    private val helpMessage =
        "ComfyWhitelist >\n" +
                "> /comfywl add <name>\n" +
                "> /comfywl remove <name>\n" +
                "> /comfywl list"

    @Test
    fun `when console is sender`() {
        val helpCommand = HelpCommand(commands)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = helpCommand.identifier,
            args = arrayOf(helpCommand.identifier)
        )

        assertTrue(result)
        assertEquals(console.nextMessage(), helpMessage)
        console.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender without permission`() {
        val helpCommand = HelpCommand(commands)
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = helpCommand.identifier,
            args = arrayOf(helpCommand.identifier)
        )

        assertFalse(result)
        assertEquals(player.nextMessage(), Message.NoPermission.getDefault(locale))
        player.assertNoMoreSaid()
    }

    @Test
    fun `when player is sender with permission`() {
        val helpCommand = HelpCommand(commands)
        player.addAttachment(plugin, helpCommand.permission, true)

        val result = handler.onCommand(
            sender = player,
            command = command,
            label = helpCommand.identifier,
            args = arrayOf(helpCommand.identifier)
        )

        assertTrue(result)
        assertEquals(player.nextMessage(), helpMessage)
        player.assertNoMoreSaid()
    }

    @Test
    fun `when to many arguments`() {
        val helpCommand = HelpCommand(commands)
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = helpCommand.identifier,
            args = arrayOf(helpCommand.identifier, player.name),
        )

        assertFalse(result)
        assertEquals(
            console.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", helpCommand.usage)
        )
        console.assertNoMoreSaid()
    }
}