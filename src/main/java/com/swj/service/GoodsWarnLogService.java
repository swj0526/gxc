package com.swj.service;

import com.swj.entity.TbGoodsWarnLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.ConditionalVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface GoodsWarnLogService extends IService<TbGoodsWarnLog> {

    int addGoodsWarnLog(TbGoodsWarnLog goodsWarnLog);

    List<TbGoodsWarnLog> getGoodsWarnLogList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();
}
