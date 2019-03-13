package com.robinpel.battlefieldv_statchecker.core

import org.jsoup.Jsoup

class BattlefieldV_StatRetriever {

    fun getStats(playerName: String, platform: Platform): List<String> {

        val rawHtml = getRawHtml(platform.value + playerName + Constants.BFV.ADDRESS_SUFFIX)
        rawHtml ?: return listOf("ERR")
        val rawInfo = extractInformation(rawHtml)
        return removeExcessInformation(rawInfo.toMutableList())
    }

    private fun getRawHtml(ofAddress: String): String? {
        //return try { org.jsoup.Jsoup.connect(Constants.BFV.ADDRESS_PREFIX + playerName + Constants.BFV.ADDRESS_SUFFIX).get().html() }
        return try { org.jsoup.Jsoup.connect(ofAddress).get().html() }
        catch (e: Exception) { Logger.err(this.toString(), "Impossible playerName") ; null }
    }

    private fun extractInformation(html: String): List<String> {
        var newHtml = Jsoup.parse(html).getElementsByClass("stat").html()
        newHtml = Jsoup.parse(newHtml).getElementsByTag("span").html()
        return newHtml.split("\n")
    }

    private fun removeExcessInformation(data: MutableList<String>): List<String> {

        for (index in data.indices.reversed()) {
            if (data[index].toLowerCase().startsWith("top ") && data[index].endsWith("%"))
                data.removeAt(index)
        }
        if (data.size < Constants.StatIndex.END) return listOf()
        return data.subList(Constants.StatIndex.START, Constants.StatIndex.END)
    }

}