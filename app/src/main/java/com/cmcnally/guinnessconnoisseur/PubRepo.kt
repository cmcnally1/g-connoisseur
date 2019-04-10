package com.cmcnally.guinnessconnoisseur



class PubRepo(private val pubService: PubService): PubService.DataListener{

    var listener: RepoListener? = null

    init {
        pubService.listener = this
    }

    fun getPubs(latitude: String, longitude: String){

        pubService.fetchData(latitude, longitude)
    }

    override fun onResponseReceived(response: Pubs) {
        listener?.returnPubs(response)
    }

    interface RepoListener {
        fun returnPubs(pubs: Pubs)
    }
}