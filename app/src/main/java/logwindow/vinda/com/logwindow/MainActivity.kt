package logwindow.vinda.com.logwindow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import logwindow.vinda.com.logwindowlib.LogManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogManager.Builder(this)
                .setOpenWindow(true)
                .setSaveLogtoLocal(true)
                .setLocalLogFIle(FileUtils.getLogPath(this))
                .init()
    }


}
