package com.cocahonka.comfywhitelist.config.message

import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object MessageFormat {
    val miniMessage = MiniMessage.miniMessage()

    object ConfigBuilders {
        val nameReplacementConfigBuilder = { playerName: String ->
            TextReplacementConfig.builder()
                .match("<name>")
                .replacement(playerName)
                .build()
        }

        val usageReplacementConfigBuilder = { usage: String ->
            TextReplacementConfig.builder()
                .match("<usage>")
                .replacement(usage)
                .build()
        }

        val playersReplacementConfigBuilder = { players: Set<String> ->
            TextReplacementConfig.builder()
                .match("<players>")
                .replacement(players.joinToString())
                .build()
        }
    }

    object Colors {
        val prefix = TextColor.fromHexString("#e681a4")!!
        val warning = TextColor.fromHexString("#cc2e23")!!
        val success = TextColor.fromHexString("#5ab317")!!
        val remove = TextColor.fromHexString("#6a66d1")!!
        val off = TextColor.fromHexString("#999c97")!!
    }

    object Resolvers {
        val warning = TagResolver.resolver("warning", Tag.styling(Colors.warning))
        val success = TagResolver.resolver("success", Tag.styling(Colors.success))
        val remove = TagResolver.resolver("remove", Tag.styling(Colors.remove))
        val off = TagResolver.resolver("off", Tag.styling(Colors.off))
    }

}