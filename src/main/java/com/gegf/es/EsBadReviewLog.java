package com.gegf.es;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;


@Component
public class EsBadReviewLog extends BaseEsClient{

    private static final String REVIEW_DATE = "review_date";
    private static final String SELLER_IDS = "review_product_asin_seller_id";
    private static final String REVIEW_PRODUCT_ASIN = "review_product_asin";
    private static final String REVIEW_PRODUCT_TITLE = "review_product_title";

    public SearchResult queryBadReviewBySellerId(String sellerId,String keyword,Integer page, Integer size){
//        QueryBuilder  qb =  QueryBuilders.termQuery(SELLER_IDS,sellerId);
//        QueryBuilder  qb = QueryBuilders.wildcardQuery(SELLER_IDS,"*"+sellerId+"*");
        QueryBuilder  qb = QueryBuilders.termsQuery(SELLER_IDS,sellerId);
        //时间排序
        SortBuilder sortBuilder = SortBuilders.fieldSort(REVIEW_DATE).order(SortOrder.DESC);
        return super.search(EsIndexTypeEnum.BAD_REVIEW.getIndex(),EsIndexTypeEnum.BAD_REVIEW.getType(),
                qb, sortBuilder, page, size);
    }

}
