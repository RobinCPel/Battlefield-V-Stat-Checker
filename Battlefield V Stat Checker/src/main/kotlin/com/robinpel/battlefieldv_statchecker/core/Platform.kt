package com.robinpel.battlefieldv_statchecker.core

enum class Platform(val value: String) {
    Origin(Constants.BFV.ORIGIN_ADDRESS_PREFIX),
    PSN(Constants.BFV.PSN_ADDRESS_PREFIX),
    XBL(Constants.BFV.XBOX_ADDRESS_PREFIX),
}
