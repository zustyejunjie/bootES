package com.yejunjie.es;

import com.yejunjie.util.DateUtil;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author hanyuting
 * @since 2018/10/22
 */
@Component
public class EsDailySellerProduct extends BaseEsClient {
    private static final String SELLER_FIELD = "seller_id";
    public static final String ASIN_FIELD = "asin";
    private static final String DATE_FIELD = "day";
    private static final String MARKET_FIELD = "marketplace";
    private static final String SALE_AMOUNT_FIELD = "sale_amount";
    private static final String SALE_VOLUME_FIELD = "sale_volume";
    private static final String BAD_FIELD = "bad_review_cnt";
    private static final String REFUND_FIELD = "refund_cnt";
    public static final String AMOUNT_SUM = "amount";
    public static final String VOLUME_SUM = "volume";
    public static final String BAD_SUM = "bad";
    public static final String REFUND_SUM = "refund";
    public static final String GROUP_ALIAS = "group";
    public static final String SUM_ALIAS = "sum";
    public static final String VALUE = "value";

    /**
     *
     * @param sellerId 店铺id
     * @param asin 产品asin
     * @param market 站点
     * @param start 起始日期：距离今天start天
     * @param end 结束日期：距离今天end天
     * @return 聚合值
     */
    public AggsSearchResult aggSumResult(Collection<String> sellerId, String asin, String market, int start, int end){
        QueryBuilder qb = boolQuery()
            .filter(termsQuery(SELLER_FIELD,sellerId))
            .filter(termQuery(ASIN_FIELD,asin))
            .filter(termQuery(MARKET_FIELD,market))
            .filter(rangeQuery(DATE_FIELD).gte(DateUtil.afterDayDateFormat(start,DateUtil.SIMPLE_DATE_FORMAT))
                .lte(DateUtil.afterDayDateFormat(end, DateUtil.SIMPLE_DATE_FORMAT)));
        List<AggregationBuilder> aggBuilders = Lists.newArrayList();
        aggBuilders.add(AggregationBuilders.sum(AMOUNT_SUM).field(SALE_AMOUNT_FIELD));
        aggBuilders.add(AggregationBuilders.sum(VOLUME_SUM).field(SALE_VOLUME_FIELD));
        aggBuilders.add(AggregationBuilders.sum(BAD_SUM).field(BAD_FIELD));
        aggBuilders.add(AggregationBuilders.sum(REFUND_SUM).field(REFUND_FIELD));
        return super.search(EsIndexTypeEnum.DAILY_SELLER_PRODUCT.getIndex(),EsIndexTypeEnum.DAILY_SELLER_PRODUCT.getType(),
            qb,aggBuilders);
    }

    public AggsSearchResult aggSumOrderResult(String sellerId,String market,String startDate,String endDate,String orderColumn){
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(DATE_FIELD).gte(startDate).lte(endDate);
        QueryBuilder qb = boolQuery()
                .must(termQuery(SELLER_FIELD,sellerId))
                .must(termQuery(MARKET_FIELD,market))
                .must(rangeQueryBuilder);
        List<AggregationBuilder> aggBuilders = Lists.newArrayList();
        TermsAggregationBuilder teamAgg= AggregationBuilders.terms(GROUP_ALIAS).field(ASIN_FIELD).order(BucketOrder.aggregation(SUM_ALIAS,false));
        //计算指定字段sum
        teamAgg.subAggregation(AggregationBuilders.sum(SUM_ALIAS).field(orderColumn));
        aggBuilders.add(teamAgg);
        return super.search(EsIndexTypeEnum.DAILY_SELLER_PRODUCT.getIndex(),EsIndexTypeEnum.DAILY_SELLER_PRODUCT.getType(),
                qb,aggBuilders);
    }

}
