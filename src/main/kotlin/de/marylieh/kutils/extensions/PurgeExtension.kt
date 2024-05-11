package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingInt
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import dev.kord.common.entity.Permission
import kotlinx.coroutines.flow.first

class PurgeExtension : Extension() {

	override val name: String = "purge"

	override suspend fun setup() {
		chatCommand(::PurgeArgs) {
			name = "purge"
			description = "Mass delete messages in the current channel"

			check { failIf(event.message.author == null) }

			action {
				if (message.getAuthorAsMember()?.hasPermission(Permission.ManageMessages) == true) {
					var msg = arguments.numberOfMessages

					while (msg > 0) {

						var msgs = channel.getMessagesBefore(message.id, msg)
						var snowflake = msgs.first().id

						channel.deleteMessage(snowflake, "Deleted by KUtils purge command. Performed by: ${message.author}")

						msg--
					}

					channel.deleteMessage(message.id, "Deleted by KUtils purge command. Performed by: ${message.author}")
				}
			}
		}
	}

	inner class PurgeArgs : Arguments() {
		val numberOfMessages by defaultingInt {
			name = "numberOfMessagesToDelete"
			description = "Mass delete messages in the current channel"

			defaultValue = 10
		}
	}
}
