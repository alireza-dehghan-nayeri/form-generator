package com.example.formgenerator.model


data class ScreenConfig(
    val dependencies: List<DependencyConfig>,
    val id: Int,
    val widgetConfigs: List<WidgetConfig>
)


