package com.swj.service;

import com.swj.entity.TbLog;
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
public interface LogService extends IService<TbLog> {

   int  addLog(TbLog log);

    List<TbLog> getLogList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();
}
