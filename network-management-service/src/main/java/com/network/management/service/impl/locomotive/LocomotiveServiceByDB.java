package com.network.management.service.impl.locomotive;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.network.management.common.convert.NetWorkMapUtils;
import com.network.management.common.mysql.JDBCClient;
import com.network.management.common.mysql.bo.LocomotiveBo;
import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.enums.LocomotiveTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocomotiveServiceByDB extends AbstractLocomotiveServiceImpl{
    @Value("${locomotive_db_url}")
    private String dbUrl;
    @Value("${locomotive_db_name}")
    private String dbName;
    @Value("${locomotive_db_password}")
    private String dbPassword;

    private String LOCOMOTIVE_QUERY_SQL = "select * from epc_uestate";
    @Override
    protected Map<String, String> queryLocomotiveLocations(String coreIp, List<Locomotive> locomotives) {
        Set<String> ueIps = NetWorkMapUtils.toSet(locomotives, Locomotive::getUeIp);
        if(CollectionUtils.isEmpty(ueIps)){
            log.warn("机车信息列表为空locomotives->{}", JSON.toJSONString(locomotives));
            return Maps.newHashMap();
        }
        List<LocomotiveBo> locomotiveBos = JDBCClient.execute(dbUrl, dbName, dbPassword, LOCOMOTIVE_QUERY_SQL);
        log.info("数据库中查询出来的机车数据locomotiveBos->{}", JSON.toJSONString(locomotiveBos));
        return ListUtils.emptyIfNull(locomotiveBos)
                .stream()
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(each.getUeIp()))
                .filter(NetWorkMapUtils.filterDistinctKey(LocomotiveBo::getUeIp))
                .collect(Collectors.toMap(LocomotiveBo::getUeIp, LocomotiveBo::getENodeBIP));
    }

    @Override
    public boolean isSupport(String type) {
        return getType().equals(type);
    }

    @Override
    public String getType() {
        return LocomotiveTypeEnum.DB_QUERY.getType();
    }
}
