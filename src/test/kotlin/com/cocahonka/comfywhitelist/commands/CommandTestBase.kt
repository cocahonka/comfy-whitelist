package com.cocahonka.comfywhitelist.commands

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock
import be.seeseemelk.mockbukkit.command.MessageTarget
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.api.Storage
import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.general.GeneralConfig
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getDefaultWithPrefix
import com.cocahonka.comfywhitelist.config.message.MessageConfig
import com.cocahonka.comfywhitelist.config.message.MessageFormat
import com.cocahonka.comfywhitelist.storage.YamlStorage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.PluginCommand
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

abstract class CommandTestBase {

    protected lateinit var server: ServerMock
    protected lateinit var plugin: ComfyWhitelist

    protected lateinit var console: ConsoleCommandSenderMock
    protected lateinit var playerWithPermission: PlayerMock
    protected lateinit var playerWithoutPermission: PlayerMock

    protected lateinit var storage: Storage
    protected lateinit var generalConfig: GeneralConfig
    protected lateinit var messageConfig: MessageConfig
    protected val locale = Locale.EN

    protected lateinit var command: PluginCommand
    protected lateinit var handler: CommandHandler

    protected val legacySection = LegacyComponentSerializer.legacySection()

    @BeforeEach
    open fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(ComfyWhitelist::class.java)

        console = ConsoleCommandSenderMock()
        playerWithPermission = server.addPlayer()
        playerWithoutPermission = server.addPlayer()

        storage = YamlStorage(plugin.dataFolder)
        generalConfig = GeneralConfig(plugin).apply { loadConfig() }
        messageConfig = MessageConfig(plugin, locale).apply { loadConfig() }

        command = plugin.getCommand(CommandHandler.identifier)!!
        handler = CommandHandler(storage, generalConfig, plugin)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    protected fun assertConnectedTrue(event: AsyncPlayerPreLoginEvent) =
        assertEquals(AsyncPlayerPreLoginEvent.Result.ALLOWED, event.loginResult)

    protected fun assertConnectedFalse(event: AsyncPlayerPreLoginEvent) =
        assertEquals(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, event.loginResult)

    protected fun assertWhitelistEnabled() =
        assertTrue(GeneralConfig.whitelistEnabled)

    protected fun assertWhitelistDisabled() =
        assertFalse(GeneralConfig.whitelistEnabled)

    protected fun assertWhitelisted(player: PlayerMock) =
        assertTrue(storage.isPlayerWhitelisted(player.name))

    protected fun assertNotWhitelisted(player: PlayerMock) =
        assertFalse(storage.isPlayerWhitelisted(player.name))

    protected fun assertStorageEmpty() =
        assertTrue(storage.allWhitelistedPlayers.isEmpty())

    protected fun assertOnlyNoPermissionMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.NoPermission.getDefaultWithPrefix(locale))
        )
        sender.assertNoMoreSaid()
    }

    protected fun assertOnlyInvalidPlayerNameMessage(sender: MessageTarget) {
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(Message.InvalidPlayerName.getDefaultWithPrefix(locale))
        )
        sender.assertNoMoreSaid()
    }

    protected fun assertOnlyInvalidUsageMessage(sender: MessageTarget, usage: String) {
        val replacementConfig = MessageFormat.ConfigBuilders.usageReplacementConfigBuilder(usage)
        val message = Message.InvalidUsage.getDefaultWithPrefix(locale).replaceText(replacementConfig)
        assertEquals(
            sender.nextMessage(),
            legacySection.serialize(message)
        )
        sender.assertNoMoreSaid()
    }

}