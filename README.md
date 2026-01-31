<p align="center">
   <a alt="Supports Minecraft 1.21.11" title="Go to Minecraft's Server download site" href="https://www.minecraft.net/en-us/download/server" > <img alt="Supported Minecraft Version" src="https://img.shields.io/badge/Minecraft-1.21.11-69986a" /></a>
    <img alt="GitHub Release" src="https://img.shields.io/github/v/release/X00LA/datapackloader" />
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/X00LA/datapackloader/total">
    <a alt="SpigotMC" title="Go to SpigotMC's download site" href="https://hub.spigotmc.org/jenkins/" >
        <img alt="Supports SpigotMC" src="https://img.shields.io/badge/Supports-SpigotMC-gold?style=flat-square" /></a>
    <a alt="Supports PaperMC" title="Go to PaperMC's download site" href="https://papermc.io/downloads/paper" >
        <img alt="Supports PaperMC" src="https://img.shields.io/badge/Supports-PaperMC-blue" /></a>
    <a alt="Supports Folia" title="Go to Folia's download site" href="https://papermc.io/downloads/folia">
        <img alt="Supports Folia" src="https://img.shields.io/badge/Supports-Folia-green" /></a>
    <a alt="Supports PurPur" title="Go to Purpur's download site" href="https://purpurmc.org/download/purpur">
        <img alt="" src="https://img.shields.io/badge/Supports-PurPur-a947ff" /></a>
</p>

Original Plugin from lichenaut ([Codeberg](https://codeberg.org/lichenaut/DatapackLoader) | [Github](https://github.com/lichenaut/DatapackLoader)).

I adapted and updated the plugin to be compatible with the latest Minecraft Version. Detailed informations about the changes I made are further down in the changelog section of this file.

# DatapackLoader

DatapackLoader automatically adds datapacks to your Minecraft server! This plugin can be used to install datapacks and quicken datapack development. DatapackLoader supports Folia, but does not make Folia-incompatible datapacks compatible with Folia.

## Plugin Installation

1. Download plugin from [latest releases](https://github.com/X00LA/DatapackLoader/releases).  
2. Stop your server.  
3. Put the datapackloader-X.X.X.jar in your server's plugins folder.  
   3-1. If you update from previous releases of datapackloader, please delete the old datapackloader folder. :warning: ***Don't forget to backup your datapacks!*** :warning:  
4. Start the server.  
5. Now the plugin generates a new folder, config and language files.  
6. You can change the language in the config file. Currently only english and german are implemented. If you want your language to be in datapackloader, feel free to use the english file as template and create a pull request.  
7. If you made any changes type /datapackloder reload in your console and reload the config and language files.  
8. Don't forget to set the corresponding permissions with your preferred permission manager plugin. (I recommend [LuckPerms](https://luckperms.net/)) Per default most permissions are only accessible with op status.  
9. Now you can either use /datapackloader import <url> to download a datapack directly on your server or you download it yourself and put it into the plugin's datapack folder inside the plugin's folder.  
10. Now reload the plugin and you're ready to go!  


## Methods

### There are three methods for adding datapacks:

    - Pasting a URL into the '/datapackloader import <url> or /dpl import <url>' console command.
    - Dragging and dropping by hand into the plugin's 'datapacks' folder.
    - Enabling 'starter-datapack' in 'config.yml'.

## Commands

```
commands:
    /datapackloader help:
      description: "Displays the datapackloader help."
    /datapackloader import <url>
      description: "Allows the user to import a datapack via download."
    /datapackloader reload:
      description: "Allows the user to reload the plugin after changes in the config or language files."
```

## Permissions

```
permissions:
  datapackloader.*:
    description: "Grants access to all DatapackLoader commands"
    default: op
    children:
      datapackloader.command: true
      datapackloader.command.help: true
      datapackloader.command.import: true
      datapackloader.command.reload: true
  datapackloader.command:
    description: "Allows using the base /datapackloader command"
    default: op
  datapackloader.command.help:
    description: "Allows using /datapackloader help"
    default: true
  datapackloader.command.import:
    description: "Allows using /datapackloader import to add datapacks"
    default: op
  datapackloader.command.reload:
    description: "Allows using /datapackloader reload to reload config and languages"
    default: op
```

## Changelog

```
2026.01.31:
    - Updated the code to be compatible with Minecraft 1.21.11 as well with Paper/Spigot/Purpur and Folia support.
    - Cleaned up the code and updated the dependencies.
    - Completely removed unnecessary dependencies.
    - Removed log4j and replaced it with java.util.logging.
    - Completely removed the teleport feature.
    - Completely removed bStats.
    - Reworked the commands and permissions system.
    - Added Multilanguage support. Currently only english and german are implemented. Feel free to submit your translations.
```
