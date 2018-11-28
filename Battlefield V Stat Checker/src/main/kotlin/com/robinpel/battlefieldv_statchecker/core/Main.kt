package com.robinpel.battlefieldv_statchecker.core

import org.json.JSONObject
import sx.blah.discord.api.ClientBuilder
import sx.blah.discord.api.IDiscordClient
import java.io.File
import java.util.*

object Main {
    @JvmStatic fun main(args: Array<String>) {
        Logger.log("main@static", "Booting")

        // Create a bot booter and load its token
        val botBooter = BotBooter()
        if (!botBooter.loadToken()) return
        //if (!botBooter.loadTestToken()) return

        Logger.log("main@static", "Creating the client")

        // Create the bot client and start it (if it did not fail)
        val botClient = botBooter.createDiscordClient()
        botClient ?: return
        BattlefieldV_StatChecker(botClient).start()

        Logger.log("main@static", "Stopping")
    }
}

object Test {
    @JvmStatic fun main(args: Array<String>) {
        Logger.log("main@static", "Booting")

        // Create a bot booter and load its token
        val botBooter = BotBooter()
        if (!botBooter.loadTestToken()) return

        // Create the bot client and start it (if it did not fail)
        val botClient = botBooter.createDiscordClient()
        botClient ?: return
        BattlefieldV_StatChecker(botClient).start()

        Logger.log("main@static", "Stopped")
    }
}

private class BotBooter {

    private val tokenFilePath: String = "./bot-token.json"
    private var botToken: String? = null

    private fun loadTokenFile(test: Boolean = false): JSONObject? {

        val tokenFile: File

        try { tokenFile = File(tokenFilePath) }
        catch(e: Exception) { e.printStackTrace() ; return null }

        if (!tokenFile.exists()) {
            Logger.log(this.toString(), "TokenFile not found, creating one...")
            tokenFile.createNewFile()

            Logger.log(this.toString(), "Please enter your bot token and press return ")
            val newBotToken = Scanner(System.`in`).nextLine()

            if (!test) { tokenFile.writeText("{\n    \"token\": \"$newBotToken\"\n}") }
            else { tokenFile.writeText("{\n    \"test\": \"$newBotToken\"\n}") }
        }
        else { Logger.log(this.toString(), "TokeFile found! Reading content...") }

        return try { JSONObject(tokenFile.readText()) }
        catch (e: Exception) { e.printStackTrace() ; null }
    }

    fun loadToken(): Boolean {

        // Load the token file and check if everything went alright
        val tokenData = loadTokenFile()
        tokenData ?: return false

        // Retrieve the token from the data
        return if (tokenData.has("token")) { botToken = tokenData.getString("token") ; true }
        else { Logger.log(this.toString(), "Could not find the token with the key 'token'") ; false }
    }

    fun loadTestToken(): Boolean {

        // Load the token file and check if everything went alright
        val tokenData = loadTokenFile(true)
        tokenData ?: return false

        // Retrieve the token from the data
        return if (tokenData.has("test")) { botToken = tokenData.getString("test") ; true }
        else { Logger.log(this.toString(), "Could not find the token with the key 'test'") ; false }
    }

    fun createDiscordClient(): IDiscordClient? {
        return try { ClientBuilder().withToken(botToken).build() }
        catch (e: Exception) { null }
    }

}