package com.example.formgenerator.ui.view

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.formgenerator.data.FakeData
import com.example.formgenerator.model.InputJson

import com.google.gson.Gson


class MainViewModel : ViewModel() {

    val form: InputJson? =
        Gson().fromJson(FakeData.formConfigJsonCancer, InputJson::class.java)

    val formValue = mutableStateMapOf<String, Any?>(

    )

}