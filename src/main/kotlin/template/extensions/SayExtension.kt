package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingCoalescingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.respond
import template.SERVER_ID

class SayExtension : Extension() {
    override val name = "say"

    override suspend fun setup() {
        chatCommand(::SayArgs) {
            name = "say"
            description = "Type something that the bot should say"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@SayExtension.kord

                message.respond(arguments.text)
            }
        }

        publicSlashCommand(::SaySlashArgs) {
            name = "say"
            description = "Type something that the bot should say"

            guild(SERVER_ID)

            action {
                val kord = this@SayExtension.kord

                respond {
                    content = arguments.text
                }
            }
        }
    }

    inner class SayArgs : Arguments() {
        val text by defaultingCoalescingString(
            "TextToSay",

            defaultValue = "I don't know what to say ;-;",
            description = "Type something that the bot should say"
        )
    }

    inner class SaySlashArgs : Arguments() {
        val text by defaultingString(
            "TextToSay",

            defaultValue = "I don't know what to say ;-;",
            description = "Type something that the bot should say"
        )
    }
}