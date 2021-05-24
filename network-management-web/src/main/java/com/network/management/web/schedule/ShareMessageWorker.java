package com.network.management.web.schedule;

import com.network.management.domain.vo.ShareMessageVo;
import com.network.management.service.ShareMessageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author yusheng
 */
@Configuration
public class ShareMessageWorker {

    @Value("${user.recorders.query.url}")
    public String userRecordersQueryUrl;
    @Autowired
    private ShareMessageService shareMessageService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${query.share.message.interval.time:1}")
    private Integer intervalTime;

    @Scheduled(cron = "${cache.share.message.interval.time}")
    public void cacheShareMessage() {
        Date currentDate = new Date();
        Map<String, Object> params = new HashMap<>();
        params.put("action", "api");
        params.put("type", "aud,vid,sms,pic");
        params.put("callee", "60700");
        params.put("startDate", getOneSecondsTime(currentDate));
        params.put("endDate", currentDate.getTime());
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity(params);
        ResponseEntity<ShareMessageResult> responseEntity = restTemplate.exchange(userRecordersQueryUrl, HttpMethod.GET, httpEntity, ShareMessageResult.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            if(Objects.nonNull(responseEntity.getBody()) && !CollectionUtils.isEmpty(responseEntity.getBody().getDataList())){
                responseEntity.getBody().getDataList().forEach(shareMessageVo -> {
                    shareMessageService.add(shareMessageVo);
                });
            }
        }
    }

    private Long getOneSecondsTime(Date currentDate) {
        Calendar c = new GregorianCalendar();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, -intervalTime);
        return c.getTimeInMillis();
    }

    @Data
    public static class ShareMessageResult {
        private Integer total;
        private List<ShareMessageVo> dataList;
    }
}
