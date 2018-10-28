package com.yejunjie.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : panhl@pingpongx.com
 * @since : 2018/7/5
 */
@EqualsAndHashCode(callSuper = true)
public class AggsSearchResult extends SearchResult {


    public static final String BUCKETS = "buckets";
    public static final String DOC_COUNT = "doc_count";

    @Setter
    @Getter
    private JSONObject aggregations;

    private static final String VALUE_FIELD = "value";
    public static BigDecimal aggsSumValue(AggsSearchResult result, String field){
        return result.getAggregations().getJSONObject(field).getJSONObject(field).getBigDecimal(VALUE_FIELD);
    }


    /**
     * 获取一个bucket某项的值
     * @param result
     * @param filter
     * @param term
     * @param key
     * @return
     */
    public static long getFilterBucketItemCount(AggsSearchResult result, String filter, String term, String key) {
        if (result == null || result.getAggregations() == null) {
            return 0;
        }
        JSONObject filterJson = result.getAggregations().getJSONObject(filter);

        if (filterJson == null || filterJson.size() == 0) {
            return 0;
        }
        filterJson = filterJson.getJSONObject(filter);

        if (filterJson == null || filterJson.size() == 0) {
            return 0;
        }
        JSONObject termJson = filterJson.getJSONObject(term);

        if (termJson == null || termJson.size() == 0) {
            return 0;
        }

        JSONArray bucket = termJson.getJSONArray(BUCKETS);

        if (bucket == null || bucket.isEmpty()) {
            return 0;
        }
        Iterator it = bucket.iterator();
        while (it.hasNext()) {
            JSONObject json = (JSONObject) it.next();
            if (key.equals(json.getString("key"))) {
                return json.getLong(DOC_COUNT);
            }
        }
        return 0;
    }

    /**
     * 获取 group by 之后 每个bucket 中每个key 的doc_count 注：此方法只支持按一个字段group by ，不支持多字段
     *
     * @param term AggregationBuilders.terms("term").field("field") 中的term
     * @param field AggregationBuilders.terms("term").field("field") 中的field
     * @param key 组中的一个元素
     */
    public static long getBucketItemCount(AggsSearchResult result, String term, String field, String key) {
        if (result == null || result.getAggregations() == null) {
            return 0;
        }

        JSONObject termJson = result.getAggregations().getJSONObject(term);

        if (termJson == null || termJson.size() == 0) {
            return 0;
        }
        JSONObject fieldJson = termJson.getJSONObject(field);

        if (fieldJson == null) {
            return 0;
        }
        JSONArray bucket = fieldJson.getJSONArray(BUCKETS);

        if (bucket == null || bucket.isEmpty()) {
            return 0;
        }
        Iterator it = bucket.iterator();
        while (it.hasNext()) {
            JSONObject json = (JSONObject) it.next();
            if (key.equals(json.getString("key"))) {
                return json.getLong(DOC_COUNT);
            }
        }
        return 0;
    }

    /**
     * 获取 group by 之后 获取个bucket 所有key 的doc_count总和 注：此方法只支持按一个字段group by ，不支持多字段
     *
     * @param term AggregationBuilders.terms("term").field("field") 中的term
     * @param field AggregationBuilders.terms("term").field("field") 中的field
     */
    public static long getBucketCount(AggsSearchResult result, String term, String field) {
        if (result == null || result.getAggregations() == null) {
            return 0;
        }

        JSONObject termJson = result.getAggregations().getJSONObject(term);

        if (termJson == null || termJson.size() == 0) {
            return 0;
        }
        JSONObject fieldJson = termJson.getJSONObject(field);

        if (fieldJson == null) {
            return 0;
        }
        JSONArray bucket = fieldJson.getJSONArray(BUCKETS);

        if (bucket == null || bucket.isEmpty()) {
            return 0;
        }
        Iterator it = bucket.iterator();
        long docCount = 0;
        while (it.hasNext()) {
            JSONObject json = (JSONObject) it.next();
            docCount += json.getLong(DOC_COUNT);
        }
        return docCount;
    }

    /**
     * 获取 group by 之后 获取个bucket 所有key 注：此方法只支持按一个字段group by ，不支持多字段
     *
     * @param term AggregationBuilders.terms("term").field("field") 中的term
     * @param field AggregationBuilders.terms("term").field("field") 中的field
     */
    public static List<String> getAggBucketItems(AggsSearchResult aggs, String term, String field) {
        List<String> result = new ArrayList<>();
        if (aggs == null || aggs.getAggregations() == null) {
            return result;
        }

        JSONObject termJson = aggs.getAggregations().getJSONObject(term);

        if (termJson == null || termJson.size() == 0) {
            return result;
        }
        JSONObject fieldJson = termJson.getJSONObject(field);

        if (fieldJson == null) {
            return result;
        }
        JSONArray bucket = fieldJson.getJSONArray(BUCKETS);

        if (bucket == null || bucket.isEmpty()) {
            return result;
        }
        Iterator it = bucket.iterator();
        while (it.hasNext()) {
            JSONObject json = (JSONObject) it.next();
            result.add(json.getString("key"));
        }
        return result;
    }

    /**
     * 获取按nest字段group by后某个item 的值
     */
    public static long getNestBucketItemCount(AggsSearchResult aggs, String nestName, String term, String field, String key) {
        if (aggs == null || aggs.getAggregations() == null) {
            return 0;
        }
        JSONObject nestJson = aggs.getAggregations().getJSONObject(nestName);
        if (nestJson == null) {
            return 0;
        }
        JSONObject termJson = nestJson.getJSONObject(term);
        if (termJson == null) {
            return 0;
        }
        JSONObject fieldJson = termJson.getJSONObject(field);

        if (fieldJson == null) {
            return 0;
        }

        JSONArray bucket = fieldJson.getJSONArray(BUCKETS);

        if (bucket == null || bucket.isEmpty()) {
            return 0;
        }
        Iterator it = bucket.iterator();
        while (it.hasNext()) {
            JSONObject json = (JSONObject) it.next();
            if (key.equals(json.getString("key"))) {
                return json.getLong(DOC_COUNT);
            }
        }
        return 0;
    }
}
