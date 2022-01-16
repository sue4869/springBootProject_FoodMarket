package com.psc.sample.oauthlogin.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psc.sample.oauthlogin.domain.BaseEntity;
import com.psc.sample.oauthlogin.helper.Indices;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Document(indexName = Indices.ORDERED_PRODUCT_INDEX)
@Setting(settingPath = "static/es-settings.json")
@Data
public class OPSearch {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Text)
    private String deliveryStatus;

    @Field(type = FieldType.Text)
    private String price;

    @Field(type = FieldType.Text)
    private String goodName;

    @Field(type = FieldType.Text)
    private String image;

    @Field(type = FieldType.Integer)
    private int count;

    @Field(type = FieldType.Text)
    private String requestMessage;

    @Field(type = FieldType.Text)
    private String sendNum;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date regDate;

}
