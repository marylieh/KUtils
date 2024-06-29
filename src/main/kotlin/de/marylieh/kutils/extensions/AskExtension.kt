package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.coalescingDefaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.respond
import de.marylieh.kutils.SERVER_ID
import de.marylieh.kutils.impl.OpenAIManager

class AskExtension : Extension() {

	override val name = "ask"

	override suspend fun setup() {
		chatCommand(::AskArgs) {
			name = "ask"
			description = "Ask OpenAI's LLM gpt4-o everything you would like to know. By using this feature you agree with OpenAI's privacy policy."

			check { failIf(event.message.author == null) }

			action {
				message.respond(OpenAIManager.generateResponse(arguments.text))
			}
		}

		publicSlashCommand(::AskSlashArgs) {
			name = "ask"
			description = "Ask OpenAI's LLM gpt4-o everything you would like to know. By using this feature you agree with OpenAI's privacy policy."

			guild(SERVER_ID)

			action {
				respond {
					content = OpenAIManager.generateResponse(arguments.text)
				}
			}
		}
	}

	inner class AskArgs : Arguments() {
		val text by coalescingDefaultingString {
			name = "question"
			description = "Ask the bot something, responses are generated by OpenAI LLM. By using this feature you agree with OpenAI's privacy policy."

			defaultValue = "Who are you?"
		}
	}

	inner class AskSlashArgs : Arguments() {
		val text by defaultingString {
			name = "question"
			description = "Ask the bot something, responses are generated by OpenAI LLM. By using this feature you agree with OpenAI's privacy policy."

			defaultValue = "Who are you?"
		}
	}
}
