package com.mateuslima.blocodenotas.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SingletonCoroutine()
