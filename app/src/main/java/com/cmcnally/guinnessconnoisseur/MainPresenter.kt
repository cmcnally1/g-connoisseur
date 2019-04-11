package com.cmcnally.guinnessconnoisseur


class MainPresenter(private val pubRepo: PubRepo): PubRepo.RepoListener {

    private var view: View? = null

    init{
        pubRepo.listener = this
    }

    //When the view is created, it must attach to the presenter
    fun attachView(view: View){
        this.view = view
    }

    //detach from the presenter when the view is destroyed
    fun detachView() {
        this.view = null
    }

    fun getPubs(latitude: String, longitude: String){

        pubRepo.getPubs(latitude, longitude)
    }

    override fun returnPubs(pubs: Pubs) {
        view?.returnPubs(pubs)
    }

    interface View{
        fun returnPubs(pubs: Pubs)
    }

}