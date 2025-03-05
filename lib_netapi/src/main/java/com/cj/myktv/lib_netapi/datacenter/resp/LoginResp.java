package com.cj.myktv.lib_netapi.datacenter.resp;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 20:58
 */
@Getter
@Setter
public class LoginResp extends BaseResp{

    private String serverip;

    private String validatecode;


}
