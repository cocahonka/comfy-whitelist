package com.cocahonka.comfywhitelist.commands

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.storage.Storage
import com.cocahonka.comfywhitelist.storage.YamlStorage
import org.bukkit.command.PluginCommand
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach

abstract class CommandTestBase {

    protected lateinit var server: ServerMock
    protected lateinit var plugin: ComfyWhitelist
    protected lateinit var command: PluginCommand
    protected lateinit var player: PlayerMock
    protected lateinit var console: ConsoleCommandSenderMock
    protected lateinit var storage: Storage
    protected lateinit var handler: CommandHandler
    protected val locale = Locale.EN

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(ComfyWhitelist::class.java)
        command = plugin.getCommand(CommandHandler.identifier)!!
        player = server.addPlayer()
        console = ConsoleCommandSenderMock()
        storage = YamlStorage(plugin.dataFolder)
        handler = CommandHandler(storage)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    protected fun assertWhitelisted(name: String) =
        assertTrue(storage.isPlayerWhitelisted(name))

    protected fun assertNotWhitelisted(name: String) =
        assertFalse(storage.isPlayerWhitelisted(name))

}