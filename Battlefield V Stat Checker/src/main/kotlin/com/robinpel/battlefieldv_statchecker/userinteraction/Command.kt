package com.robinpel.battlefieldv_statchecker.userinteraction

import com.robinpel.battlefieldv_statchecker.core.Constants.Operation.HEADER
import com.robinpel.battlefieldv_statchecker.core.Constants.Operation.DELIMITER
import com.robinpel.battlefieldv_statchecker.core.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent

class Command(private val event: MessageReceivedEvent) {
    private val invalid: Boolean = (!(event.message.content.startsWith(HEADER, true)))
    private val playerName: String

    init {
        val parts = (event.message.content).split(DELIMITER)

        // Get the given player name
        playerName = if (parts.size > 1) parts[1] else ""
    }

    fun execute() {
        GlobalScope.launch {
            if (invalid) { Logger.err("Command@Coroutine", "Cannot execute") ; return@launch }

            // TODO: Get the HTML
            // TODO: Check if we did not get a 404 back
            // TODO: Respond to the user
        }
    }

}