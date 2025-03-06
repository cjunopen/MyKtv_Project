package com.cj.myktv.lib_netapi.datacenter.req;

import com.cj.myktv.lib_netapi.datacenter.resp.BaseResp;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 22:36
 */
@Getter
@Setter
public class SongMediaUrlReq extends BaseReq {

    private String songid;

    private String userid;

    private String code;

    private String token;
}
