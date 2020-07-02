package com.swj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.entity.TbGoodsInAndOut;
import com.swj.vo.ConditionalVO;

import java.util.List;

public interface GoodsInAndOutService extends IService<TbGoodsInAndOut> {

    int addGoodsInAndOut(TbGoodsInAndOut goodsInAndOut);

    List<TbGoodsInAndOut> getGoodsInAndOutList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();


}
