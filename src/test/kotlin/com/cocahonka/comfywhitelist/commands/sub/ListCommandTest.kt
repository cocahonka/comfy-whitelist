package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getDefaultWithPrefix
import com.cocahonka.comfywhitelist.config.message.MessageFormat
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

        playerWithPermission.addAttachment(plugin, listCommand.permission, true)
    }

    private fun assertOnlyWhitelistedPlayersListMessage(sender: MessageTarget) {
        val whitelistedPlayers = storage.allWhitelistedPlayers
        val replacementConfig = MessageFormat.ConfigBuilders.playersReplacementConfigBuilder(whitelistedPlayers)
        val message = Message.WhitelistedPlayersList.getDefaultWithPrefix(locale).replaceText(replacementConfig)
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(message)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyEmptyWhitelistedPlayersListMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.EmptyWhitelistedPlayersList.getDefaultWithPrefix(locale))
        )
        sender.assertNoMoreSaid()
    }


    @Test
    fun `when console is sender and storage not empty`() {
        val addedPlayer = server.addPlayer()
        val addedPlayerSecond = server.addPlayer()

        storage.addPlayer(addedPlayer.name)
        storage.addPlayer(addedPlayerSecond.name)

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
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertFalse(result)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier),
        )

        assertTrue(result)
        assertOnlyEmptyWhitelistedPlayersListMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(listCommand.identifier, listCommand.identifier),
        )

        assertFalse(result)
        assertOnlyInvalidUsageMessage(console, listCommand.usage)
    }

}