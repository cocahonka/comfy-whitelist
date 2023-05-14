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

class DisableCommandTest : CommandTestBase() {

    private lateinit var eventCallerThread: Thread
    private lateinit var event: AsyncPlayerPreLoginEvent
    private lateinit var latch: CountDownLatch

    private lateinit var disableCommand: DisableCommand
    private lateinit var label: String

    private val timeout = 2L
    private val timeUnit = TimeUnit.SECONDS

    @BeforeEach
    override fun setUp() {
        super.setUp()
        disableCommand = DisableCommand(generalConfig)
        label = disableCommand.identifier

        generalConfig.enableWhitelist()

        server.pluginManager.registerEvents(PlayerPreLoginEvent(storage), plugin)

        val joiningPlayer = server.addPlayer()

        latch = CountDownLatch(1)

        eventCallerThread = Thread {
            val inetAddress = InetAddress.getLocalHost()
            event = AsyncPlayerPreLoginEvent(joiningPlayer.name, inetAddress, joiningPlayer.uniqueId)

            server.pluginManager.callEvent(event)

            latch.countDown()
        }

        playerWithPermission.addAttachment(plugin, disableCommand.permission, true)
    }

    private fun assertOnlyDisableMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistDisabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    private fun assertOnlyAlreadyDisableMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.WhitelistAlreadyDisabled.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    @Test
    fun `when console is sender`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        eventCallerThread.start()
        latch.await(timeout, timeUnit)

        assertTrue(result)
        assertWhitelistDisabled()
        assertConnectedTrue(event)
        assertOnlyDisableMessage(console)
    }

    @Test
    fun `when player is sender without permission`() {
        val result = handler.onCommand(
            sender = playerWithoutPermission,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        eventCallerThread.start()
        latch.await(timeout, timeUnit)

        assertFalse(result)
        assertWhitelistEnabled()
        assertConnectedFalse(event)
        assertOnlyNoPermissionMessage(playerWithoutPermission)
    }

    @Test
    fun `when player is sender with permission`() {
        val result = handler.onCommand(
            sender = playerWithPermission,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        eventCallerThread.start()
        latch.await(timeout, timeUnit)

        assertTrue(result)
        assertWhitelistDisabled()
        assertConnectedTrue(event)
        assertOnlyDisableMessage(playerWithPermission)
    }

    @Test
    fun `when to many arguments`() {
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier, disableCommand.identifier)
        )

        eventCallerThread.start()
        latch.await(timeout, timeUnit)

        assertFalse(result)
        assertWhitelistEnabled()
        assertConnectedFalse(event)
        assertOnlyInvalidUsageMessage(console, disableCommand.usage)
    }

    @Test
    fun `when whitelist already disabled`() {
        generalConfig.disableWhitelist()
        val result = handler.onCommand(
            sender = console,
            command = command,
            label = label,
            args = arrayOf(disableCommand.identifier)
        )

        eventCallerThread.start()
        latch.await(timeout, timeUnit)

        assertTrue(result)
        assertWhitelistDisabled()
        assertConnectedTrue(event)
        assertOnlyAlreadyDisableMessage(console)
    }

}