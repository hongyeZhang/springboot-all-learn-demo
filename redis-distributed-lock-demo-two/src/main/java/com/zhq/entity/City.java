package com.zhq.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author ZHQ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {
    private int cityId;

    private String cityName;

    private String cityIntroduce;
}
