package template.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import dev.kord.common.entity.Permission
import template.SERVER_ID

class InviteExtension : Extension() {
    override val name = "invite"

    override suspend fun setup() {
        chatCommand {
            name = "invite"
            description = "Create an invite link that lasts 24h"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@InviteExtension.kord

                if (message.getAuthorAsMember()?.hasPermission(Permission.CreateInstantInvite) == true) {
                    var invite = kord.rest.channel.createInvite(message.channelId)
                    message.respond("https://discord.gg/${invite.code}")
                }
            }
        }

        publicSlashCommand {
            name = "invite"
            description = "Creates an invite link for the current channel"

            guild(SERVER_ID)

            action {
                val kord = this@InviteExtension.kord

                if (member?.asMember()?.hasPermission(Permission.CreateInstantInvite) == true) {
                    var invite = kord.rest.channel.createInvite(channel.id)
                    respond {
                        content = "https://discord.gg/${invite.code}"
                    }
                }
            }
        }
    }
}