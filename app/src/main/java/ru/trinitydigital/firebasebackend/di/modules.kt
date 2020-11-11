package ru.trinitydigital.firebasebackend.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.trinitydigital.firebasebackend.data.api.FirebaseApi
import ru.trinitydigital.firebasebackend.data.api.FirebaseApiImpl
import ru.trinitydigital.firebasebackend.data.repository.FirebaseRepository
import ru.trinitydigital.firebasebackend.data.repository.FirebaseRepositoryImpl
import ru.trinitydigital.firebasebackend.ui.main.MainViewModel

val viewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
}

val repositoryModule: Module = module {
    single<FirebaseRepository> { FirebaseRepositoryImpl(get()) }
}

val apiModule: Module = module {
    single<FirebaseApi> { FirebaseApiImpl() }
}

val appModules = listOf(viewModelModule, repositoryModule, apiModule)