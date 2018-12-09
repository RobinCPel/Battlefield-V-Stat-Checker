package com.robinpel.battlefieldv_statchecker.core

import org.jsoup.Jsoup

class BattlefieldV_StatRetriever {

    fun getStats(playerName: String): List<String> {

        val rawHtml = getRawHtml(playerName)
        rawHtml ?: return listOf("ERR")
        val rawInfo = extractInformation(rawHtml)
        return removeExcessInformation(rawInfo)
    }

    private fun getRawHtml(playerName: String): String? {
        return try { org.jsoup.Jsoup.connect(Constants.BFV.ADDRESS_PREFIX + playerName + Constants.BFV.ADDRESS_SUFFIX).get().html() }
        catch (e: Exception) { Logger.err(this.toString(), "Impossible playerName") ; null }
    }

    private fun extractInformation(html: String): List<String> {
        var newHtml = Jsoup.parse(html).getElementsByClass("stat").html()
        newHtml = Jsoup.parse(newHtml).getElementsByTag("span").html()
        return newHtml.split("\n")
    }

    private fun removeExcessInformation(data: List<String>): List<String> {
        if (data.size < 48) return listOf()
        return data.subList(12, 48)
    }

}