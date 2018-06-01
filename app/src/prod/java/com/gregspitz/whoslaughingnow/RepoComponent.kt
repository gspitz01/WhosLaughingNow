package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.source.GameRepository
import com.gregspitz.whoslaughingnow.di.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RepoModule::class)])
interface RepoComponent {
    fun exposeRepository(): GameRepository
}
