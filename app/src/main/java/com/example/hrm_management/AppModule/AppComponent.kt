package com.example.hrm_management.AppModule

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [SharedPreferencesModule::class])

interface AppComponent {
    // Define methods to inject dependencies into your classes, including ViewModels
}