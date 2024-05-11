package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.respond
import de.marylieh.kutils.SERVER_ID
import dev.kord.common.entity.Permission
import dev.kord.core.cache.data.BanData
import dev.kord.core.entity.Ban

class BanExtension : Extension() {

	override val name: String = "ban"

	override suspend fun setup() {
		chatCommand(::BanArgs) {
			name = "ban"
			description = "Ban members from the server"

			check { failIf(event.message.author == null) }

			action {
				val kord = this@BanExtension.kord

				if (message.getAuthorAsMember().hasPermission(Permission.Administrator)) {
					Ban(BanData(arguments.reason, arguments.target.id, SERVER_ID), kord, kord.defaultSupplier)

					message.respond("You have banned the member ${arguments.target.username} with reason ${arguments.reason}.")
				} else {
					message.respond("Sorry, you don't have the permission to do that.")
				}
			}
		}

		publicSlashCommand(::BanArgs) {
			name = "ban"
			description = "Ban members from the server"

			guild(SERVER_ID)

			action {
				val kord = this@BanExtension.kord

				if (member?.asMember()?.hasPermission(Permission.Administrator) == true) {
					Ban(BanData(arguments.reason, arguments.target.id, SERVER_ID), kord, kord.defaultSupplier)

					respond {
						content = "You have banned the member ${arguments.target.username} with reason ${arguments.reason}."
					}
				} else {
					respond {
						content = "Sorry, you don't have the permission to do that."
					}
				}
			}
		}
	}

	inner class BanArgs : Arguments() {
		val target by user {
			name = "target"
			description = "The user you want to ban."
		}

		val reason by defaultingString {
			name = "reason"

			defaultValue = "Banned by KUtils, powered by Kotlin. Unspecified Reason."
			description = "Reason for ban."
		}
	}
}
