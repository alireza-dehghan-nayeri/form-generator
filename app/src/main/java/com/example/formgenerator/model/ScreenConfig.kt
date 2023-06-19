package com.example.formgenerator.model

data class ScreenConfig(
    val dependencies: List<DependencyConfig>,
    val widgetConfigs: List<WidgetConfig>,
    val title: String?,
    val id: Int
)


