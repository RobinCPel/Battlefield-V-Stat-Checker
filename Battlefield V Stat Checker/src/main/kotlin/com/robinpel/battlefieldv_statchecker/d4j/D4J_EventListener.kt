package com.robinpel.battlefieldv_statchecker.d4j

import com.robinpel.battlefieldv_statchecker.core.Logger
import com.robinpel.battlefieldv_statchecker.userinteraction.Command
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.shard.LoginEvent

class D4J_EventListener {

    @EventSubscriber fun onLoginEvent(event: LoginEvent) {
        Logger.log(this.toString(), "Logged in")
        event.client.changePlayingText("!bfvstats help")
    }

    @EventSubscriber fun onReadyEvent(event: ReadyEvent) {
        Logger.log(this.toString(), "Ready")
        Logger.log(this.toString(), "Joined ${event.client.guilds.size} Guilds")
    }

    @EventSubscriber fun onMessageReceivedEvent(event: MessageReceivedEvent) {
        if (event.author.isBot) return      // ignore bot messages
        Logger.log(this.toString(), "New Message \"${event.message}\"")
        Thread(Command(event)).start()
    }
}
