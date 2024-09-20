# UltimateDiscordShop

![Minecraft](https://img.shields.io/badge/Minecraft-1.20.4_--_1.21-brightgreen)
![License](https://img.shields.io/badge/License-CC0-blue.svg)
![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen)

**UltimateDiscordShop** (UDS) is a versatile and customizable plugin for Minecraft 1.20.4 to 1.21 servers, designed to reward your active Discord community with in-game items! With UDS, you can easily manage rewards and create unique in-game experiences for your server members based on their Discord activity.

## Features

- **Discord Integration**: Reward your Discord users with in-game items based on their activity levels.
- **Custom Item Support**: UDS encodes item data in Base64, ensuring that all custom items and their metadata are preserved and functional.
- **Permission-Based Control**: Administrators can use permission-based item distribution to customize who has access to which items, making community engagement more tailored and rewarding.
- **Low Performance Impact**: Optimized to ensure minimal performance impact, so your server runs smoothly even with active Discord integration.

## Getting Started

### Prerequisites

- A Minecraft server running version 1.20.4 to 1.21
- A permission management plugin (such as LuckPerms)
- A Discord server integrated with your Minecraft server

### Installation

1. **Download the Plugin**:  
   Download the latest version of UDS from the [Releases](https://github.com/PizzaThatCodes/UltimateDiscordShop/releases) page.

2. **Install the Plugin**:  
   Place the downloaded `UltimateDiscordShop.jar` file in your server's `plugins` directory.

3. **Restart the Server**:  
   Restart your Minecraft server to load the plugin.

4. **Configure Discord Bot**:  
   Set up the necessary Discord bot and tokens to connect your server to Discord in `discord-settings.yml`.

5. **Configure Database**:  
   Add your database in `database.yml` and select between MySQL or SQLite.

### Configuration

UDS provides a configuration file to customize Discord rewards and item permissions.

1. **Locate the Configuration File**:  
   After the first start, the configuration file will be generated in the `plugins/UltimateDiscordShop` directory.

2. **Edit the Configuration File**:  
   Open the config files with a text editor and configure the settings to your preference, such as `coins-per-message`, `discord embeds`, and `messages`.

Example of `discord-settings.yml`:

```
general:
  discordToken: ""
  guildID: ""

coin-settings:
  startingCoins: 100
  coin-per-message: "Math.floor(Math.random() * (10 - 1 + 1) + 1);" # Could use a formula here, using JavaScript syntax.
  blocked-channels:
    - "123456789012345678"



embeds:
  shop-embed:
    colour: 0x00ff00
    title: "Shop"
    description: "Buy items with your coins!"
    thumbnail: "https://cdn.discordapp.com/attachments/123456789012345678/123456789012345678/coin.png"
  buy-embed:
    colour: 0x00ff00
    title: "Purchase"
    description: "Thank you for your purchase! Here are your items:"
    footer: "Finish of Purchase complete"
    thumbnail: "https://cdn.discordapp.com/attachments/123456789012345678/123456789012345678/coin.png"
  not-enough-coins-embed:
    colour: 0xff0000
    emphemeral: true
    title: "Not Enough Coins"
    description: "You do not have enough coins to purchase this item!"
    footer: "Finish of Not Enough Coins"
    thumbnail: "https://cdn.discordapp.com/attachments/123456789012345678/123456789012345678/coin.png"
  balance-embed:
    colour: 0x00ff00
    title: "Balance"
    description: "Here is the current balance of %user%:"
    footer: "Finish of Balance"
    thumbnail: "https://cdn.discordapp.com/attachments/123456789012345678/123456789012345678/coin.png"
```

### Commands and Permissions

- `/uds <additem:removeitem:reload>` - Manage Discord reward items and reload the plugin configuration.
  - Permission: `ultimateshop.admin`
 
- `/redeem <code>` - Redeem a code
  - Permission `ultimateshop.redeem`

### Default Permissions

- `ultimateshop.admin` - Grants access to add, remove, and reload Discord shop items.
- `ultimateshop.redeem` - Grants access to redeem codes.

## License

This project is licensed - see the [LICENSE](LICENSE) file for details.

## Contact

For support and inquiries, please join my [Discord server](https://discord.gg/pj8auQEPJU) or open an issue on GitHub.

---

Thank you for using UltimateDiscordShop! We hope it enhances the connection between your Minecraft server and Discord community.
