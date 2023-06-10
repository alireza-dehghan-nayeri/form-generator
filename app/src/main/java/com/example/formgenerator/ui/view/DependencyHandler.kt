package com.example.formgenerator.ui.view

import androidx.compose.runtime.Composable
import com.example.formgenerator.model.DependencyConfig
import io.github.jamsesso.jsonlogic.JsonLogic
import org.json.JSONObject

@Composable
fun dependencyHandler(
    dependencies: List<DependencyConfig>,
    formValue: Map<String, Any?>
): Boolean {
    val booleanList = mutableListOf<Boolean>()
    val jsonLogic = JsonLogic()
    var conditionEvaluation = true
    if (dependencies.isNotEmpty()) {
        dependencies.forEach {
            val rule = JSONObject(it.rule).toString()
            val output = jsonLogic.apply(rule, formValue)
            booleanList.add(
                output.toString() == "true"
            )
        }
        conditionEvaluation = !booleanList.any { !it }
    }
    return conditionEvaluation
}