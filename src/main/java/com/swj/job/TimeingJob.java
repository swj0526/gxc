package com.swj.job;

import com.swj.service.GoodsService;
import com.swj.service.GoodsWarnLogService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

//定时任务，执行任务的类以及方法
public class TimeingJob extends QuartzJobBean {
    @Autowired
    private GoodsService goodsService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        //检查库存上限下限报警
        goodsService.checkInventory();

    }
}
