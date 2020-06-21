package com.swj.service;

import com.swj.entity.TbSell;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.entity.TbSellDetalis;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface SellService extends IService<TbSell> {

    int addSell(TbSell sell);

    int updateSell(TbSell sell);

    int deleteSell(Integer id);

    TbSell getSellById(Integer id);

    List<TbSell> getSellList(Integer page, Integer limit, TbSell sell);

    long getTotal();

    int updateSellByRead(Integer sellId,Integer state);

    List<TbSell> getSellListByState(Integer page, Integer limit, Integer state);

    int endSellById(Integer sellId, int stateEnd);
}
