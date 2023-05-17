package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.ComfyWhitelist
import com.cocahonka.comfywhitelist.config.base.Locale
import net.kyori.adventure.text.Component
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

    companion object {

        /**
         * A prefix component for all plugin messages.
         */
        val prefixComponent = Component.text(ComfyWhitelist.DISPLAY_NAME + " > ").color(MessageFormat.Colors.prefix)

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
                MessageFormat.applyStyles(rawMessageFromConfig)
            }
        }

    }

    object NoPermission : Message("no-permission") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>У вас недостаточно полномочий для использования этой команды.</warning>"
                Locale.EN -> "<comfy><warning>You do not have permission to use this command.</warning>"
            }
        )
    }

    object InvalidUsage : Message("invalid-usage") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Недопустимое использование команды.</warning> Используйте: <usage>"
                Locale.EN -> "<comfy><warning>Invalid command usage.</warning> Use: <usage>"
            }
        )
    }

    object UnknownSubcommand : Message("unknown-subcommand") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Неизвестная подкоманда.</warning> Введите /comfywl help для отображения доступных подкоманд."
                Locale.EN -> "<comfy><warning>Unknown subcommand.</warning> Type /comfywl help for a list of commands."
            }
        )
    }

    object InvalidPlayerName : Message("invalid-player-name") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Некорректный формат имени игрока.</warning>"
                Locale.EN -> "<comfy><warning>Invalid player name.</warning>"
            }
        )
    }

    object PluginReloaded : Message("plugin-reloaded") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>успешно перезагружен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>has been successfully reloaded.</success>"
            }
        )
    }

    object WhitelistEnabled : Message("whitelist-enabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>включен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>enabled.</success>"
            }
        )
    }

    object WhitelistDisabled : Message("whitelist-disabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <off>выключен.</off>"
                Locale.EN -> "<comfy>ComfyWhitelist <off>disabled.</off>"
            }
        )
    }

    object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>уже включен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>already enabled.</success>"
            }
        )
    }

    object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <off>уже выключен.</off>"
                Locale.EN -> "<comfy>ComfyWhitelist <off>already disabled.</off>"
            }
        )
    }

    object NotWhitelisted : Message("not-whitelisted") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<off>Вы не в вайтлисте.</off>"
                Locale.EN -> "<off>You are not whitelisted.</off>"
            }
        )
    }

    object PlayerAdded : Message("player-added") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрок <success><name></success> <success>добавлен</success> в вайтлист."
                Locale.EN -> "<comfy>Player <success><name></success> has been <success>added</success> to the whitelist."
            }
        )
    }

    object PlayerRemoved : Message("player-removed") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрок <remove><name></remove> <remove>удален</remove> из вайтлиста."
                Locale.EN -> "<comfy>Player <remove><name></remove> has been <remove>removed</remove> from the whitelist."
            }
        )
    }

    object NonExistentPlayerName : Message("non-existent-player-name") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрока с именем <warning><name></warning> <warning>нет</warning> в вайтлисте."
                Locale.EN -> "<comfy>There is <warning>no</warning> player named <warning><name></warning> in the whitelist."
            }
        )
    }

    object WhitelistedPlayersList : Message("whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игроки в вайтлисте: <success><players></success>"
                Locale.EN -> "<comfy>Whitelisted players: <success><players></success>"
            }
        )
    }

    object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>В вайтлисте <off>нет игроков.</off>"
                Locale.EN -> "<comfy>Whitelist is <off>empty.</off>"
            }
        )
    }

    object WhitelistCleared : Message("whitelist-cleared") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Все игроки <remove>удалены</remove> из вайтлиста."
                Locale.EN -> "<comfy>All players have been <remove>removed</remove> from the whitelist."
            }
        )
    }

}
