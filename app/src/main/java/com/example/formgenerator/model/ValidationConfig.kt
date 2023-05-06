package com.example.formgenerator.model

data class ValidationConfig(
    val type: String,
    val message: String,
    val dependencies: List<DependencyConfig>
)
