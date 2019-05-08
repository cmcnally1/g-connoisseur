package com.cmcnally.guinnessconnoisseur

import org.koin.dsl.module.module

/*
    Koin module used for dependency injection
 */

val koin = module {

    factory {
        MainPresenter(
            get()
        )
    }

    single {
        PubRepo(
            get()
        )
    }

    single {
        PubService()
    }

}