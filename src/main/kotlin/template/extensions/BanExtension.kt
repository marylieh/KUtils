package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingInt
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.member
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.ban
import template.SERVER_ID

class BanExtension : Extension() {
    override val name = "ban"

    override suspend fun setup() {
        chatCommand(::BanArgs) {
            name = "ban"
            description = "Ban members from the server"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@BanExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    message.author!!
                } else {
                    arguments.target
                }

                if (message.getAuthorAsMember()?.hasPermission(Permission.Administrator) == true) {
                    arguments.target.ban {
                        this.reason = arguments.reason
                        this.deleteMessagesDays = arguments.deleteMessagesByDays
                    }
                    message.respond("You have banned the member ${arguments.target.username} with reason ${arguments.reason}")
                } else {
                    message.respond("Sorry, you don't have the permission to do that")
                }
            }
        }

        publicSlashCommand(::BanSlashArgs) {
            name = "ban"
            description = "Ban members from the server"

            guild(SERVER_ID)

            action {
                val kord = this@BanExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    member
                } else {
                    arguments.target
                }

                if (member?.asMember()?.hasPermission(Permission.Administrator) == true) {
                    arguments.target.ban {
                        this.reason = arguments.reason
                        this.deleteMessagesDays = arguments.deleteMessageByDays
                    }
                    respond {
                        content = "You have banned the member ${arguments.target.username} with reason ${arguments.reason}"
                    }
                }
            }
        }
    }

    inner class BanArgs() : Arguments() {
        val target by member("target", "Person you want to ban")

        val reason by defaultingString(
            "reason",

            defaultValue = "Banned by KUtils, powered by Kotlin. Unspecified Reason.",
            description = "Reason for ban"
        )

        val deleteMessagesByDays by defaultingInt(
            "deleteMessagesByDays",

            defaultValue = 7,
            description = "Deleted messages in days"
        )
    }

    inner class BanSlashArgs() : Arguments() {
        val target by member("target", "Person you want to ban")

        val reason by defaultingString("reason",

            defaultValue = "Banned by KUtils, powered by Kotlin. Unspecified Reason.",
            description = "Reason for ban"
            )

        val deleteMessageByDays by defaultingInt(
            "deleteMessagesByDays",

            defaultValue = 7,
            description = "Deleted messages in days (default: 7)"
        )
    }
}