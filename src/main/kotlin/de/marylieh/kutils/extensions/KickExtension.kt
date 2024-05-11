package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.coalescingDefaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import de.marylieh.kutils.SERVER_ID
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.MemberBehavior
import kotlinx.serialization.json.internal.decodeToSequenceByReader

class KickExtension : Extension() {

	override val name: String = "kick"

	override suspend fun setup() {
		chatCommand(::KickArgs) {
			name = "kick"
			description = "Kick members from the server."

			check { failIf(event.message.author == null) }

			action {
				if (message.getAuthorAsMember().hasPermission(Permission.Administrator)) {
					MemberBehavior(SERVER_ID, arguments.target.id, this@KickExtension.kord).kick(arguments.reason)

					message.respond("You have kicked the member ${arguments.target.username} with reason ${arguments.reason}")
				} else {
					message.respond("Sorry, you don't have the permission to do that.")
				}
			}
		}

		publicSlashCommand(::KickArgs) {
			name = "kick"
			description = "Kick members from the server."

			guild(SERVER_ID)

			action {
				if (member?.asMember()?.hasPermission(Permission.Administrator) == true) {
					MemberBehavior(SERVER_ID, arguments.target.id, this@KickExtension.kord).kick(arguments.reason)

					respond {
						content = "You have kicked the member ${arguments.target.username} with reason ${arguments.reason}"
					}
				} else {
					respond {
						content = "Sorry, you don't have the permission to do that."
					}
				}
			}
		}
	}

	inner class KickArgs : Arguments() {
		val target by user {
			name = "target"
			description = "The member you want to kick."
		}

		val reason by defaultingString {
			name = "reason"
			description = "Reason for the kick which shows in the Audit Log."

			defaultValue = "Kicked by KUtils, powered by Kotlin. Unspecified Reason."
		}
	}
}
