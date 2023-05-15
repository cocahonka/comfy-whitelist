package com.cocahonka.comfywhitelist.config.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object MessageTagResolvers {
    val warning = TagResolver.resolver("warning", Tag.styling(TextColor.fromHexString("#FF0000")!!))

    fun insertUsage(usage: String) = Placeholder.component("usage", Component.text(usage))
    fun insertName(name: String) = Placeholder.component("name", Component.text(name))
    fun insertPlayers(players: Set<String>) = Placeholder.component("players", Component.text(players.joinToString()))

}