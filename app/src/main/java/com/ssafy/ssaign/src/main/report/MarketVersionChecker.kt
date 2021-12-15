package com.ssafy.ssaign.src.main.report

import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MarketVersionChecker {
    fun getMarketVersion(packageName:String):String?
    {
        try {
            val doc = Jsoup.connect ("https://play.google.com/store/apps/details?id=$packageName").get()
            val version = doc.select (".content")

            version.forEach { mElement ->
                if (mElement.attr("itemprop").equals("softwareVersion")) {
                    return mElement.text().trim()
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    fun getMarketVersionFast(packageName:String):String? {
        var mData = ""
        var mVer:String? = ""
        try {
            val mUrl =  URL("https://play.google.com/store/apps/details?id=$packageName")
            val mConnection =  mUrl.openConnection() as HttpURLConnection
            mConnection.connectTimeout = 5000
            mConnection.useCaches = false
            mConnection.doOutput = true
            if (mConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val mReader = BufferedReader(InputStreamReader(mConnection.inputStream))
                while(true){
                    val line = mReader.readLine() ?: break
                    mData += line
                }

                mReader.close()
            }
            mConnection.disconnect()
        } catch (ex:Exception) {
            ex.printStackTrace()
            return null
        }
        val startToken = "softwareVersion\">"
        val endToken = "<"
        val index = mData.indexOf (startToken)

        if (index == -1) {
            mVer = null
        } else {
            mVer = mData.substring(index + startToken.length, index + startToken.length + 100)
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim()
        }
        return mVer
    }
}