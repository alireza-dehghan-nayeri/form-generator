package com.example.formgenerator.ui.view

import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.example.formgenerator.data.FakeData
import com.example.formgenerator.model.InputJson
import com.google.gson.Gson

class MainViewModel : ViewModel() {

    val form: InputJson? =
        Gson().fromJson(FakeData.formConfigJsonCancer, InputJson::class.java)

    val formValue: SnapshotStateMap<String, Any?> = SnapshotStateMap()

    init {
        // as we know the json key is always an string and the value is considered Any the cast is safe
        // even though it is safe we still use as? so instead of throwing any exceptions it returns null
        @Suppress("UNCHECKED_CAST")
        (Gson().fromJson(
            FakeData.formValueJsonCancer,
            Map::class.java
        ) as? Map<out String, Any?>)?.let {
            formValue.putAll(
                it
            )
        }
    }
}