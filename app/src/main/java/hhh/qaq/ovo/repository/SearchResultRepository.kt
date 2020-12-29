package hhh.qaq.ovo.repository

/**
 * @By Journey 2020/12/29
 * @Description
 */
class SearchResultRepository:NetRepository() {
    suspend fun searchKeyMusic(key:String,page:Int) = apiService.searchMusic(key,page)
}