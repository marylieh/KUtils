package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import de.marylieh.kutils.SERVER_ID
import dev.kord.common.entity.Permission

class InviteExtension : Extension() {

	override val name: String = "invite"

	override suspend fun setup() {
		chatCommand {
			name = "invite"
			description = "Create an invite link that lasts 24h"

			check { failIf(event.message.author == null) }

			val kord = this@InviteExtension.kord

			action {
				if (message.getAuthorAsMember().hasPermission(Permission.CreateInstantInvite)) {
					val invite = kord.rest.channel.createInvite(message.channelId)
					message.respond("https://discord.gg/${invite.code}")
				}
			}
		}

		publicSlashCommand {
			name = "invite"
			description = "Create an invite link that lasts 24h"

			guild(SERVER_ID)

			action {
				val kord = this@InviteExtension.kord

				if (member?.asMember()?.hasPermission(Permission.CreateInstantInvite) == true) {
					val invite = kord.rest.channel.createInvite(channel.id)

					respond {
						content = "https://discord.gg/${invite.code}"
					}
				}
			}
		}
	}
}
