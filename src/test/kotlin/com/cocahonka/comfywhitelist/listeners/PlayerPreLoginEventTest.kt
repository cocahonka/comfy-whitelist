package com.cocahonka.comfywhitelist.listeners

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.storage.Storage
import com.cocahonka.comfywhitelist.storage.YamlStorage
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.InetAddress
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class PlayerPreLoginEventTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: ComfyWhitelist

    private lateinit var storage: Storage
    private lateinit var generalConfig: GeneralConfig

    private lateinit var joiningPlayer: PlayerMock

    private lateinit var eventCallerThread: Thread
    private lateinit var event: AsyncPlayerPreLoginEvent
    private lateinit var latch: CountDownLatch

    private val timeout = 2L
    private val timeUnit = TimeUnit.SECONDS

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(ComfyWhitelist::class.java)
        storage = YamlStorage(plugin.dataFolder)
        generalConfig = GeneralConfig(plugin).apply { loadConfig() }

        server.pluginManager.registerEvents(PlayerPreLoginEvent(storage), plugin)

        joiningPlayer = server.addPlayer()

        latch = CountDownLatch(1)
        eventCallerThread = Thread {
            val inetAddress = InetAddress.getLocalHost()
            event = AsyncPlayerPreLoginEvent(joiningPlayer.name, inetAddress, joiningPlayer.uniqueId)

            server.pluginManager.callEvent(event)

            latch.countDown()
        }

    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    private fun assertConnectedTrue() =
        Assertions.assertEquals(AsyncPlayerPreLoginEvent.Result.ALLOWED, event.loginResult)

    private fun assertConnectedFalse() =
        Assertions.assertEquals(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, event.loginResult)

    private fun executeEvent() {
        eventCallerThread.start()
        latch.await(timeout, timeUnit)
    }

    @Test
    fun `when ISN'T whitelisted, whitelist is OFF`() {
        generalConfig.disableWhitelist()
        executeEvent()
        assertConnectedTrue()
    }

    @Test
    fun `when ISN'T whitelisted, whitelist is ON`() {
        generalConfig.enableWhitelist()
        executeEvent()
        assertConnectedFalse()
    }

    @Test
    fun `when IS whitelisted, whitelist is OFF`() {
        generalConfig.disableWhitelist()
        storage.addPlayer(joiningPlayer.name)
        executeEvent()
        assertConnectedTrue()
    }

    @Test
    fun `when IS whitelisted, whitelist is ON`() {
        generalConfig.enableWhitelist()
        storage.addPlayer(joiningPlayer.name)
        executeEvent()
        assertConnectedTrue()
    }

}