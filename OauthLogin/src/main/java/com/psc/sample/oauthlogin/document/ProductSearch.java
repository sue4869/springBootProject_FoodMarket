package com.psc.sample.oauthlogin.document;

import com.psc.sample.oauthlogin.helper.Indices;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(indexName = Indices.PRODUCT_INDEX)
@Setting(settingPath = "static/es-settings.json")
@Data
public class ProductSearch {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Text)
    private String goodName;

    private int amount;

    @Field(type = FieldType.Text , fielddata = true)
    private String price;

    // elastic search 에서는 text 가 sorting 을 해주지않는다
    // 그래서 옵션을 @Field(type= FieldType.Keyword) 또는 @Field(type = FieldType.Text, fielddata = true)
    // 을 해줘야 먹히는다고 하는데 아무리해도 안된다. 그래서 sorting 해주기위해서 만든 컬럼이다.
    @Field(type = FieldType.Integer)
    private int value;

    @Field(type = FieldType.Integer)
    private int discount;

    @Field(type = FieldType.Text)
    private String big_catagory;

    @Field(type = FieldType.Text)
    private String small_catagory;

    @Field(type = FieldType.Integer)
    private int heartNumber;

    @Field(type = FieldType.Text)
    private String image;

    @Field(type = FieldType.Text)
    private String origin;

    @Field(type = FieldType.Text)
    private String weight;

    @Field(type = FieldType.Text)
    private String expiration;

    @Field(type = FieldType.Text)
    private String detailimagefirst;

    @Field(type = FieldType.Text)
    private String detailimagesecond;

    @Field(type = FieldType.Text)
    private String detailcontext;

    @Field(type = FieldType.Date)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;
}
