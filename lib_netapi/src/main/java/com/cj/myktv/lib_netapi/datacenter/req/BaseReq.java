package com.cj.myktv.lib_netapi.datacenter.req;

import com.cj.myktv.lib_business.DeviceUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 21:06
 */
@Getter
@Setter
public class BaseReq {

    private String cmdid;

    private String mac = DeviceUtils.getSn();
}
