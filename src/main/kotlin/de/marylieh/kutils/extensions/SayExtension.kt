package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.coalescingDefaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.respond
import de.marylieh.kutils.SERVER_ID

class SayExtension : Extension() {

	override val name: String = "say"

	override suspend fun setup() {
		chatCommand(::SayArgs) {
			name = "say"
			description = "Type something the bot should say."

			check { failIf(event.message.author == null) }

			action {
				message.respond(arguments.text)
			}
		}

		publicSlashCommand(::SaySlashArgs) {
			name = "say"
			description = "Type something the bot should say."

			guild(SERVER_ID)

			action {
				respond {
					content = arguments.text
				}
			}
		}
	}

	inner class SayArgs : Arguments() {
		val text by coalescingDefaultingString {
			name = "text"
			description = "Type something the bot should say."

			defaultValue = "I don't know what to say ;-;"
		}
	}

	inner class SaySlashArgs : Arguments() {
		val text by defaultingString {
			name = "text"
			description = "Type something the bot should say."

			defaultValue = "I don't know what to say ;-;"
		}
	}
}
