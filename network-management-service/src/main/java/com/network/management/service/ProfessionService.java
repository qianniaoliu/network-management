package com.network.management.service;

import com.network.management.domain.search.Page;
import com.network.management.domain.search.ProfessionSearch;
import com.network.management.domain.vo.ProfessionVo;

import java.util.List;

/**
 * 职位服务类
 * @author yyc
 * @date 2021/2/28 11:50
 */
public interface ProfessionService {
    /**
     * 新增职位信息
     *
     * @param professionVo 职位信息
     * @return 职位id
     */
    void add(ProfessionVo professionVo);

    /**
     * 修改职位信息
     *
     * @param professionVo 职位信息
     */
    void update(ProfessionVo professionVo);

    /**
     * 删除职位信息
     *
     * @param id 职位id
     */
    void delete(Integer id);

    /**
     * 获取单个职位信息
     *
     * @param id 职位id
     * @return 职位信息
     */
    ProfessionVo get(Integer id);

    /**
     * 分页查询职位信息
     * @param search 条件对象
     * @return 分页数据
     */
    Page<ProfessionVo> search(ProfessionSearch search);

    /**
     * 查询所有职位类别
     * @return {@link List<ProfessionVo>}
     */
    List<ProfessionVo> queryAllProfessions();

}
