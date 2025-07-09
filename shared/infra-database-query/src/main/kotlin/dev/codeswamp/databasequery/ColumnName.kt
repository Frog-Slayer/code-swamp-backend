package dev.codeswamp.databasequery

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ColumnName(val value: String)
