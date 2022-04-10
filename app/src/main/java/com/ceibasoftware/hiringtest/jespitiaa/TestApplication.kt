package com.ceibasoftware.hiringtest.jespitiaa

import android.app.Application
import com.ceibasoftware.hiringtest.jespitiaa.database.CeibaDB

class TestApplication: Application()  {
    val database by lazy { CeibaDB.getDatabase(this) }
}
