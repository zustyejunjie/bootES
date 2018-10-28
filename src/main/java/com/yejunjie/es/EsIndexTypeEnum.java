package com.yejunjie.es;

/**
 * @author hanyuting
 * @since 2018/10/16
 */
public enum EsIndexTypeEnum {
    /**
     * buy box
     */
    BUY_BOX("selection_buybox_monitor_log_index","selection_buybox_monitor_log_index"),
    /**
     * 跟卖
     */
    FOLLOW_SALE("selection_followsale_monitor_log_index","selection_followsale_monitor_log_index"),
    /**
     * bsr
     */
    BSR("selection_product_seller_bsr","selection_product_seller_bsr"),
    /**
     * 类目
     */
    RANK("ads_sp_seller_bsr_rank_sh","ads_sp_seller_bsr_rank_sh"),
    /**
     * deal
     */
    DEAL("ads_sp_seller_lightning_deal_sh","ads_sp_seller_lightning_deal_sh"),
    /**
     * 商品
     */
    PRODUCT("selection_amazon_com_seller_product_info","selection_amazon_com_seller_product_info"),
    /**
     * 每日商品(店铺运营分析)
     */
    DAILY_SELLER_PRODUCT("ads_sp_seller_asin_index_sd","ads_sp_seller_asin_index_sd"),
    /**
     * 差评
     */
    BAD_REVIEW("selection_amazon_com_seller_badreview","selection_amazon_com_seller_badreview"),
    /**
     * Listing
     */
    LISTING("ads_sp_seller_listing_variation_sh","ads_sp_seller_listing_variation_sh"),
    ;
    private String index;
    private String type;
    EsIndexTypeEnum(String index, String type){
        this.index = index;
        this.type = type;
    }
    public String getIndex(){
        return this.index;
    }
    public String getType(){
        return this.type;
    }
}
