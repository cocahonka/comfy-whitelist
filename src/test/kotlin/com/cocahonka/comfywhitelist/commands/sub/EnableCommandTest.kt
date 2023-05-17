package com.cocahonka.comfywhitelist.commands.sub

import be.seeseemelk.mockbukkit.command.MessageTarget
import com.cocahonka.comfywhitelist.commands.CommandTestBase
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.listeners.PlayerPreLoginEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.InetAddress
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class EnableCommandTest : CommandTestBase() {

    private lateinit var eventCallerThread: Thread
    private lateinit var event: AsyncPlayerPreLoginEvent
    private lateinit var latch: CountDownLatch

    private lateinit var enableCommand: EnableCommand
    private lateinit var label: String

    private val timeout = 2L
    private val timeUnit = TimeUnit.SECONDS

    @BeforeEach
    override fun setUp() {
        super.setUp()
        enableCommand = EnableCommand(generalConfig)
        label = enableCommand.identifier

        generalConfig.disableWhitelist()

        server.pluginManager.registerEvents(PlayerPreLoginEvent(storage), plugin)

        val joiningPlayer = server.addPlayer()
        
        latch = CountDownLatch(1)
        eventCallerThread = Thread {
            val inetAddress = InetAddress.getLocalHost()
            event = AsyncPlayerPreLoginEvent(joiningPlayer.name, inetAddress, joiningPlayer.uniqueId)

            server.pluginManager.callEvent(event)

            latch.countDown()
        }

        playerWithPermission.addAttachment(plugin, enableCommand.permission, true)
    }

    private fun assertOnlyEnableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.WhitelistEnabled.getDefault(locale))
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyAlreadyEnableMessage(sender: MessageTarget){
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.WhitelistAlreadyEnabled.getDefault(locale))
        )
        sender.assertNoMoreSaid()
    }

    private fun executeEvent() {
        eventCallerThread.start()
        latch.await(timeout, timeUnit)
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        executeEvent()
        
        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(event)
        assertOnlyEnableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        executeEvent()
        
        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(event)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        executeEvent()
        
        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(event)
        assertOnlyEnableMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier, enableCommand.identifier)
        )

        executeEvent()
        
        assertFalse(result)
        assertWhitelistDisabled()
        assertConnectedTrue(event)
        assertOnlyInvalidUsageMessage(console, enableCommand.usage)
    }

    @Test
    fun `when whitelist already enabled`() {
        generalConfig.enableWhitelist()
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(enableCommand.identifier)
        )

        executeEvent()
        
        assertTrue(result)
        assertWhitelistEnabled()
        assertConnectedFalse(event)
        assertOnlyAlreadyEnableMessage(console)
    }

}