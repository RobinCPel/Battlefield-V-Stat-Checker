package com.robinpel.battlefieldv_statchecker.userinteraction

import com.robinpel.battlefieldv_statchecker.core.BattlefieldV_StatRetriever
import com.robinpel.battlefieldv_statchecker.core.Constants
import com.robinpel.battlefieldv_statchecker.core.Constants.Operation.HEADER
import com.robinpel.battlefieldv_statchecker.core.Constants.Operation.DELIMITER
import com.robinpel.battlefieldv_statchecker.core.Logger
import com.robinpel.battlefieldv_statchecker.core.Platform
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.Embed

class Command(private val event: MessageReceivedEvent): Runnable {
    private val invalid: Boolean = (!(event.message.content.startsWith(HEADER, true)))
    private val playerName: String
    private val platform: Platform?
    private val statRetriever = BattlefieldV_StatRetriever()

    init {
        val parts = (event.message.content).split(DELIMITER)

        if (parts.size < 3) {
            platform = null
            playerName = ""
        }
        else {
            // Get the given platform & player name
            platform = when(parts[1].toLowerCase()) {
                "origin" -> Platform.Origin
                "psn" -> Platform.PSN
                "xbl" -> Platform.XBL
                else -> null
            }

            playerName = parts[2]
        }
    }

    override fun run() {
        execute()
    }

    private fun execute() {
        if (invalid) { Logger.debug("Command@Thread", "Invalid command detected") ; return }
        Logger.log("Command@Thread", "Valid command detected")
        event.channel.typingStatus = true

        try {
            if (platform != null)   printUserStats()
            else                    printExtraHelp()
        }
        catch (e: java.lang.Exception) { e.printStackTrace() }
        finally { event.channel.typingStatus = false }
    }

    private fun printUserStats() {
        try {
            val stats = statRetriever.getStats(playerName, platform!!)

            // Check whether something went wrong or not
            if (stats.isEmpty()) { GuildCommunicator.sendToChannel(event.channel, "No stats found.") ; return }
            if (stats[0] == "ERR") { GuildCommunicator.sendToChannel(event.channel, "Could not retrieve the stats.") ; return }

            val embedObject = embedData(stats)
            GuildCommunicator.sendToChannel(event.channel, embedObject)
        }
        catch (e: Exception) { Logger.err(this.toString(), "An error occurred while getting the stats") }
    }

    private fun embedData(data: List<String>): EmbedObject {
        val embedObject = EmbedObject()

        try {
            embedObject.title = playerName
            embedObject.description = "Battlefield V"
            embedObject.url = platform!!.value + playerName + Constants.BFV.ADDRESS_SUFFIX
            embedObject.thumbnail = EmbedObject.ThumbnailObject(Constants.Address.BFV_ICON, Constants.Address.BFV_ICON, 10, 10)
            embedObject.author = EmbedObject.AuthorObject("Battlefield V Stat Checker", Constants.Address.GIT_REPO, Constants.Address.BOT_ICON, Constants.Address.BOT_ICON)
            embedObject.color = 0x6B81CA
            embedObject.footer = EmbedObject.FooterObject("Battlefieldtracker.com", Constants.Address.BFT_SITE, Constants.Address.BFT_SITE)

            if (data.size < 36) {
                embedObject.fields = arrayOf(EmbedObject.EmbedFieldObject("Insufficient data, the user needs at least one of every stat listed below", "kill, death, finished match, assist, heal, revive, resupply", false))
                return embedObject
            }

            //var embeddedFields = EmbedObject.EmbedFieldObject()[]
            var embeddedObjectList = arrayOf(
                EmbedObject.EmbedFieldObject(data[0],  "${data[ 1]} (${data[ 2]})", true),
                EmbedObject.EmbedFieldObject(data[3],  "${data[ 4]} (${data[ 5]})", true),
                EmbedObject.EmbedFieldObject(data[6],  "${data[ 7]} (${data[ 8]})", true),
                EmbedObject.EmbedFieldObject(data[9],  "${data[10]} (${data[11]})", true),
                EmbedObject.EmbedFieldObject(data[12], "${data[13]} (${data[14]})", true),
                EmbedObject.EmbedFieldObject(data[15], "${data[16]} (${data[17]})", true),
                EmbedObject.EmbedFieldObject(data[18], "${data[19]} (${data[20]})", true),
                EmbedObject.EmbedFieldObject(data[21], "${data[22]} (${data[23]})", true),
                EmbedObject.EmbedFieldObject(data[24], "${data[25]} (${data[26]})", true),
                EmbedObject.EmbedFieldObject(data[27], "${data[28]} (${data[29]})", true),
                EmbedObject.EmbedFieldObject(data[30], "${data[31]} (${data[32]})", true),
                EmbedObject.EmbedFieldObject(data[33], "${data[34]} (${data[35]})", true)
            )

            embedObject.fields = embeddedObjectList
        }
        catch (e: Exception) { e.printStackTrace() }

        return embedObject
    }

    private fun printExtraHelp() {
        val helpMessage = "To retrieve a user's Battlefield V Stats, use one of the following commands. \n```\n!bfvstats origin USERNAME\n!bfvstats psn USERNAME\n!bfvstats xbl USERNAME\n```"
        GuildCommunicator.sendToChannel(event.channel, helpMessage)
    }

}