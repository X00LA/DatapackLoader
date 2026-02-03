<p align="center">
   <img width="256" height="256" alt="Chunky Offline Plugin Logo" src="https://private-user-images.githubusercontent.com/5796516/543335092-510acf87-ed67-458d-831d-1f7c6ca15dd6.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3Njk4OTQxMzIsIm5iZiI6MTc2OTg5MzgzMiwicGF0aCI6Ii81Nzk2NTE2LzU0MzMzNTA5Mi01MTBhY2Y4Ny1lZDY3LTQ1OGQtODMxZC0xZjdjNmNhMTVkZDYucG5nP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDEzMSUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAxMzFUMjExMDMyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9OGY4ZTBjZmRhNmE4MDg3NDYyODRlYWRiMzFjZmYyYWMxOWIzNWM0Zjc0NTZkOGJiMmE2M2E3MjM3NjU0MzY4YyZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.WO2rwxj2k2CS9vRJNEFMkD9Sn2_dsazD6Lw1gQwQLkA" />
</p>

<p align="center">
   <a alt="Supports Minecraft 1.21.x" title="Go to Minecraft's Server download site" href="https://www.minecraft.net/en-us/download/server" > <img alt="Supported Minecraft Version" src="https://img.shields.io/badge/Minecraft-1.21.x-69986a" /></a>
    <img alt="GitHub Release (latest)" src="https://img.shields.io/github/v/release/X00LA/ChunkyOfflinePlugin">
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/X00LA/DatapackLoader/total" />
    <img alt="GitHub Downloads (latest)" src="https://img.shields.io/github/downloads/X00LA/DatapackLoader/latest/total">
    <a alt="SpigotMC" title="Go to SpigotMC's download site" href="https://hub.spigotmc.org/jenkins/" >
        <img alt="Supports SpigotMC" src="https://img.shields.io/badge/Supports-SpigotMC-gold?style=flat-square" /></a>
    <a alt="Supports PaperMC" title="Go to PaperMC's download site" href="https://papermc.io/downloads/paper" >
        <img alt="Supports PaperMC" src="https://img.shields.io/badge/Supports-PaperMC-blue" /></a>
    <a alt="Supports Folia" title="Go to Folia's download site" href="https://papermc.io/downloads/folia">
        <img alt="Supports Folia" src="https://img.shields.io/badge/Supports-Folia-green" /></a>
    <a alt="Supports PurPur" title="Go to Purpur's download site" href="https://purpurmc.org/download/purpur">
        <img alt="Supports Purpur" src="https://img.shields.io/badge/Supports-PurPur-a947ff" /></a>
</p>

<p align="center">
    <a alt="Ko-Fi Donation Link" title="Support me on Ko-Fi" href="https://ko-fi.com/Y8Y1RKLT1">
       <img alt="Ko-Fi Donation Banner" src="https://ko-fi.com/img/githubbutton_sm.svg" /></a>
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
