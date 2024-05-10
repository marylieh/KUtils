/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package de.marylieh.kutils

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.Snowflake
import de.marylieh.kutils.extensions.TestExtension
import de.marylieh.kutils.extensions.SayExtension
import de.marylieh.kutils.extensions.AvatarExtension

val TEST_SERVER_ID = Snowflake(
	env("TEST_SERVER").toLong()  // Get the test server ID from the env vars or a .env file
)

private val TOKEN = env("TOKEN")   // Get the bot' token from the env vars or a .env file

suspend fun main() {
	val bot = ExtensibleBot(TOKEN) {
		chatCommands {
			defaultPrefix = "?"
			enabled = true

			prefix { default ->
				if (guildId == TEST_SERVER_ID) {
					// For the test server, we use ! as the command prefix
					"?"
				} else {
					// For other servers, we use the configured default prefix
					default
				}
			}
		}

		extensions {
			add(::TestExtension)
			add(::SayExtension)
			add(::AvatarExtension)
		}
	}

	bot.start()
}