package com.robinpel.battlefieldv_statchecker.core

import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException

object Main {
    @JvmStatic fun main(args: Array<String>) {

        // TODO: Load the json file with the token
        // TODO: If the token file does not exist, create the template and shut down

        Logger.log("main@static", "Booting")
        BattlefieldV_StatChecker(createDiscordClient("TOKEN")).start()
        Logger.log("main@static", "Stopped")
    }

    private fun createDiscordClient(token: String): IDiscordClient? {
        try {
            return ClientBuilder().withToken(token).build()
        } catch(de: DiscordException) {
            de.printStackTrace()
        }
        return null
    }
}