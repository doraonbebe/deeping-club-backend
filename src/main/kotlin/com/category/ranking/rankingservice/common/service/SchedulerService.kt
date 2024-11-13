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


    // 1시간 마다 실행
//    @Scheduled(cron = "0 0 * * * *")
    fun syncStoreLikes() {
        val storeViewsKey = RedisKeys.STORE_LIKES.value
        val storeViewAllKey = "$storeViewsKey*"

        val values = redisService.getAllValuesOfKeys(storeViewAllKey)

        val keys = values.keys
        val uuids = keys.map { it.substringAfter(":") }

        val stores = storeService.findByUuids(uuids)

        for (store in stores) {
            val existedUserIds = store.likes.map { it.userId }
            val userIds = values["$storeViewsKey${store.uuid}"]!!.map { it.toLong() }

            val saveUserIds = userIds.filterNot { existedUserIds.contains(it) }
            store.addAllLike(saveUserIds)
        }

        storeService.saveAllStore(stores)

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
            val redisViewCnt = values["$storeViewsKey${store.uuid}"]!!
            store.increaseViewCnt(redisViewCnt.toInt())
        }

        storeService.saveAllStore(stores)
    }
}