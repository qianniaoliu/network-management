package com.network.management.mapper;

import com.network.management.domain.dao.Profession;
import com.network.management.domain.search.ProfessionSearch;

import java.util.List;


/**
 * 职位mapper
 * @author yyc
 * @date 2021/2/27 13:21
 */
public interface ProfessionMapper {
    /**
     * 插入职位信息
     * @param profession {@link Profession}
     * @return 返回主键id
     */
    Integer insert(Profession profession);

    /**
     * 修改职位数据
     * @param profession 职位信息
     */
    void updateByPrimaryKeySelective(Profession profession);

    /**
     * 通过主键id删除职位信息
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 条件查询总数
     * @param search 搜索条件
     * @return 总数
     */
    Integer count(ProfessionSearch search);

    /**
     * 条件搜索
     * @param search 搜索条件
     * @return 职位列表
     */
    List<Profession> search(ProfessionSearch search);

    /**
     * 根据职位id获取职位信息
     * @param id 职位id
     * @return 职位信息
     */
    Profession selectByPrimaryKey(Integer id);

    /**
     * 根据职位名称查询职位信息
     * @param professionName 职位名称
     * @return {@link Profession}
     */
    Profession selectByProfessionName(String professionName);

    /**
     * 查询所有职位列表
     * @return {@link List<Profession>}
     */
    List<Profession> queryAllProfession();
}
