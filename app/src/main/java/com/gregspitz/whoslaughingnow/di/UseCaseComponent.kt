package com.gregspitz.whoslaughingnow.di

import com.gregspitz.whoslaughingnow.RepoComponent
import com.gregspitz.whoslaughingnow.game.GameFragment
import dagger.Component

/**
 * Dagger component for use case injection
 */
@UseCaseScope
@Component(modules = [(UseCaseModule::class)], dependencies = [RepoComponent::class])
interface UseCaseComponent {

    fun inject(gameFragment: GameFragment)
}
