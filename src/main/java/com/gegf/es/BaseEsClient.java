package com.gegf.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseEsClient {

    @Autowired
    protected TransportClient transportClient;
    private static final Integer SIZE = 20;

    SearchResult search(String index, String type, QueryBuilder qb, SortBuilder sb, Integer page, Integer size) {
        return this.search(index,type,qb,Collections.singletonList(sb),page,size);
    }
    SearchResult search(String index, String type, QueryBuilder qb, List<SortBuilder> sortBuilderList, Integer page, Integer size) {
        SearchRequestBuilder builder = transportClient.prepareSearch(index);
        size = size == null?SIZE:size;
        int from = page==null?0:(page-1)*size;
        builder.setTypes(type)
            .setQuery(qb)
            .setSize(size)
            .setFrom(from);
        sortBuilderList.forEach(builder::addSort);

        SearchResponse response = builder.execute().actionGet();

        SearchResult searchResult = SearchResult.builder()
            .took(response.getTook().millis())
            .timeOut(response.isTimedOut())
            .totalShards(response.getTotalShards())
            .successfulShards(response.getSuccessfulShards())
            .failedShards(response.getFailedShards())
            .scrollId(response.getScrollId())
            .totalHits(response.getHits().getTotalHits())
            .build();

        if (ArrayUtils.isNotEmpty(response.getHits().getHits())) {
            List<Map<String, Object>> hits = Arrays.stream(response.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
            searchResult.setHits(JSON.toJSONString(hits));
        }
        return searchResult;
    }

    AggsSearchResult search(String index, String type, QueryBuilder qb, List<AggregationBuilder> aggs) {
        SearchRequestBuilder builder = transportClient.prepareSearch(index);
        builder.setTypes(type).setQuery(qb).setSize(0);
        for (AggregationBuilder agg : aggs) {
            builder.addAggregation(agg);
        }
        SearchResponse response = builder.execute().actionGet();
        Aggregations aggregations = response.getAggregations();
        AggsSearchResult searchResult = new AggsSearchResult();
        searchResult.setTook(response.getTook().millis());
        searchResult.setTimeOut(response.isTimedOut());
        searchResult.setTotalShards(response.getTotalShards());
        searchResult.setSuccessfulShards(response.getSuccessfulShards());
        searchResult.setFailedShards(response.getFailedShards());
        searchResult.setTotalHits(response.getHits().getTotalHits());
        List<Aggregation> list = aggregations.asList();
        JSONObject result = new JSONObject();
        if (list != null) {
            for (Aggregation agg : list) {
                String aggStr = agg.toString();
                if (aggStr.startsWith("{") || aggStr.startsWith("[")) {
                    result.put(agg.getName(), JSONObject.parseObject(aggStr));
                } else {
                    result.put(agg.getName(), JSONObject.toJSON(agg));
                }
            }
        }
        searchResult.setAggregations(result);

        return searchResult;
    }

    long updateTimeDiff(String index, String type, String keyword) {
        long diff = 0;
        QueryBuilder qb = QueryBuilders.matchAllQuery();
        SortBuilder sb = SortBuilders.fieldSort(keyword).order(SortOrder.DESC);
        SearchResult result = search(index,type,qb,sb,1,1);
        if(StringUtils.isNotBlank(result.getHits())) {
           JSONArray list =  JSON.parseArray(result.getHits());
           if(!list.isEmpty()){
               Date updated = list.getJSONObject(0).getDate(keyword);
               return System.currentTimeMillis() - updated.getTime();
           }
        }
        return diff;
    }

    protected AggsSearchResult search(String index, String type, QueryBuilder qb, SortBuilder sb, List<AggregationBuilder> aggs, Integer page, Integer size) {
        SearchRequestBuilder builder = transportClient.prepareSearch(index)
                .setTypes(type)
                .addSort(sb)
                .setQuery(qb)
                .setSize(size)
                .setFrom((page - 1) * (null == size ? SIZE : size));
        for (AggregationBuilder agg : aggs) {
            builder.addAggregation(agg);
        }

        SearchResponse response = builder.execute().actionGet();

        Aggregations aggregations = response.getAggregations();
        AggsSearchResult searchResult = new AggsSearchResult();
        searchResult.setTook(response.getTook().millis());
        searchResult.setTimeOut(response.isTimedOut());
        searchResult.setTotalShards(response.getTotalShards());
        searchResult.setSuccessfulShards(response.getSuccessfulShards());
        searchResult.setFailedShards(response.getFailedShards());
        searchResult.setTotalHits(response.getHits().getTotalHits());
        List<Aggregation> list = aggregations.asList();
        JSONObject result = new JSONObject();
        if (list != null) {
            for (Aggregation agg : list) {
                result.put(agg.getName(), JSONObject.parseObject(agg.toString()));
            }
        }
        searchResult.setAggregations(result);

        return searchResult;
    }
}
