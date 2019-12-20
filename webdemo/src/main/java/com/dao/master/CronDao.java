package com.dao.master;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author AlbertXe
 * @date 2019-12-20 14:41
 */
@Component
public interface CronDao {
    @Select("select cron from cron limit 1")
    String getCron() ;
}
