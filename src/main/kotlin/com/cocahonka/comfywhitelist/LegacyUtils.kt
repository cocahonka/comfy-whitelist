package com.cocahonka.comfywhitelist

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender

/**
 * A utility object that provides helper methods for dealing with legacy components.
 */
object LegacyUtils {

    /**
     * The legacy section serializer used to convert Components to their legacy format.
     */
    private val legacySection = LegacyComponentSerializer.legacySection()

    /**
     * Sends a message to the CommandSender. The message is a Component that is serialized to its legacy format.
     *
     * @param component The Component to be serialized and sent as a message.
     */
    fun CommandSender.sendMessage(component: Component) {
        sendMessage(legacySection.serialize(component))
    }

    /**
     * Converts a Component to its legacy text format.
     *
     * @return The serialized text of the Component in its legacy format.
     */
    fun Component.toLegacyText() = legacySection.serialize(this)
}