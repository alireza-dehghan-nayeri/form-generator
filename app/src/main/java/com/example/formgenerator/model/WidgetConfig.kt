package com.example.formgenerator.model


data class WidgetConfig(
    val id: Int,
    val type: String,
    val dataPath: String,
    val dependencies: List<DependencyConfig>,
    val hint: String?,
    val text: String?,
    val validations: List<ValidationConfig>?,
    val keyboardType: String?,
    val items: List<String>?,
    val options: List<String>?
)

// todo: break into widget classes


