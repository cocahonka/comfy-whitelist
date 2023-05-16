package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getDefaultWithPrefix
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearCommandTest : CommandTestBase() {
    
    private lateinit var clearCommand: ClearCommand
    private lateinit var label: String

    private lateinit var addedPlayer: PlayerMock
    private lateinit var addedPlayerSecond: PlayerMock

    
    @BeforeEach
    override fun setUp() {
        super.setUp()
        clearCommand = ClearCommand(storage)
        label = clearCommand.identifier

        addedPlayer = server.addPlayer()
        addedPlayerSecond = server.addPlayer()

        storage.addPlayer(addedPlayer.name)
        storage.addPlayer(addedPlayerSecond.name)

        playerWithPermission.addAttachment(plugin, clearCommand.permission, true)
    }

    private fun assertOnlyWhitelistClearedMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.WhitelistCleared.getDefaultWithPrefix(locale))
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertOnlyWhitelistClearedMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertWhitelisted(addedPlayerSecond)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier)
        )

        assertTrue(result)
        assertStorageEmpty()
        assertOnlyWhitelistClearedMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(clearCommand.identifier, clearCommand.identifier)
        )

        assertFalse(result)
        assertWhitelisted(addedPlayer)
        assertWhitelisted(addedPlayerSecond)
        assertOnlyInvalidUsageMessage(console, clearCommand.usage)
    }

}