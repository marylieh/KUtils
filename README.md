# KUtils Discord Bot

This repository contains the code for the KUtils discord bot. This is a very basic bot to moderate and control
your discord Server.

## Features
KUtils prefix is `!`. For most commands a `/` command is also provided.

The KUtils bot has the following features:
* avatar - displays the avatar of the selected user in an embed message
* ban - members with the `ban` permission can ban users with this command easily.
* kick - members with the `kick` permission can kick users easily with this command.
* invite - selected members can create invites in the current text channel.
* purge - this command can bulk delete messages.
* role - this command is work in progress.
* say - with this simple command members can write something that the bot should say.
## Bundled Bot

* `App.kt` includes a basic bot which uses environment variables (or variables in a `.env` file) for the testing guild
  ID (`TEST_SERVER`) and the bot's token (`TOKEN`). You can specify these either directly as environment variables, or
  as `KEY=value` pairs in a file named `.env`. This file also includes some example code that shows one potential way 
  of providing different command prefixes for different servers.
* `TestExtension.kt` includes a simple example extension that creates a `slap` command. This command works as both a
  message command and slash command, and allows you to slap other users with whatever you wish, defaulting to a
  `large, smelly trout`.

To test the bot, we recommend using a `.env` file that looks like the following:

```dotenv
TOKEN=abc...
TEST_SERVER=123...
```

Create this file, fill it out, and run the `run` gradle task for testing in development.
