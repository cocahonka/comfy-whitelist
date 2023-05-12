package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HelpCommandTest : CommandTestBase() {

    private lateinit var helpCommand: HelpCommand
    private lateinit var label: String

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

    @BeforeEach
    override fun setUp() {
        super.setUp()
        helpCommand = HelpCommand(commands)
        label = helpCommand.identifier
    }

    private fun assertOnlyHelpMessage(sender: MessageTarget) {
       assertEquals(
            sender.nextMessage(),
            helpMessage
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(helpCommand.identifier)
        )

        assertTrue(result)
        assertOnlyHelpMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(helpCommand.identifier)
        )

        assertFalse(result)
        assertOnlyNoPermissionMessage(player)
    }

    @Test
    fun `when player is sender with permission`() {
        player.addAttachment(plugin, helpCommand.permission, true)
        val result = handler.onCommand(
            sender = player,
            command = command,
            label = label,
            args = arrayOf(helpCommand.identifier)
        )

        assertTrue(result)
        assertOnlyHelpMessage(player)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(helpCommand.identifier, player.name),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(console, helpCommand.usage)
    }
}