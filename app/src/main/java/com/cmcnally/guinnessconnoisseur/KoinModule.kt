package com.cmcnally.guinnessconnoisseur

import org.koin.dsl.module.module


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