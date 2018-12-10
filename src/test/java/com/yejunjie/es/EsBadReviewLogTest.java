package com.yejunjie.es;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yejunjie.AbstractControllerTest;
import com.yejunjie.dto.BadReviewItemLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;


/**
 * @author: junjieye
 * @since: 2018/10/24
 * @des
 */
public class EsBadReviewLogTest extends AbstractControllerTest {

    @Autowired
    private EsBadReviewLog esBadReviewLog;

    @Test
    public void test(){

        String sellerId = "A3DX6IPPS9017J";
        String keyword = "asintest2";
        List<String> sellerIds = Lists.newArrayList();
        sellerIds.add("A2X5JMTLFSLHUT");
        sellerIds.add("123456");
        sellerIds.add("A3DX6IPPS9017J");

        SearchResult result = esBadReviewLog.queryBadReviewBySellerId(null,sellerIds,null,1,20);
        if (Objects.isNull(result) || Objects.isNull(result.getHits())) {
            System.out.println("no hints");
        }else{
            List<BadReviewItemLog> list = JSON.parseArray(result.getHits(), BadReviewItemLog.class);
            System.out.println(JSON.toJSONString(list));
        }

    }
}
