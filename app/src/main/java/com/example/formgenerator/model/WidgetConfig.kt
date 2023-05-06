package com.example.formgenerator.model


data class WidgetConfig(
    val id: Int,
    val type: String,
    val dataPath: String,
    val dependencies: List<DependencyConfig>,
    val hint: String?,
    val text: String?,
    val validations: List<ValidationConfig>?
)

