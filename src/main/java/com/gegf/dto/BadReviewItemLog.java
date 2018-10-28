package com.gegf.dto;

import lombok.Data;

import java.util.Date;

/**
 * es 中对应结构
 * @author: junjieye
 * @since: 2018/10/23
 * @des
 */
@Data
public class BadReviewItemLog {

  private String id;
  private String day;
  private String hour;
  private String review_id;
  private String review_title;
  private String review_buyer_id;
  private String review_buyer_name;
  private String review_body;
  private Integer review_helpful;
  private Integer review_rating;
  private Date review_date;
  private Boolean review_vp;
  private Integer review_variant;
  private Double review_product_rating;
  private Integer review_product_reviews;
  private Integer review_product_negatives;
  private String review_product_title;
  private String review_product_image;
  private String review_product_asin;
  private String review_product_asin_seller_id;
  private Date collect_time;
  private Date created;

}
