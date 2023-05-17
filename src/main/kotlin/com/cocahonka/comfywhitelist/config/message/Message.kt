package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.config.base.Locale
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import org.bukkit.configuration.file.FileConfiguration

/**
 * Sealed class for managing plugin messages.
 *
 * @property key The key to look up the message in the configuration.
 */
sealed class Message(val key: String) {

    /**
     * Returns the default message for the given locale.
     *
     * @param locale The locale to get the default message for.
     * @return The default message for the specified locale.
     */
    abstract fun getDefault(locale: Locale): Component

    /**
     * Applies styling to the raw message string.
     *
     * @param rawMessage The raw message string.
     * @return The styled message component.
     */
    abstract fun applyStyles(rawMessage: String): Component

    companion object {

        /**
         * A prefix component for all plugin messages.
         */
        private val prefixComponent = Component.text(ComfyWhitelist.DISPLAY_NAME + " > ").color(MessageFormat.Colors.prefix)

        /**
         * Retrieves a message from the configuration and applies styling.
         * If the message is not found, the default message for the specified locale is used.
         *
         * @param M The type of message object that extends [Message].
         * @param message The message object containing the key and default message.
         * @param locale The locale to be used for retrieving the default message if the key is not found in the configuration.
         * @return The styled message component.
         */     
        fun <M : Message> FileConfiguration.getFormattedWithDefault(
            message: M,
            locale: Locale,
        ): Component {
            val rawMessageFromConfig = this.getString(message.key)

            return if (rawMessageFromConfig == null) {
                message.getDefault(locale)
            } else {
                message.applyStyles(rawMessageFromConfig)
            }
        }

        /**
         * Returns the default message for the given locale, prefixed.
         *
         * @param M The type of message object that extends [Message].
         * @param locale The locale to get the default message for.
         * @return The default message for the specified locale, prefixed.
         */
        fun <M : Message> M.getDefaultWithPrefix(locale: Locale) : Component =
            Component.join(
                JoinConfiguration.noSeparators(),
                prefixComponent,
                getDefault(locale)
            )

        /**
         * Joins the prefix with the raw message.
         *
         * @param rawMessage The raw message string.
         * @return The combined message component.
         */
        fun joinWithPrefix(rawMessage: String) =
            Component.join(
                JoinConfiguration.noSeparators(),
                prefixComponent,
                Component.text(rawMessage)
            )

        fun Component.joinWithPrefix() =
            Component.join(
                JoinConfiguration.noSeparators(),
                prefixComponent,
                this
            )

    }

    object NoPermission : Message("no-permission") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "<warning>У вас недостаточно полномочий для использования этой команды.</warning>"
                Locale.EN -> "<warning>You do not have permission to use this command.</warning>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )
    }

    object InvalidUsage : Message("invalid-usage") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "<warning>Недопустимое использование команды.</warning> Используйте: <usage>"
                Locale.EN -> "<warning>Invalid command usage.</warning> Use: <usage>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )
    }

    object UnknownSubcommand : Message("unknown-subcommand") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "<warning>Неизвестная подкоманда.</warning> Введите /comfywl help для отображения доступных подкоманд."
                Locale.EN -> "<warning>Unknown subcommand.</warning> Type /comfywl help for a list of commands."
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )
    }

    object InvalidPlayerName : Message("invalid-player-name") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "<warning>Некорректный формат имени игрока.</warning>"
                Locale.EN -> "<warning>Invalid player name.</warning>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )
    }

    object PluginReloaded : Message("plugin-reloaded") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "ComfyWhitelist <success>успешно перезагружен.</success>"
                Locale.EN -> "ComfyWhitelist <success>has been successfully reloaded.</success>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.success
            )
    }

    object WhitelistEnabled : Message("whitelist-enabled") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "ComfyWhitelist <success>включен.</success>"
                Locale.EN -> "ComfyWhitelist <success>enabled.</success>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.success
            )
    }

    object WhitelistDisabled : Message("whitelist-disabled") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "ComfyWhitelist <off>выключен.</off>"
                Locale.EN -> "ComfyWhitelist <off>disabled.</off>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.off
            )
    }

    object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "ComfyWhitelist <success>уже включен.</success>"
                Locale.EN -> "ComfyWhitelist <success>already enabled.</success>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.success
            )
    }

    object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "ComfyWhitelist <off>уже выключен.</off>"
                Locale.EN -> "ComfyWhitelist <off>already disabled.</off>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.off
            )
    }

    object NotWhitelisted : Message("not-whitelisted") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "<warning>Вы не в вайтлисте.</warning>"
                Locale.EN -> "<warning>You are not whitelisted.</warning>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )

    }

    object PlayerAdded : Message("player-added") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "Игрок <success><name></success> <success>добавлен</success> в вайтлист."
                Locale.EN -> "Player <success><name></success> has been <success>added</success> to the whitelist."
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.success
            )
    }

    object PlayerRemoved : Message("player-removed") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "Игрок <remove><name></remove> <remove>удален</remove> из вайтлиста."
                Locale.EN -> "Player <remove><name></remove> has been <remove>removed</remove> from the whitelist."
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.remove
            )
    }

    object NonExistentPlayerName : Message("non-existent-player-name") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "Игрока с именем <warning><name></warning> <warning>нет</warning> в вайтлисте."
                Locale.EN -> "There is <warning>no</warning> player named <warning><name></warning> in the whitelist."
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.warning
            )
    }

    object WhitelistedPlayersList : Message("whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "Игроки в вайтлисте: <success><players></success>"
                Locale.EN -> "Whitelisted players: <success><players></success>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.success
            )
    }

    object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "В вайтлисте <off>нет игроков.</off>"
                Locale.EN -> "Whitelist is <off>empty.</off>"
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.off
            )
    }

    object WhitelistCleared : Message("whitelist-cleared") {
        override fun getDefault(locale: Locale): Component = applyStyles(
            when (locale) {
                Locale.RU -> "Все игроки <remove>удалены</remove> из вайтлиста."
                Locale.EN -> "All players have been <remove>removed</remove> from the whitelist."
            }
        )

        override fun applyStyles(rawMessage: String): Component =
            MessageFormat.miniMessage.deserialize(
                rawMessage,
                MessageFormat.Resolvers.remove
            )
    }

}
