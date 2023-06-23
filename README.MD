# ComfyWhitelist :scroll:

[![Github All Releases](https://img.shields.io/github/downloads/cocahonka/comfy-whitelist/total)]()

ComfyWhitelist is a flexible and user-friendly whitelist plugin for Minecraft Paper servers,
designed to work with nicknames instead of UUIDs. This makes it an excellent choice for offline server configurations.

## Features and Benefits :star2:

- :name_badge: **Nickname-based Whitelisting**: ComfyWhitelist uses player nicknames for whitelisting,
  making it compatible with both online and offline Minecraft servers.
- :wrench: **API for Plugin Integration**: ComfyWhitelist provides an easy-to-use API for other plugin developers,
  enabling seamless integration with their projects.
- :hammer_and_wrench: **Permissions and Commands**: Manage your server's whitelist with simple and intuitive commands,
  along with permission-based access control for better security.
- :control_knobs: **Configurable Settings**: Customize the behavior of the plugin through a configuration file,
  allowing you to tailor its functionality to your server's needs.
- :globe_with_meridians: **Broad Version Support**: ComfyWhitelist is designed to support a wide range of Minecraft versions
  (from 1.13 to 1.20), ensuring usability across different server setups.
- :speaking_head: Multilingual Support: ComfyWhitelist boasts multi-language support. 
  It caters to a broad array of languages including English (**en**), Russian (**ru**), Ukrainian (**uk**), 
  Belarusian (**be**), German (**de**), Spanish (**es**), French (**fr**), Italian (**it**), Japanese (**ja**), Korean (**ko**),
  Dutch (**nl**), Portuguese (**pt**), Swedish (**sv**), Turkish (**tr**), Simplified Chinese (**zh**), Komi (**komi**)
  and LOLCAT (**lolcat**). This feature ensures a comfortable user experience for players around the globe,
  making your server more inclusive and accessible.

## :computer: For Developers

ComfyWhitelist provides a developer-friendly API that can be used to leverage
the plugin's functionalities in your own plugins. You can find more details and
usage examples in our [ComfyWhitelistApi](https://github.com/cocahonka/comfy-whitelist-api).

## :inbox_tray: Installation

To install the plugin, navigate to the 'Releases' section of our GitHub
repository. Here you will find two versions of the plugin for each release:
**standalone** and **lightweight**.

- The '**standalone**' version works right out of the box, you just need to put it in the plugin's folder.
- The '**lightweight**' version requires a separate [Kotlin dependency](https://www.spigotmc.org/resources/kotlin-stdlib.80808/)
to be downloaded and placed in the plugin folder.

The versioning follows this format: MAJOR.MINOR.FIX

The latest release is capable of running on versions `1.13.* - 1.20.*`

## :gear: Configuration

The plugin offers extensive customization options through its configuration
files. You can modify messages, change their colors using tags provided by
the plugin, or any colors from [this site](https://docs.advntr.dev/minimessage/format.html).
The plugin-specific color tags are:
- `<warning>`
- `<success>`
- `<remove>`
- `<off>`

<details><summary><h2>:closed_lock_with_key: Permissions</h2></summary>

ComfyWhitelist provides a broad range of permissions for flexible configuration:
- comfywhitelist.*
- comfywhitelist.comfywhitelist
- comfywhitelist.add
- comfywhitelist.remove
- comfywhitelist.clear
- comfywhitelist.list
- comfywhitelist.on
- comfywhitelist.off
- comfywhitelist.reload
- comfywhitelist.help
</details>

## :building_construction: Building the Project
To build the project, you can use the Gradle menu and go to the 'build' section.
There you will find the tasks '**standalone**' and '**lightweight**'. 
You can also use the console command to build the project:
```bash
./gradlew standalone
# or
./gradlew lightweight
```

## :handshake: Support

If you encounter any issues or have questions, please open an issue on the GitHub repository. 
We'll be happy to assist you.

## :loudspeaker: Contribute

We welcome any contributions to the ComfyWhitelist project. Whether it's
improving the code, adding features, or reporting bugs, every bit helps
in making this plugin better for everyone.

**:star2: Special Request**: We would greatly appreciate any assistance in adapting the project to support
older Minecraft versions!

## :pray: Acknowledgments

A huge thank you to [vmlakk](https://github.com/vmlakk) for their invaluable help in developing
this plugin. Without their guidance, this project wouldn't be what it is today ;)

## :scroll: License

ComfyWhitelist is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE)
file for more information.
