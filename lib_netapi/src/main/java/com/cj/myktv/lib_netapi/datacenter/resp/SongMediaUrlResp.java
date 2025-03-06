package com.cj.myktv.lib_netapi.datacenter.resp;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 22:35
 */
@Getter
@Setter
public class SongMediaUrlResp extends BaseResp{

    private List<Media> medialist;

    private class Media{
        private String url;
    }
}
