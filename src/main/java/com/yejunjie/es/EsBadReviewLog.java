package com.yejunjie.es;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Component
public class EsBadReviewLog extends BaseEsClient{

    private static final String REVIEW_DATE = "review_date";
    private static final String SELLER_IDS = "review_product_asin_seller_id";
    private static final String REVIEW_PRODUCT_ASIN = "review_product_asin";
    private static final String REVIEW_PRODUCT_TITLE = "review_product_title";

    public SearchResult queryBadReviewBySellerId(String sellerId,Collection<String> sellerList,
                                                 String keyword, Integer page, Integer size){
        BoolQueryBuilder qb = boolQuery();
        //sellerId为空 查询该用户下全部店铺的差评
        if(StringUtils.isNotEmpty(sellerId)){
            qb.filter(termsQuery(SELLER_IDS,sellerId));
        }else{
            qb.filter(termsQuery(SELLER_IDS,sellerList));
        }
        //关键字搜索
        if(StringUtils.isNotEmpty(keyword)){
            qb.must(
                    boolQuery()
                            .should(termQuery(REVIEW_PRODUCT_ASIN,keyword))
                            .should(matchPhraseQuery(REVIEW_PRODUCT_TITLE,keyword))
            );
        }
        //时间排序
        SortBuilder sortBuilder = SortBuilders.fieldSort(REVIEW_DATE).order(SortOrder.DESC);
        return super.search(EsIndexTypeEnum.BAD_REVIEW.getIndex(),EsIndexTypeEnum.BAD_REVIEW.getType(),
                qb, sortBuilder, page, size);
    }




}
