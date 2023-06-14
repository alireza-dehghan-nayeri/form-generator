package com.example.formgenerator.ui.view

import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.example.formgenerator.data.FakeData
import com.example.formgenerator.model.InputJson
import com.google.gson.Gson


class MainViewModel : ViewModel() {

    val form: InputJson? =
        Gson().fromJson(FakeData.formConfigJsonCancer, InputJson::class.java)

    val formValue: SnapshotStateMap<String, Any?> = SnapshotStateMap<String, Any?>()

    init {

        formValue.putAll(
            Gson().fromJson(
                FakeData.formValueJsonCancer,
                Map::class.java
            ) as Map<out String, Any?>
        )
    }

}