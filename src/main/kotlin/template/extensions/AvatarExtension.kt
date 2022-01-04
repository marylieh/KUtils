package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.member
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import dev.kord.core.behavior.channel.createEmbed
import template.SERVER_ID

class AvatarExtension : Extension() {
    override val name = "avatar"

    override suspend fun setup() {
        chatCommand(::AvatarSlashArguments) {
            name = "avatar"
            description = "Displays the avatar from a user"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@AvatarExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    message.author!!
                } else {
                    arguments.target
                }

                message.channel.createEmbed {
                    this.image = arguments.target.avatar?.url
                    this.title = "The Avatar of the user ${arguments.target.username}"
                }
            }
        }

        publicSlashCommand(::AvatarSlashArguments) {
            name = "avatar"
            description = "Displays the avatar from a user"

            guild(SERVER_ID)

            action {
                val kord = this@AvatarExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    member
                } else {
                    arguments.target
                }

                channel.createEmbed {
                    this.image = arguments.target.avatar?.url
                    this.title = "The Avatar of the user ${arguments.target.username}"
                }
            }
        }
    }

    inner class AvatarSlashArguments() : Arguments() {
        val target by member("target", "Displays the targets avatar")
    }
}