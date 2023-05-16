package com.cocahonka.comfywhitelist.config.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object MessageTagResolvers {
    val prefix = TagResolver.resolver("prefix", Tag.styling(TextColor.fromHexString("#e681a4")!!))
    val warning = TagResolver.resolver("warning", Tag.styling(TextColor.fromHexString("#cc2e23")!!))
    val success = TagResolver.resolver("success", Tag.styling(TextColor.fromHexString("#5ab317")!!))
    val remove = TagResolver.resolver("remove", Tag.styling(TextColor.fromHexString("#6a66d1")!!))
    val off = TagResolver.resolver("off", Tag.styling(TextColor.fromHexString("#999c97")!!))
    fun insertUsage(usage: String) = Placeholder.component("usage", Component.text(usage))
    fun insertName(name: String) = Placeholder.component("name", Component.text(name))
    fun insertPlayers(players: Set<String>) = Placeholder.component("players", Component.text(players.joinToString()))

}