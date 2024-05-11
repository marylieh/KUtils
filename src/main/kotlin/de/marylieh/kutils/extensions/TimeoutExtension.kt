package de.marylieh.kutils.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingInt
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.utils.TimeoutMembers
import com.kotlindiscord.kord.extensions.utils.hasPermission
import dev.kord.common.entity.GuildFeature
import dev.kord.common.entity.Permission
import dev.kord.core.entity.automoderation.AutoModerationAction

class TimeoutExtension : Extension() {

	override val name: String = "timeout"

	override suspend fun setup() {
		chatCommand(::TimeoutArgs) {
			name = "timeout"
			description = "Timeout a member."

			check { failIf(event.message.author == null) }

			action {
				if (message.getAuthorAsMember().hasPermission(Permission.Administrator)) {
					// TODO: Implement timeout command
				}
			}
		}
	}

	inner class TimeoutArgs : Arguments() {
		val target by defaultingString {
			name = "target"
			description = "The Member you want to timeout."
		}

		val duration by defaultingInt {
			name = "duration"
			description = "The duration for the timeout."

			defaultValue = 10
		}
	}
}
