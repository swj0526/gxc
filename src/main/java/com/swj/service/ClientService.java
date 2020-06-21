package com.swj.service;

import com.swj.entity.TbClient;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.ConditionalVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface ClientService extends IService<TbClient> {

    int addClient(TbClient client);

    int updateClient(TbClient client);

    int deleteClient(Integer id);

    TbClient getClientById(Integer id);

    List<TbClient> getClientList (Integer page, Integer limit, ConditionalVO vo);

    long getTotal();
}
