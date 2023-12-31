package com.example.hrm_management.AppModule

import com.example.hrm_management.BackgroundSync.SyncWorker
import com.example.hrm_management.MainViewModel
import com.example.hrm_management.Views.Menu.MenuViewModel
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel) // Inject your ViewModel here

    fun inject(SyncWorker: SyncWorker)

    fun inject(viewModel: MenuViewModel)
}