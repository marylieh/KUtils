package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingCoalescingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.member
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import dev.kord.common.entity.Permission
import template.SERVER_ID

class KickExtension : Extension() {
    override val name = "kick"

    override suspend fun setup() {
        chatCommand(::KickArgs) {
            name = "kick"
            description = "Kick members from the server"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@KickExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    message.author!!
                } else {
                    arguments.target
                }

                if (message.getAuthorAsMember()?.hasPermission(Permission.Administrator) == true) {
                    arguments.target.kick(arguments.reason)

                    message.respond("You have kicked the member ${arguments.target.username} with reason ${arguments.reason}")
                } else {
                    message.respond("Sorry, you don't have the permission to do that")
                }
            }
        }

        publicSlashCommand(::KickSlashArgs) {
            name = "kick"
            description = "Kick members from the server"

            guild(SERVER_ID)

            action {
                val kord = this@KickExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    member
                } else {
                    arguments.target
                }

                println(member?.asMember()?.username)

                if (member?.asMember()?.hasPermission(Permission.Administrator) == true) {
                    arguments.target.kick(arguments.reason)

                    respond {
                        content = "You have kicked the member ${arguments.target.username} with reason ${arguments.reason}"
                    }
                } else {

                    respond {
                        content = "Sorry ${member?.asMember()?.username}, you don't have the permission to do that"
                    }
                }
            }
        }
    }

    inner class KickArgs() : Arguments() {
        val target by member("target", description = "Person you want to kick")

        val reason by defaultingCoalescingString(
            "reason",

            defaultValue = "Kicked by KUtils, powered by Kotlin. Unspecified Reason.",
            description = "Reason for kick"
        )
    }

    inner class KickSlashArgs() : Arguments() {
        val target by member("target", description = "Person you want to kick")

        val reason by defaultingString(
            "reason",

            defaultValue = "Kicked by KUtils, powered by Kotlin. Unspecified Reason.",
            description = "Reason for kick"
        )
    }
}