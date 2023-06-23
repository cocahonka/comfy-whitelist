package com.cocahonka.comfywhitelist.config.base

import org.bukkit.Bukkit.getLogger

/**
 * Enum class that represents a supported locale.
 * @param code The locale code as a string.
 * @param filePath The path to the configuration file for the locale.
 */
enum class Locale(val code: String, val filePath: String) {
    RU("ru", "locales/messages_ru.yml"),
    EN("en", "locales/messages_en.yml"),
    DE("de", "locales/messages_de.yml"),
    ES("es", "locales/messages_es.yml"),
    FR("fr", "locales/messages_fr.yml"),
    IT("it", "locales/messages_it.yml"),
    JA("ja", "locales/messages_ja.yml"),
    KO("ko", "locales/messages_ko.yml"),
    NL("nl", "locales/messages_nl.yml"),
    PT("pt", "locales/messages_pt.yml"),
    SV("sv", "locales/messages_sv.yml"),
    TR("tr", "locales/messages_tr.yml"),
    ZH("zh", "locales/messages_zh.yml"),
    UK("uk", "locales/messages_uk.yml"),
    BE("be", "locales/messages_be.yml"),

    // KEKW
    KOMI("komi", "locales/messages_komi.yml"),
    LOLCAT("lolcat", "locales/messages_lolcat.yml");

    companion object {

        /**
         * Retrieves the Locale object based on the provided value.
         * If no matching locale is found, the default English locale is returned.
         * @param value The value to find a matching Locale object for.
         * @return The matching Locale object or the default English locale if not found.
         */
        fun fromString(value: String?): Locale {
            val locale = values().find { it.name.equals(value, true) }
            return if (locale == null){
                getLogger().warning("locale '$value' does not exist!\n" +
                        "so the english locale will be loaded")
                EN
            } else {
                locale
            }
        }
    }
}
