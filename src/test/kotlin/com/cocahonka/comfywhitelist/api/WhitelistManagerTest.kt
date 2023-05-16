package com.cocahonka.comfywhitelist.api

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.MockPlugin
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.cocahonka.comfywhitelist.config.message.MessageTagResolvers
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhitelistManagerTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: MockPlugin
    private lateinit var player: PlayerMock

    @BeforeEach
    fun setup() {
        server = MockBukkit.mock()
        plugin = MockBukkit.createMockPlugin()
        player = server.addPlayer()
    }

    @AfterEach
    fun end() {
        MockBukkit.unmock()
    }

    @Test
    fun test1() {
        val mm = MiniMessage.miniMessage()
        val message = "<warning>warning! <name> </warning>"
        val messageComponent = mm.deserialize(
            message,
            MessageTagResolvers.warning,
            MessageTagResolvers.insertName("cocahonka")
        )
        player.sendMessage(messageComponent)
        println(player.nextMessage())
    }

    @Test
    fun test2() {
        val mm = MiniMessage.miniMessage()
        val message = "<warning>warning! <name> </warning>"
        val messageComponent = mm.deserialize(
            message,
            MessageTagResolvers.warning,
        )
        val nameReplacing = TextReplacementConfig.builder()
            .match("<name>")
            .replacement("cocahonka")
            .build()

        val messageComponent2 = messageComponent.replaceText(nameReplacing)
        player.sendMessage(messageComponent2)
        println(player.nextMessage())
    }
}