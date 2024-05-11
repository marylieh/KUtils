package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import de.marylieh.kutils.SERVER_ID
import dev.kord.core.behavior.channel.createEmbed

class AvatarExtension : Extension() {
	override val name: String = "avatar"

	override suspend fun setup() {
		chatCommand(::AvatarArgs) {
			name = "avatar"
			description = "Displays the avatar of the target user."

			check { failIf(event.message.author == null) }

			action {
				message.channel.createEmbed {
					this.image = arguments.target.avatar?.cdnUrl?.toUrl()
					this.title = "The avatar of ${arguments.target.username}"
				}
			}
		}

		publicSlashCommand(::AvatarArgs) {
			name = "avatar"
			description = "displays the avatar of the target user."

			guild(SERVER_ID)

			action {
				channel.createEmbed {
					this.image = arguments.target.avatar?.cdnUrl?.toUrl()
					this.title = "The avatar of ${arguments.target.username}"
				}
			}
		}
	}

	inner class AvatarArgs : Arguments() {
		val target by user {
			name = "target"
			description = "Displays the avatar of the target user."
		}
	}
}
