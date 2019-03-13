package com.robinpel.battlefieldv_statchecker.core

class Constants {
    object Operation {
        @JvmStatic val HEADER:      String  = "!bfvstats"
        @JvmStatic val DELIMITER:   String  = " "
    }

    object BFV {
        @JvmStatic val ORIGIN_ADDRESS_PREFIX: String = "https://battlefieldtracker.com/bfv/profile/origin/"
        @JvmStatic val PSN_ADDRESS_PREFIX: String = "https://battlefieldtracker.com/bfv/profile/psn/"
        @JvmStatic val XBOX_ADDRESS_PREFIX: String = "https://battlefieldtracker.com/bfv/profile/xbl/"
        @JvmStatic val ADDRESS_SUFFIX: String = "/overview"
    }

    object Address {
        @JvmStatic val BOT_ICON: String = "https://cdn.vox-cdn.com/thumbor/d4wTU8dHXwpoZWD8dsOFfGu1fMU=/0x0:1129x631/1200x800/filters:focal(483x72:663x252)/cdn.vox-cdn.com/uploads/chorus_image/image/59837397/Screen_Shot_2018_05_24_at_9.48.41_AM.0.png"
        @JvmStatic val BFV_ICON: String = "https://bfclanwars.net/wp-content/uploads/2018/07/32682511_1792615077462870_5239568812143017984_n.png"
        @JvmStatic val GIT_REPO: String = "https://github.com/RobinCPel/Battlefield-V-Stat-Checker"
        @JvmStatic val BFT_SITE: String = "https://trackerfiles.blob.core.windows.net/trackernetwork/logos/btrlogo.png"
    }

    object StatIndex {
        @JvmStatic val START: Int = 8
        @JvmStatic val END: Int = 32
        @JvmStatic val SIZE: Int = END - START
    }

}
