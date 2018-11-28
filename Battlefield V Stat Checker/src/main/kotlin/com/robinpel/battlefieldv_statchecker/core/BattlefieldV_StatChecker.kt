package com.robinpel.battlefieldv_statchecker.core

import com.robinpel.battlefieldv_statchecker.d4j.D4J_EventListener
import sx.blah.discord.api.IDiscordClient

class BattlefieldV_StatChecker(val client: IDiscordClient?) {

    fun start(): Boolean {
        client?: return false

        client.dispatcher.registerListener(D4J_EventListener())
        keepRunning()

        return true
    }

    private var count: Int = 0

    private fun keepRunning() {
        client?: return

        if (login()) Logger.log(this.toString(), "Logged in")
        else Logger.err(this.toString(), "Could not log in")

        while (client.isLoggedIn);

        if (count < 3) {
            count++
            keepRunning()

        } else return

    }

    private fun login(): Boolean {
        client?: return false

        var tries: Int = 0
        client.login()

        while(!client.isLoggedIn) {
            try { Thread.sleep(3000) }
            catch (ie: InterruptedException) { ie.printStackTrace() }

            if (tries >= 10) return false
            else tries++
        }

        return true
    }

}