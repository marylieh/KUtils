package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingBoolean
import com.kotlindiscord.kord.extensions.commands.converters.impl.member
import com.kotlindiscord.kord.extensions.commands.converters.impl.role
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.hasRole
import com.kotlindiscord.kord.extensions.utils.respond
import dev.kord.common.entity.Permission

class RoleExtension : Extension() {
    override val name: String = "role"

    override suspend fun setup() {
        chatCommand(::RoleArgs) {
            name = "role"
            description = "Set/unset a role for a specific user"

            check { failIf(event.message.author == null) }

            action {
                val kord = this@RoleExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    message.author!!
                } else {
                    arguments.target
                }

                if (message.getAuthorAsMember()?.hasPermission(Permission.ManageRoles) == true) {

                    when (arguments.set) {
                        true -> {
                            if (arguments.target.hasRole(arguments.role)) {
                                message.respond("The member ${arguments.target.username} already have the role ${arguments.role.name}, nothing changed")
                            }
                            arguments.target.addRole(arguments.role.id)
                            message.respond("The Role ${arguments.role.name} has been successfully added to member: ${arguments.target.username}")
                        }
                        false -> {
                            if (arguments.target.hasRole(arguments.role)) {
                                arguments.target.removeRole(arguments.role.id)
                                message.respond("The Role ${arguments.role.name} has been successfully removed from member: ${arguments.target.username}")
                            } else {
                                message.respond("The member ${arguments.target.username} doesn't have the role ${arguments.role.name}, nothing changed")
                            }
                        }
                    }

                } else {
                    message.respond("Sorry, ${message.author?.username} you don't have the permission to do that.")
                }

            }
        }
    }

    inner class RoleArgs(): Arguments() {
        val target by member("target", "The member to set the permissions for.")
        val role by role("TargetRole", "The role ")

        val set by defaultingBoolean(
            "set/unset",
            "Should the role be set to true or false",

            defaultValue = true
        )
    }

}