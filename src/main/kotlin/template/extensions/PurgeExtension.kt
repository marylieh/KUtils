package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingInt
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import kotlinx.coroutines.flow.first

class PurgeExtension : Extension() {
    override val name: String = "purge"

    var flakes: ArrayList<Snowflake> = ArrayList<Snowflake>()

    override suspend fun setup() {
        chatCommand(::PurgeArgs) {
            name = "purge"
            description = "Deletes messages in the current channel"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@PurgeExtension.kord

                if (message.getAuthorAsMember()?.hasPermission(Permission.ManageMessages) == true) {
                    var msg = arguments.numberOfMessages

                    while (msg > 0) {

                        var msgs = channel.getMessagesBefore(message.id, msg)
                        var snowflake = msgs.first().id

                        channel.deleteMessage(snowflake, "Deleted by KUtils purge command. Performed from: ${message.author}")

                        msg--
                    }

                    channel.deleteMessage(message.id, "Deleted by KUtils purge command. Performed from: ${message.author}")
                }
            }
        }
    }

    inner class PurgeArgs() : Arguments() {
        val numberOfMessages by defaultingInt(
            "NumberOfMessagesToDelete",

            defaultValue = 10,
            description = "Deletes messages in current channel"
        )
    }
}