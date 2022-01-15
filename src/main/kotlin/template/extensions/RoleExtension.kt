package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingBoolean
import com.kotlindiscord.kord.extensions.commands.converters.impl.member
import com.kotlindiscord.kord.extensions.commands.converters.impl.role
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.hasPermission
import com.kotlindiscord.kord.extensions.utils.hasRole
import com.kotlindiscord.kord.extensions.utils.respond
import dev.kord.common.entity.Permission
import template.SERVER_ID

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

        publicSlashCommand(::RoleSlashArgs) {
            name = "role"
            description = "Set or unset a role for a specific user"

            guild(SERVER_ID)

            action {
                val kord = this@RoleExtension.kord

                val realTarget = if (arguments.target.id == kord.selfId) {
                    member
                } else {
                    arguments.target
                }

                if (member?.asMember()?.hasPermission(Permission.ManageRoles) == true) {

                    when (arguments.set) {
                        true -> {
                            if (arguments.target.hasRole(arguments.role)) {
                                respond {
                                    content = "The member ${arguments.target.username} already have the role ${arguments.role.name}, nothing changed"
                                }
                            }
                            arguments.target.addRole(arguments.role.id)
                            respond {
                                content = "The Role ${arguments.role.name} has been successfully added to member: ${arguments.target.username}"
                            }
                        }
                        false -> {
                            if (arguments.target.hasRole(arguments.role)) {
                                arguments.target.removeRole(arguments.role.id)
                                respond {
                                    content = "The Role ${arguments.role.name} has been successfully removed from member: ${arguments.target.username}"
                                }
                            } else {
                                respond {
                                    content = "The member ${arguments.target.username} doesn't have the role ${arguments.role.name}, nothing changed"
                                }
                            }
                        }
                    }

                } else {
                    respond {
                        content = "Sorry, ${member?.asMember()?.username} you don't have the permission to do that."
                    }
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

    inner class RoleSlashArgs(): Arguments() {
        val target by member("target", "The member to set the permissions for.")
        val role by role("TargetRole", "The role ")

        val set by defaultingBoolean(
            "set/unset",

            description = "Should the role be set to true or false",
            defaultValue = true
        )
    }
}