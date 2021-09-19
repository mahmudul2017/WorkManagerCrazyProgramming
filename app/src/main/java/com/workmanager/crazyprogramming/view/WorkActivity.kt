package com.workmanager.crazyprogramming.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.workmanager.crazyprogramming.R
import com.workmanager.crazyprogramming.worker.CrazyWorker
import com.workmanager.crazyprogramming.worker.RECEIVE_DATA

const val SEND_DATA = "sendData"
class WorkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        // send data to Worker Class
        val data = Data.Builder()
            .putString(SEND_DATA, "data from activity")
            .build()

        // simple oneTime request
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(CrazyWorker::class.java).setInputData(data).build()

        // simple oneTime request with sending data
        val oneTimeWorkRequestWithData = OneTimeWorkRequest.Builder(CrazyWorker::class.java).setInputData(data).build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(oneTimeWorkRequestWithData)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequestWithData.id).observe(this, Observer {
            if (it.state.isFinished) {
                val receiveData = it.outputData.getString(RECEIVE_DATA)
                Toast.makeText(this, "$receiveData", Toast.LENGTH_LONG).show()
                d("WorkActivity", "WorkManager Finished :: $receiveData")
            }
        })

        d("WorkActivity", "WorkActivity Called")
    }
}