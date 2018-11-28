package com.robinpel.battlefieldv_statchecker.core

import org.jsoup.Jsoup

class BattlefieldV_StatRetriever {

    fun getStats(playerName: String): String {

        val rawHtml = getRawHtml(playerName)
        rawHtml ?: return "ERR"
        return extractInformation(rawHtml)
    }

    private fun getRawHtml(playerName: String): String? {
        return try { org.jsoup.Jsoup.connect(Constants.BFV.ADDRESS_PREFIX + playerName + Constants.BFV.ADDRESS_SUFFIX).get().html() }
        catch (e: Exception) { e.printStackTrace() ; null }
    }

    private fun extractInformation(html: String): String {
        var newHtml = Jsoup.parse(html).getElementsByAttribute("data-v-2bd3262b").html()
        newHtml = Jsoup.parse(newHtml).getElementsByTag("main").html()
        newHtml = Jsoup.parse(newHtml).getElementsByTag("span").html()
        return newHtml
    }

}