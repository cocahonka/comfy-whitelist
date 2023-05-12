package com.cocahonka.comfywhitelist.commands

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock
import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.storage.Storage
import com.cocahonka.comfywhitelist.storage.YamlStorage
import org.bukkit.command.PluginCommand
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

abstract class CommandTestBase {

    protected lateinit var server: ServerMock
    protected lateinit var plugin: ComfyWhitelist
    protected lateinit var command: PluginCommand
    protected lateinit var player: PlayerMock
    protected lateinit var console: ConsoleCommandSenderMock
    protected lateinit var storage: Storage
    protected lateinit var handler: CommandHandler
    protected lateinit var generalConfig: GeneralConfig
    protected val locale = Locale.EN

    @BeforeEach
    open fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(ComfyWhitelist::class.java)
        command = plugin.getCommand(CommandHandler.identifier)!!
        player = server.addPlayer()
        console = ConsoleCommandSenderMock()
        storage = YamlStorage(plugin.dataFolder)
        generalConfig = GeneralConfig(plugin)
        handler = CommandHandler(storage, generalConfig, plugin)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    protected fun assertConnectedTrue(player: PlayerMock) =
        assertTrue(player.isOnline)

    protected fun assertConnectedFalse(player: PlayerMock) =
        assertFalse(player.isOnline)

    protected fun assertWhitelistEnabled() =
        assertTrue(GeneralConfig.whitelistEnabled)

    protected fun assertWhitelistDisabled() =
        assertFalse(GeneralConfig.whitelistEnabled)

    protected fun assertWhitelisted(player: PlayerMock) =
        assertTrue(storage.isPlayerWhitelisted(player.name))

    protected fun assertNotWhitelisted(player: PlayerMock) =
        assertFalse(storage.isPlayerWhitelisted(player.name))

    protected fun assertStorageEmpty() =
        assertTrue(storage.getAllWhitelistedPlayers().isEmpty())

    protected fun assertOnlyNoPermissionMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            Message.NoPermission.getDefault(locale)
        )
        sender.assertNoMoreSaid()
    }

    protected fun assertOnlyInvalidUsageMessage(sender: MessageTarget, usage: String) {
        assertEquals(
            sender.nextMessage(),
            Message.InvalidUsage.getDefault(locale).replace("%s", usage)
        )
        sender.assertNoMoreSaid()
    }

}