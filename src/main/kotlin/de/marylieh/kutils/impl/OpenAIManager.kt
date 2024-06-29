package de.marylieh.kutils.impl

import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.kotlindiscord.kord.extensions.utils.env
import kotlinx.coroutines.flow.Flow

object OpenAIManager {

	val openai = OpenAI(env("OPENAI_TOKEN"))

	fun generateResponse(request: String) : Flow<ChatCompletionChunk> {
		val chatCompletionRequest = ChatCompletionRequest(
			model = ModelId("gpt-3.5-turbo"),
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

		return openai.chatCompletions(chatCompletionRequest)
	}

	fun formatResponse(responseList: List<String>) : String {
		val resultString = StringBuilder()

		responseList.forEach {
			resultString.append(it)
		}

		return resultString.toString()
	}

}
