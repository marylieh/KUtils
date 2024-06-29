package de.marylieh.kutils.impl

import com.aallam.openai.api.chat.*
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.kotlindiscord.kord.extensions.utils.env
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages
import kotlin.time.Duration.Companion.seconds

object OpenAIManager {

	val openai = OpenAI(env("OPENAI_TOKEN"))

	suspend fun generateResponse(request: String) : String {
		val chatCompletionRequest = ChatCompletionRequest(
			model = ModelId("gpt-4o"),
			messages = listOf(
				ChatMessage(
					role = ChatRole.System,
					content = "You are a helpful assistent!"
				),
				ChatMessage(
					role = ChatRole.User,
					content = request
				)
			)
		)

		val completion: ChatCompletion = openai.chatCompletion(chatCompletionRequest)
		return completion.toString()
	}

}
