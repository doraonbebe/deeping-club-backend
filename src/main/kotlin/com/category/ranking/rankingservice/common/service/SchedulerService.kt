package com.category.ranking.rankingservice.common.service


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

    // 5분 마다 실행
//    @Scheduled(cron = "0 */5 * * * *")
    fun updateStoreViews() {

    }

}