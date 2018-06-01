package com.gregspitz.whoslaughingnow

import android.app.Application
import com.gregspitz.whoslaughingnow.di.AppModule
import com.gregspitz.whoslaughingnow.di.DaggerUseCaseComponent
import com.gregspitz.whoslaughingnow.di.UseCaseComponent
import com.gregspitz.whoslaughingnow.di.UseCaseModule

/**
 * Main application class
 */
class WhosLaughingApplication : Application() {

    companion object {
        @JvmStatic lateinit var repoComponent: RepoComponent

        @JvmStatic lateinit var useCaseComponent: UseCaseComponent
    }

    override fun onCreate() {
        super.onCreate()

        repoComponent = DaggerRepoComponent.builder()
                .appModule(AppModule(this))
                .repoModule(RepoModule())
                .build()

        useCaseComponent = DaggerUseCaseComponent.builder()
                .repoComponent(repoComponent)
                .useCaseModule(UseCaseModule())
                .build()
    }
}
