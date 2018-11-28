package com.robinpel.battlefieldv_statchecker.userinteraction

import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IGuild

object GuildCommunicator {

    @JvmStatic fun sendToGeneral(guild: IGuild, message: String, tts: Boolean = false) {
        sendToChannel(guild.defaultChannel, message, tts)
    }

    @JvmStatic fun sendToGeneral(guild: IGuild, eo: EmbedObject) {
        sendToChannel(guild.defaultChannel, eo)
    }

    @JvmStatic fun sendToChannel(channel: IChannel, message: String, tts: Boolean = false) {
        channel.sendMessage(message, tts)
    }

    @JvmStatic fun sendToChannel(channel: IChannel, eo: EmbedObject) {
        channel.sendMessage(eo)
    }


}