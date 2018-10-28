package com.yejunjie.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yejunjie.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: junjieye
 * @since: 2018/10/28
 * @des
 */
public class EsDailySellerProductTest extends AbstractControllerTest {


    @Autowired
    private EsDailySellerProduct esDailySellerProduct;

    @Test
    public void test(){
        String sellerId = "A3DX6IPPS9017J";
        String platform = "amazon_na";
        String marketplace = "amazon";
        String startDate = "2018-10-18";
        String endDate = "2018-10-18";
        String sortColume = "sale_amount";
        AggsSearchResult result =  esDailySellerProduct.aggSumOrderResult(sellerId,marketplace,startDate,endDate,sortColume);
        JSONObject analyse = result.getAggregations();
        JSONArray jsonArray = (JSONArray)analyse.getJSONObject(EsDailySellerProduct.GROUP_ALIAS).getJSONObject(EsDailySellerProduct.GROUP_ALIAS).get("buckets");
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
        }
    }
}
