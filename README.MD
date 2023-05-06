# ComfyWhitelist

ComfyWhitelist is a flexible and user-friendly whitelist plugin for Minecraft Paper servers, designed to work with nicknames instead of UUIDs. This makes it an excellent choice for offline server configurations. The plugin provides an API for easy integration with other plugins, allowing plugin developers to take advantage of its features.

## Key Features

- **Nickname-based Whitelisting**: ComfyWhitelist uses player nicknames for whitelisting, making it compatible with both online and offline Minecraft servers.
- **Customizable Whitelist Storage**: Choose between various storage options for your whitelist, including file-based storage and database storage.
- **API for Plugin Integration**: ComfyWhitelist provides an easy-to-use API for other plugin developers, enabling seamless integration with their projects.
- **Permissions and Commands**: Manage your server's whitelist with simple and intuitive commands, along with permission-based access control for better security.
- **Configurable Settings**: Customize the behavior of the plugin through a configuration file, allowing you to tailor its functionality to your server's needs.

## Installation

To install ComfyWhitelist, simply download the latest release from the GitHub Releases page and place the JAR file in your server's `plugins` folder. Restart your server, and ComfyWhitelist will automatically generate a default configuration file for you to modify as needed.

## Configuration

After installing the plugin, you can find the configuration file `comfywhitelist.yml` in the `plugins/ComfyWhitelist` folder. Modify the settings in this file to customize the plugin's behavior according to your server's requirements.

## API Usage

ComfyWhitelist offers a simple API for other plugin developers to integrate with their projects. The API allows you to interact with the whitelist, add or remove players, and check if a player is whitelisted.

## Support

If you encounter any issues or have questions, please open an issue on the GitHub repository. We'll be happy to assist you.

## License

ComfyWhitelist is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for more information.
