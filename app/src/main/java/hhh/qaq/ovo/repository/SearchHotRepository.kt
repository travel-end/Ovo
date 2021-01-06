package hhh.qaq.ovo.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotRepository:NetRepository() {
    suspend fun getHotSearchTag() = withContext(Dispatchers.Default){
        async {
            // TODO: 2020/12/28 数据库操作 获取数据库中的数据
            val hotSearch = netEaseApiService.getHotSearchInfo()
            hotSearch
        }
    }.await()

    suspend fun getHistorySearch() = withContext(Dispatchers.Default){
        async {
            // TODO: 2020/12/28 从数据 库获取搜索历史
        }   
    }.await()

}