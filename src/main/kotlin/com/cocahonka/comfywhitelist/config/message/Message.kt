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
                Locale.DE -> "<comfy><warning>Du hast keine Berechtigung, diesen Befehl zu verwenden.</warning>"
                Locale.ES -> "<comfy><warning>No tienes permiso para usar este comando.</warning>"
                Locale.FR -> "<comfy><warning>Vous n'avez pas la permission d'utiliser cette commande.</warning>"
                Locale.IT -> "<comfy><warning>Non hai il permesso di usare questo comando.</warning>"
                Locale.JA -> "<comfy><warning>このコマンドを使用する権限がありません。</warning>"
                Locale.KO -> "<comfy><warning>이 명령어를 사용할 권한이 없습니다.</warning>"
                Locale.NL -> "<comfy><warning>Je hebt geen toestemming om dit commando te gebruiken.</warning>"
                Locale.PT -> "<comfy><warning>Você não tem permissão para usar este comando.</warning>"
                Locale.SV -> "<comfy><warning>Du har inte tillåtelse att använda detta kommando.</warning>"
                Locale.TR -> "<comfy><warning>Bu komutu kullanma yetkiniz yok.</warning>"
                Locale.ZH -> "<comfy><warning>您没有权限使用此命令。</warning>"
                Locale.UK -> "<comfy><warning>У вас немає дозволу використовувати цю команду.</warning>"
                Locale.BE -> "<comfy><warning>У вас няма дазволу карыстацца гэтай камандай.</warning>"
                Locale.KOMI -> "<comfy><warning>Тый сез командаыт корыстны и пырыш адӧмас.</warning>"
                Locale.LOLCAT -> "<comfy><warning>U CANT HAZ PERMISSION 2 USE DIS COMMAND.</warning>"

            }
        )
    }

    object InactiveCommand : Message("inactive-command") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Данная команда <off>выключена</off> через конфиг."
                Locale.EN -> "<comfy>This command is <off>disabled</off> via config."
                Locale.DE -> "<comfy>Dieser Befehl ist <off>deaktiviert</off> via Konfiguration."
                Locale.ES -> "<comfy>Este comando está <off>deshabilitado</off> via config."
                Locale.FR -> "<comfy>Cette commande est <off>désactivée</off> via la configuration."
                Locale.IT -> "<comfy>Questo comando è <off>disabilitato</off> tramite config."
                Locale.JA -> "<comfy>このコマンドは設定で<off>無効化</off>されています。"
                Locale.KO -> "<comfy>이 명령어는 설정을 통해 <off>비활성화</off>되었습니다."
                Locale.NL -> "<comfy>Dit commando is <off>uitgeschakeld</off> via de configuratie."
                Locale.PT -> "<comfy>Este comando está <off>desativado</off> via configuração."
                Locale.SV -> "<comfy>Det här kommandot är <off>inaktiverat</off> via konfigurationen."
                Locale.TR -> "<comfy>Bu komut, yapılandırma üzerinden <off>devre dışı</off>."
                Locale.ZH -> "<comfy>此命令已通过配置<off>禁用</off>。"
                Locale.UK -> "<comfy>Ця команда <off>вимкнена</off> через конфігурацію."
                Locale.BE -> "<comfy>Гэтая каманда <off>адключана</off> праз канфігурацыю."
                Locale.KOMI -> "<comfy>Та команда <off>кытсӧмсь</off> ана конфигурация вара."
                Locale.LOLCAT -> "<comfy>DIS COMMAND IZ <off>OFF</off> VIA CONFIG."
            }
        )
    }

    object InvalidUsage : Message("invalid-usage") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Недопустимое использование команды.</warning>\nИспользуйте: <usage>"
                Locale.EN -> "<comfy><warning>Invalid command usage.</warning>\nUse: <usage>"
                Locale.DE -> "<comfy><warning>Ungültige Befehlsverwendung.</warning>\nVerwende: <usage>"
                Locale.ES -> "<comfy><warning>Uso del comando inválido.</warning>\nUsa: <usage>"
                Locale.FR -> "<comfy><warning>Utilisation de la commande invalide.</warning>\nUtilisez : <usage>"
                Locale.IT -> "<comfy><warning>Utilizzo del comando non valido.</warning>\nUsa: <usage>"
                Locale.JA -> "<comfy><warning>コマンドの使用が無効です。</warning>\n使い方: <usage>"
                Locale.KO -> "<comfy><warning>잘못된 명령어 사용입니다.</warning>\n사용법: <usage>"
                Locale.NL -> "<comfy><warning>Ongeldig commandogebruik.</warning>\nGebruik: <usage>"
                Locale.PT -> "<comfy><warning>Uso inválido do comando.</warning>\nUse: <usage>"
                Locale.SV -> "<comfy><warning>Ogiltig kommandoanvändning.</warning>\nAnvänd: <usage>"
                Locale.TR -> "<comfy><warning>Geçersiz komut kullanımı.</warning>\nKullan: <usage>"
                Locale.ZH -> "<comfy><warning>命令使用无效。</warning>\n使用：<usage>"
                Locale.UK -> "<comfy><warning>Недійсне використання команди.</warning>\nВикористовуйте: <usage>"
                Locale.BE -> "<comfy><warning>Няправільнае выкарыстанне каманды.</warning>\nВыкарыстоўвайце: <usage>"
                Locale.KOMI -> "<comfy><warning>Нырысь команда корыс.</warning>\nКорысӧм: <usage>"
                Locale.LOLCAT -> "<comfy><warning>INVALID COMMAND USAGE.</warning>\nUSE: <usage>"
            }
        )
    }

    object UnknownSubcommand : Message("unknown-subcommand") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Неизвестная подкоманда.</warning> Введите /comfywl help для отображения доступных подкоманд."
                Locale.EN -> "<comfy><warning>Unknown subcommand.</warning> Type /comfywl help for a list of commands."
                Locale.DE -> "<comfy><warning>Unbekanntes Unterbefehl.</warning> Gib /comfywl help für eine Liste der Befehle ein."
                Locale.ES -> "<comfy><warning>Subcomando desconocido.</warning> Escribe /comfywl help para una lista de comandos."
                Locale.FR -> "<comfy><warning>Sous-commande inconnue.</warning> Tapez /comfywl help pour une liste de commandes."
                Locale.IT -> "<comfy><warning>Sottocomando sconosciuto.</warning> Digita /comfywl help per un elenco di comandi."
                Locale.JA -> "<comfy><warning>未知のサブコマンド。</warning> コマンドリストを表示するには /comfywl help を入力します。"
                Locale.KO -> "<comfy><warning>알 수 없는 하위 명령어입니다.</warning> 명령어 목록을 보려면 /comfywl help를 입력하세요."
                Locale.NL ->  "<comfy><warning>Onbekend subcommando.</warning> Typ /comfywl help voor een lijst met commando's."
                Locale.PT -> "<comfy><warning>Subcomando desconhecido.</warning> Digite /comfywl help para uma lista de comandos."
                Locale.SV -> "<comfy><warning>Okänt underkommando.</warning> Skriv /comfywl help för en lista över kommandon."
                Locale.TR -> "<comfy><warning>Bilinmeyen alt komut.</warning> Komut listesi için /comfywl help yazın."
                Locale.ZH -> "<comfy><warning>未知的子命令。</warning>键入/comfywl help以获取命令列表。"
                Locale.UK -> "<comfy><warning>Невідома підкоманда.</warning> Введіть /comfywl help для отримання списку команд."
                Locale.BE -> "<comfy><warning>Невядомая падкаманда.</warning> Напішыце /comfywl help для атрымання спісу каманд."
                Locale.KOMI -> "<comfy><warning>Нырысь пӧдкоманда.</warning> Командаысь списк вара /comfywl помощь тыны керет."
                Locale.LOLCAT -> "<comfy><warning>UNKNOWN SUBCOMMAND.</warning> TYPE /COMFYWL HELP 4 LIST OF COMMANDS."
            }
        )
    }

    object InvalidPlayerName : Message("invalid-player-name") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy><warning>Некорректный формат имени игрока.</warning>"
                Locale.EN -> "<comfy><warning>Invalid player name.</warning>"
                Locale.DE -> "<comfy><warning>Ungültiger Spielername.</warning>"
                Locale.ES -> "<comfy><warning>Nombre de jugador inválido.</warning>"
                Locale.FR -> "<comfy><warning>Nom de joueur invalide.</warning>"
                Locale.IT -> "<comfy><warning>Nome giocatore non valido.</warning>"
                Locale.JA -> "<comfy><warning>プレイヤー名が無効です。</warning>"
                Locale.KO -> "<comfy><warning>잘못된 플레이어 이름입니다.</warning>"
                Locale.NL -> "<comfy><warning>Ongeldige spelersnaam.</warning>"
                Locale.PT -> "<comfy><warning>Nome de jogador inválido.</warning>"
                Locale.SV -> "<comfy><warning>Ogiltigt spelarnamn.</warning>"
                Locale.TR -> "<comfy><warning>Geçersiz oyuncu adı.</warning>"
                Locale.ZH -> "<comfy><warning>玩家名称无效。</warning>"
                Locale.UK -> "<comfy><warning>Недійсне ім'я гравця.</warning>"
                Locale.BE -> "<comfy><warning>Няправільнае імя гульца.</warning>"
                Locale.KOMI -> "<comfy><warning>Нырысь игрокын ним.</warning>"
                Locale.LOLCAT -> "<comfy><warning>BAD PLAYER NAME.</warning>"
            }
        )
    }

    object PluginReloaded : Message("plugin-reloaded") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>успешно перезагружен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>has been successfully reloaded.</success>"
                Locale.DE -> "<comfy>ComfyWhitelist <success>wurde erfolgreich neu geladen.</success>"
                Locale.ES -> "<comfy>ComfyWhitelist <success>se ha recargado con éxito.</success>"
                Locale.FR -> "<comfy>ComfyWhitelist <success>a été rechargé avec succès.</success>"
                Locale.IT -> "<comfy>ComfyWhitelist <success>è stato ricaricato con successo.</success>"
                Locale.JA -> "<comfy>ComfyWhitelist が<success>成功裏にリロードされました。</success>"
                Locale.KO -> "<comfy>ComfyWhitelist가 <success>성공적으로 리로드되었습니다.</success>"
                Locale.NL -> "<comfy>ComfyWhitelist <success>is succesvol herladen.</success>"
                Locale.PT -> "<comfy>ComfyWhitelist <success>foi recarregado com sucesso.</success>"
                Locale.SV -> "<comfy>ComfyWhitelist <success>har laddats om framgångsrikt.</success>"
                Locale.TR -> "<comfy>ComfyWhitelist <success>başarıyla yeniden yüklendi.</success>"
                Locale.ZH -> "<comfy>ComfyWhitelist <success>已成功重新加载。</success>"
                Locale.UK -> "<comfy>ComfyWhitelist <success>успішно перезавантажено.</success>"
                Locale.BE -> "<comfy>ComfyWhitelist <success>паспяхова перазагружана.</success>"
                Locale.KOMI -> "<comfy>ComfyWhitelist <success>успешно перезагруженысь.</success>"
                Locale.LOLCAT -> "<comfy>ComfyWhitelist <success>IZ BACK.</success>"
            }
        )
    }

    object WhitelistEnabled : Message("whitelist-enabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>включен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>enabled.</success>"
                Locale.DE -> "<comfy>ComfyWhitelist <success>aktiviert.</success>"
                Locale.ES -> "<comfy>ComfyWhitelist <success>activada.</success>"
                Locale.FR -> "<comfy>ComfyWhitelist <success>activé.</success>"
                Locale.IT -> "<comfy>ComfyWhitelist <success>abilitato.</success>"
                Locale.JA -> "<comfy>ComfyWhitelist が<success>有効になりました。</success>"
                Locale.KO -> "<comfy>ComfyWhitelist가 <success>활성화되었습니다.</success>"
                Locale.NL -> "<comfy>ComfyWhitelist <success>ingeschakeld.</success>"
                Locale.PT -> "<comfy>ComfyWhitelist <success>ativado.</success>"
                Locale.SV -> "<comfy>ComfyWhitelist <success>aktiverad.</success>"
                Locale.TR -> "<comfy>ComfyWhitelist <success>etkinleştirildi.</success>"
                Locale.ZH -> "<comfy>ComfyWhitelist <success>已启用。</success>"
                Locale.UK -> "<comfy>ComfyWhitelist <success>увімкнено.</success>"
                Locale.BE -> "<comfy>ComfyWhitelist <success>уключана.</success>"
                Locale.KOMI -> "<comfy>ComfyWhitelist <success>включенысь.</success>"
                Locale.LOLCAT -> "<comfy>ComfyWhitelist <success>ON.</success>"
            }
        )
    }

    object WhitelistDisabled : Message("whitelist-disabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <off>выключен.</off>"
                Locale.EN -> "<comfy>ComfyWhitelist <off>disabled.</off>"
                Locale.DE -> "<comfy>ComfyWhitelist <off>deaktiviert.</off>"
                Locale.ES -> "<comfy>ComfyWhitelist <off>deshabilitada.</off>"
                Locale.FR -> "<comfy>ComfyWhitelist <off>désactivé.</off>"
                Locale.IT -> "<comfy>ComfyWhitelist <off>disabilitato.</off>"
                Locale.JA -> "<comfy>ComfyWhitelist が<off>無効化</off>されました。"
                Locale.KO -> "<comfy>ComfyWhitelist가 <off>비활성화</off>되었습니다."
                Locale.NL -> "<comfy>ComfyWhitelist <off>uitgeschakeld.</off>"
                Locale.PT -> "<comfy>ComfyWhitelist <off>desativado.</off>"
                Locale.SV -> "<comfy>ComfyWhitelist <off>inaktiverad.</off>"
                Locale.TR -> "<comfy>ComfyWhitelist <off>devre dışı bırakıldı.</off>"
                Locale.ZH -> "<comfy>ComfyWhitelist <off>已禁用。</off>"
                Locale.UK -> "<comfy>ComfyWhitelist <off>вимкнено.</off>"
                Locale.BE -> "<comfy>ComfyWhitelist <off>адключана.</off>"
                Locale.KOMI -> "<comfy>ComfyWhitelist <off>выключенысь.</off>"
                Locale.LOLCAT -> "<comfy>ComfyWhitelist <off>OFF.</off>"
            }
        )
    }

    object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <success>уже включен.</success>"
                Locale.EN -> "<comfy>ComfyWhitelist <success>already enabled.</success>"
                Locale.DE -> "<comfy>ComfyWhitelist <success>bereits aktiviert.</success>"
                Locale.ES -> "<comfy>ComfyWhitelist <success>ya está activada.</success>"
                Locale.FR -> "<comfy>ComfyWhitelist <success>déjà activé.</success>"
                Locale.IT -> "<comfy>ComfyWhitelist <success>già abilitato.</success>"
                Locale.JA -> "<comfy>ComfyWhitelist は既に<success>有効化</success>されています。"
                Locale.KO -> "<comfy>ComfyWhitelist는 이미 <success>활성화</success>된 상태입니다."
                Locale.NL -> "<comfy>ComfyWhitelist <success>al ingeschakeld.</success>"
                Locale.PT -> "<comfy>ComfyWhitelist <success>já ativado.</success>"
                Locale.SV -> "<comfy>ComfyWhitelist <success>är redan aktiverad.</success>"
                Locale.TR -> "<comfy>ComfyWhitelist <success>zaten etkin.</success>"
                Locale.ZH -> "<comfy>ComfyWhitelist <success>已经启用。</success>"
                Locale.UK -> "<comfy>ComfyWhitelist <success>уже увімкнено.</success>"
                Locale.BE -> "<comfy>ComfyWhitelist <success>ужо уключана.</success>"
                Locale.KOMI -> "<comfy>ComfyWhitelist <success>уже включенысь.</success>"
                Locale.LOLCAT -> "<comfy>ComfyWhitelist <success>ALREADY ON.</success>"
            }
        )
    }

    object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>ComfyWhitelist <off>уже выключен.</off>"
                Locale.EN -> "<comfy>ComfyWhitelist <off>already disabled.</off>"
                Locale.DE -> "<comfy>ComfyWhitelist <off>bereits deaktiviert.</off>"
                Locale.ES -> "<comfy>ComfyWhitelist <off>ya está deshabilitada.</off>"
                Locale.FR -> "<comfy>ComfyWhitelist <off>déjà désactivé.</off>"
                Locale.IT -> "<comfy>ComfyWhitelist <off>già disabilitato.</off>"
                Locale.JA -> "<comfy>ComfyWhitelist は既に<off>無効化</off>されています。"
                Locale.KO -> "<comfy>ComfyWhitelist는 이미 <off>비활성화</off>된 상태입니다."
                Locale.NL -> "<comfy>ComfyWhitelist <off>al uitgeschakeld.</off>"
                Locale.PT -> "<comfy>ComfyWhitelist <off>já desativado.</off>"
                Locale.SV -> "<comfy>ComfyWhitelist <off>är redan inaktiverad.</off>"
                Locale.TR -> "<comfy>ComfyWhitelist <off>zaten devre dışı.</off>"
                Locale.ZH -> "<comfy>ComfyWhitelist <off>已经禁用。</off>"
                Locale.UK -> "<comfy>ComfyWhitelist <off>уже вимкнено.</off>"
                Locale.BE -> "<comfy>ComfyWhitelist <off>ужо адключана.</off>"
                Locale.KOMI -> "<comfy>ComfyWhitelist <off>уже выключенысь.</off>"
                Locale.LOLCAT -> "<comfy>ComfyWhitelist <off>ALREADY OFF.</off>"
            }
        )
    }

    object NotWhitelisted : Message("not-whitelisted") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<warning>Вы не в вайтлисте.</warning>"
                Locale.EN -> "<warning>You are not whitelisted.</warning>"
                Locale.DE -> "<warning>Du bist nicht auf der Whitelist.</warning>"
                Locale.ES -> "<warning>No estás en la lista blanca.</warning>"
                Locale.FR -> "<warning>Vous n'êtes pas sur la liste blanche.</warning>"
                Locale.IT -> "<warning>Non sei sulla whitelist.</warning>"
                Locale.JA -> "<warning>あなたはホワイトリストに登録されていません。</warning>"
                Locale.KO -> "<warning>화이트리스트에 없습니다.</warning>"
                Locale.NL -> "<warning>Je staat niet op de whitelist.</warning>"
                Locale.PT -> "<warning>Você não está na lista de permissões.</warning>"
                Locale.SV -> "<warning>Du finns inte i whitelist.</warning>"
                Locale.TR -> "<warning>Beyaz listeye eklenmemişsiniz.</warning>"
                Locale.ZH -> "<warning>你不在白名单中。</warning>"
                Locale.UK -> "<warning>Ви не в білому списку.</warning>"
                Locale.BE -> "<warning>Вы не ў белым спісе.</warning>"
                Locale.KOMI -> "<warning>Тый та белый списокын нет.</warning>"
                Locale.LOLCAT -> "<warning>U NOT ON WHITELIST.</warning>"
            }
        )
    }

    object PlayerAdded : Message("player-added") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрок <success><name></success> <success>добавлен</success> в вайтлист."
                Locale.EN -> "<comfy>Player <success><name></success> has been <success>added</success> to the whitelist."
                Locale.DE -> "<comfy>Spieler <success><name></success> wurde <success>hinzugefügt</success> zur Whitelist."
                Locale.ES -> "<comfy>Jugador <success><name></success> ha sido <success>agregado</success> a la lista blanca."
                Locale.FR -> "<comfy>Joueur <success><name></success> a été <success>ajouté</success> à la liste blanche."
                Locale.IT -> "<comfy>Giocatore <success><name></success> è stato <success>aggiunto</success> alla whitelist."
                Locale.JA -> "<comfy>プレイヤー <success><name></success> がホワイトリストに<success>追加</success>されました。"
                Locale.KO -> "<comfy>플레이어 <success><name></success>가 화이트리스트에 <success>추가</success>되었습니다."
                Locale.NL -> "<comfy>Speler <success><name></success> is <success>toegevoegd</success> aan de whitelist."
                Locale.PT -> "<comfy>Jogador <success><name></success> foi <success>adicionado</success> à lista de permissões."
                Locale.SV -> "<comfy>Spelaren <success><name></success> har <success>lagts till</success> i whitelist."
                Locale.TR -> "<comfy>Oyuncu <success><name></success>, beyaz listeye <success>eklendi</success>."
                Locale.ZH -> "<comfy>玩家 <success><name></success> 已被<success>添加</success>到白名单中。"
                Locale.UK -> "<comfy>Гравець <success><name></success> <success>додано</success> до білого списку."
                Locale.BE -> "<comfy>Гулец <success><name></success> <success>дададзены</success> да белага спісу."
                Locale.KOMI -> "<comfy>Игрок <success><name></success> белый списокын <success>добавленысь</success>."
                Locale.LOLCAT -> "<comfy>PLAYER <success><name></success> ADDED 2 <success>WHITELIST</success>."
            }
        )
    }

    object PlayerRemoved : Message("player-removed") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрок <remove><name></remove> <remove>удален</remove> из вайтлиста."
                Locale.EN -> "<comfy>Player <remove><name></remove> has been <remove>removed</remove> from the whitelist."
                Locale.DE -> "<comfy>Spieler <remove><name></remove> wurde <remove>entfernt</remove> von der Whitelist."
                Locale.ES -> "<comfy>Jugador <remove><name></remove> ha sido <remove>eliminado</remove> de la lista blanca."
                Locale.FR -> "<comfy>Joueur <remove><name></remove> a été <remove>supprimé</remove> de la liste blanche."
                Locale.IT -> "<comfy>Giocatore <remove><name></remove> è stato <remove>rimosso</remove> dalla whitelist."
                Locale.JA -> "<comfy>プレイヤー <remove><name></remove> がホワイトリストから<remove>削除</remove>されました。"
                Locale.KO -> "<comfy>플레이어 <remove><name></remove>가 화이트리스트에서 <remove>제거</remove>되었습니다."
                Locale.NL -> "<comfy>Speler <remove><name></remove> is <remove>verwijderd</remove> van de whitelist."
                Locale.PT -> "<comfy>Jogador <remove><name></remove> foi <remove>removido</remove> da lista de permissões."
                Locale.SV -> "<comfy>Spelaren <remove><name></remove> har <remove>tagits bort</remove> från whitelist."
                Locale.TR -> "<comfy>Oyuncu <remove><name></remove>, beyaz listeden <remove>çıkarıldı</remove>."
                Locale.ZH -> "<comfy>玩家 <remove><name></remove> 已从白名单中<remove>移除</remove>。"
                Locale.UK -> "<comfy>Гравець <remove><name></remove> <remove>видалено</remove> з білого списку."
                Locale.BE -> "<comfy>Гулец <remove><name></remove> <remove>выдалены</remove> з белага спісу."
                Locale.KOMI -> "<comfy>Игрок <remove><name></remove> белый списокыннун <remove>удаленысь</remove>."
                Locale.LOLCAT -> "<comfy>PLAYER <remove><name></remove> <remove>REMOVED</remove> FROM WHITELIST."
            }
        )
    }

    object NonExistentPlayerName : Message("non-existent-player-name") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игрока с именем <warning><name></warning> <warning>нет</warning> в вайтлисте."
                Locale.EN -> "<comfy>There is <warning>no</warning> player named <warning><name></warning> in the whitelist."
                Locale.DE -> "<comfy>Es gibt <warning>keinen</warning> Spieler namens <warning><name></warning> in der Whitelist."
                Locale.ES -> "<comfy>No hay ningún jugador llamado <warning><name></warning> en la lista blanca."
                Locale.FR -> "<comfy>Il n'y a <warning>pas</warning> de joueur nommé <warning><name></warning> dans la liste blanche."
                Locale.IT -> "<comfy>Non c'è <warning>nessun</warning> giocatore chiamato <warning><name></warning> nella whitelist."
                Locale.JA -> "<comfy>ホワイトリストには <warning><name></warning> という名前のプレイヤーは<warning>存在しません</warning>。"
                Locale.KO -> "<comfy>화이트리스트에 <warning><name></warning>라는 이름의 플레이어는 <warning>존재하지 않습니다</warning>."
                Locale.NL -> "<comfy>Er is <warning>geen</warning> speler met de naam <warning><name></warning> op de whitelist."
                Locale.PT -> "<comfy>Não existe <warning>nenhum</warning> jogador chamado <warning><name></warning> na lista de permissões."
                Locale.SV -> "<comfy>Det finns <warning>ingen</warning> spelare med namnet <warning><name></warning> i whitelist."
                Locale.TR -> "<comfy>Beyaz listede <warning><name></warning> adında bir oyuncu <warning>yok</warning>."
                Locale.ZH -> "<comfy>白名单中没有名为 <warning><name></warning> 的玩家。"
                Locale.UK -> "<comfy>У білому списку <warning>немає</warning> гравця з ім'ям <warning><name></warning>."
                Locale.BE -> "<comfy>У белым спісе <warning>немае</warning> гульца з імем <warning><name></warning>."
                Locale.KOMI -> "<comfy>Белый списокын та <warning><name></warning> нимысь игрок нет."
                Locale.LOLCAT -> "<comfy>NO PLAYER NAMED <warning><name></warning> ON WHITELIST."
            }
        )
    }

    object WhitelistedPlayersList : Message("whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Игроки в вайтлисте: <success><players></success>"
                Locale.EN -> "<comfy>Whitelisted players: <success><players></success>"
                Locale.DE -> "<comfy>Spieler auf der Whitelist: <success><players></success>"
                Locale.ES -> "<comfy>Jugadores en la lista blanca: <success><players></success>"
                Locale.FR -> "<comfy>Joueurs sur liste blanche : <success><players></success>"
                Locale.IT -> "<comfy>Giocatori in whitelist: <success><players></success>"
                Locale.JA -> "<comfy>ホワイトリストのプレイヤー: <success><players></success>"
                Locale.KO -> "<comfy>화이트리스트의 플레이어들: <success><players></success>"
                Locale.NL -> "<comfy>Whitelist spelers: <success><players></success>"
                Locale.PT -> "<comfy>Jogadores na lista de permissões: <success><players></success>"
                Locale.SV -> "<comfy>Spelare i whitelist: <success><players></success>"
                Locale.TR -> "<comfy>Beyaz listeye eklenmiş oyuncular: <success><players></success>"
                Locale.ZH -> "<comfy>白名单中的玩家：<success><players></success>"
                Locale.UK -> "<comfy>Гравці в білому списку: <success><players></success>"
                Locale.BE -> "<comfy>Гульцы ў белым спісе: <success><players></success>"
                Locale.KOMI -> "<comfy>Белый списокысь игрокыс: <success><players></success>"
                Locale.LOLCAT -> "<comfy>PLAYERS ON WHITELIST: <success><players></success>"
            }
        )
    }

    object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>В вайтлисте <off>нет игроков.</off>"
                Locale.EN -> "<comfy>Whitelist is <off>empty.</off>"
                Locale.DE -> "<comfy>Whitelist ist <off>leer.</off>"
                Locale.ES -> "<comfy>La lista blanca está <off>vacía.</off>"
                Locale.FR -> "<comfy>La liste blanche est <off>vide.</off>"
                Locale.IT -> "<comfy>La whitelist è <off>vuota.</off>"
                Locale.JA -> "<comfy>ホワイトリストは<off>空</off>です。"
                Locale.KO -> "<comfy>화이트리스트가 <off>비어있습니다</off>."
                Locale.NL -> "<comfy>Whitelist is <off>leeg.</off>"
                Locale.PT -> "<comfy>A lista de permissões está <off>vazia.</off>"
                Locale.SV -> "<comfy>Whitelist är <off>tom.</off>"
                Locale.TR -> "<comfy>Beyaz liste <off>boş</off>."
                Locale.ZH -> "<comfy>白名单<off>为空。</off>"
                Locale.UK -> "<comfy>Білий список <off>порожній.</off>"
                Locale.BE -> "<comfy>Белы спіс <off>пусты.</off>"
                Locale.KOMI -> "<comfy>Белый список <off>пуст.</off>"
                Locale.LOLCAT -> "<comfy>WHITELIST <off>EMPTY</off>."
            }
        )
    }

    object WhitelistCleared : Message("whitelist-cleared") {
        override fun getDefault(locale: Locale): Component = MessageFormat.applyStyles(
            when (locale) {
                Locale.RU -> "<comfy>Все игроки <remove>удалены</remove> из вайтлиста."
                Locale.EN -> "<comfy>All players have been <remove>removed</remove> from the whitelist."
                Locale.DE -> "<comfy>Alle Spieler wurden <remove>entfernt</remove> von der Whitelist."
                Locale.ES -> "<comfy>Todos los jugadores han sido <remove>eliminados</remove> de la lista blanca."
                Locale.FR -> "<comfy>Tous les joueurs ont été <remove>supprimés</remove> de la liste blanche."
                Locale.IT -> "<comfy>Tutti i giocatori sono stati <remove>rimossi</remove> dalla whitelist."
                Locale.JA -> "<comfy>全プレイヤーがホワイトリストから<remove>削除</remove>されました。"
                Locale.KO -> "<comfy>모든 플레이어가 화이트리스트에서 <remove>제거</remove>되었습니다."
                Locale.NL -> "<comfy>Alle spelers zijn <remove>verwijderd</remove> van de whitelist."
                Locale.PT -> "<comfy>Todos os jogadores foram <remove>removidos</remove> da lista de permissões."
                Locale.SV -> "<comfy>Alla spelare har <remove>tagits bort</remove> från whitelist."
                Locale.TR -> "<comfy>Tüm oyuncular beyaz listeden <remove>kaldırıldı</remove>."
                Locale.ZH -> "<comfy>所有玩家已从白名单中<remove>移除</remove>。"
                Locale.UK -> "<comfy>Всі гравці <remove>видалені</remove> з білого списку."
                Locale.BE -> "<comfy>Усе гульцы <remove>выдаленыя</remove> з белага спісу."
                Locale.KOMI -> "<comfy>Белый списокыннун кышӧдӧй игрокыс <remove>удаленысь</remove>."
                Locale.LOLCAT -> "<comfy>ALL PLAYERS <remove>REMOVED</remove> FROM WHITELIST."
            }
        )
    }

}
