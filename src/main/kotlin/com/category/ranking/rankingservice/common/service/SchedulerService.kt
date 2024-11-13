package com.category.ranking.rankingservice.common.service


import com.category.ranking.rankingservice.common.enums.RedisKeys
import com.category.ranking.rankingservice.storeDomain.domain.Store
import com.category.ranking.rankingservice.storeDomain.service.StoreService
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val redisService: RedisService,
    private val storeService: StoreService
) {


    // 5분 마다 실행
//    @Scheduled(cron = "0 */5 * * * *")
    fun syncStoreLikes() {

    }

    // 1시간 마다 실행
//    @Scheduled(cron = "0 0 * * * *")
    fun updateStoreViews() {
        val storeViewsKey = RedisKeys.STORE_VIEWS.value
        val storeViewAllKey = "$storeViewsKey*"

        val values = redisService.countValuesByKeys(storeViewAllKey)
        if (values.isEmpty()) return

        val keys = values.keys
        val uuids = keys.map { it.substringAfter(":") }

        val stores = storeService.findByUuids(uuids)

        stores.forEach { store ->
            val redisViewCnt = values["$storeViewsKey${store.uuid}"] ?: 0
            store.increaseViewCnt(redisViewCnt.toInt())

            // TODO: bulk로 변경하기
            storeService.updateStoreViewCnt(store)
        }

        redisService.removeKeys(keys)
    }
}