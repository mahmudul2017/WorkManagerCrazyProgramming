package com.workmanager.crazyprogramming.worker

import android.content.Context
import android.util.Log.d
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.workmanager.crazyprogramming.view.SEND_DATA
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val RECEIVE_DATA = "receiveData"
class CrazyWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        // receive simple data
        val receiveData = inputData.getString(SEND_DATA)

        showToast()
        d("CrazyWorker", "return Result.success() :: $receiveData")

        val data = Data.Builder()
            .putString(RECEIVE_DATA, "data from worker class")
            .build()
        return Result.success(data)
    }

    private fun showToast() {
        // delay is not freeze the UI
        GlobalScope.launch {
            delay(5000)
            d("CrazyWorker", "showToast called")
        }

        // sleep is freeze the UI
        Thread.sleep(5000)
        d("CrazyWorker", "showToast called")
    }
}