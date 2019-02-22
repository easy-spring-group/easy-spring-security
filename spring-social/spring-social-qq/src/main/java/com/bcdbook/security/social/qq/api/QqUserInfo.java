package com.bcdbook.security.social.qq.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * qq 用户详情对象
 *
 * @author summer
 * @date 2019-01-24 12:16
 * @version V1.0.0-RELEASE
 */
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QqUserInfo implements Serializable {

    private static final long serialVersionUID = 7076536841722439164L;

    /**
     * 	返回码
     */
    private String ret;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     *
     */
    private String openId;
    /**
     * 不知道什么东西，文档上没写，但是实际api返回里有。
     */
    @JsonProperty(value = "is_lost")
    private String isLost;
    /**
     * 省(直辖市)
     */
    private String province;
    /**
     * 市(直辖市区)
     */
    private String city;
    /**
     * 出生年月
     */
    private String year;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 	用户在 QQ 空间的昵称。
     */
    private String nickname;
    /**
     * 	大小为 30×30 像素的 QQ 空间头像 URL。
     */
    @JsonProperty(value = "figureurl")
    private String figureUrl;
    /**
     * 	大小为50×50像素的QQ空间头像URL。
     */
    @JsonProperty(value = "figureurl_1")
    private String figureUrl1;
    /**
     * 	大小为100×100像素的QQ空间头像URL。
     */
    @JsonProperty(value = "figureurl_2")
    private String figureUrl2;
    /**
     * 	大小为40×40像素的QQ头像URL。
     */
    @JsonProperty(value = "figureurl_qq_1")
    private String figureUrlQq1;
    /**
     * 	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
     */
    @JsonProperty(value = "figureurl_qq_2")
    private String figureUrlQq2;
    /**
     * 	性别。 如果获取不到则默认返回”男”
     */
    private String gender;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）。
     */
    @JsonProperty(value = "is_yellow_vip")
    private String isYellowVip;
    /**
     * 	标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String vip;
    /**
     * 	黄钻等级
     */
    @JsonProperty(value = "yellow_vip_level")
    private String yellowVipLevel;
    /**
     * 	黄钻等级
     */
    private String level;
    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    @JsonProperty(value = "is_yellow_year_vip")
    private String isYellowYearVip;
}
