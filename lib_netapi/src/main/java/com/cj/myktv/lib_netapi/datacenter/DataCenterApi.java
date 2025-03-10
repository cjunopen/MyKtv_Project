package com.cj.myktv.lib_netapi.datacenter;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cj.lib_tools.util.rxjava.RxjavaUtils;
import com.cj.myktv.lib_business.DeviceUtils;
import com.cj.myktv.lib_netapi.api.BaseApi;
import com.cj.myktv.lib_netapi.datacenter.okhttp.OkhttpFactory;
import com.cj.myktv.lib_netapi.datacenter.req.BaseReq;
import com.cj.myktv.lib_netapi.datacenter.req.LoginReq;
import com.cj.myktv.lib_netapi.datacenter.req.SongMediaUrlReq;
import com.cj.myktv.lib_netapi.datacenter.resp.BaseResp;
import com.cj.myktv.lib_netapi.datacenter.resp.LoginResp;
import com.cj.myktv.lib_netapi.datacenter.resp.SongMediaUrlResp;
import com.cj.myktv.lib_netapi.datacenter.resp.TokenResp;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/4 22:52
 */
public class DataCenterApi extends BaseApi<DataCenterApi.IDataCenter> {

    private String mValidatecode = "";
    private String mMediaUrlCheckCode;   //获取播放链时校验

    public interface IDataCenter{
        @POST(".")
        @FormUrlEncoded
        Observable<LoginResp> login(@Field("body") String body);

        @POST(".")
        @FormUrlEncoded
        Observable<TokenResp> requestToken(@Field("body") String body);

        @POST(".")
        @FormUrlEncoded
        Observable<SongMediaUrlResp> requestSongMediaUrl(@Field("body") String body);
    }

    /**
     * 请求播放链
     * @param songId
     */
    public void requestSongMediaUrl(int songId){
        SongMediaUrlReq req = new SongMediaUrlReq();
        req.setCmdid("sn_song_media_list");
//        req.setUserid("8527534");
        req.setCode(mMediaUrlCheckCode);
        req.setSongid(StringUtils.format("%08d", songId));
        req.setToken("");

        getApi().requestSongMediaUrl(GsonUtils.toJson(req))
                .compose(RxjavaUtils.obs_io_main())
                .subscribe(new Consumer<SongMediaUrlResp>() {
                    @Override
                    public void accept(SongMediaUrlResp songMediaUrlResp) throws Throwable {
                        checkResp(songMediaUrlResp);
                    }
                });
    }

    private void checkResp(BaseResp resp) throws Exception {
        if (!resp.getErrorcode().equals("0")){
            throw new Exception(StringUtils.format("errorcode: %s, msg: %s", resp.getErrorcode(), resp.getErrormessage()));
        }
    }

    public void init(){
//        login();
    }

    private void requestToken(){
        BaseReq req = new BaseReq();
        req.setCmdid("au_get_token");
        getApi().requestToken(GsonUtils.toJson(req))
                .compose(RxjavaUtils.obs_io_main())
                .subscribe(new Consumer<TokenResp>() {
                    @Override
                    public void accept(TokenResp resp) throws Throwable {
                        checkResp(resp);

                        mMediaUrlCheckCode = resp.getR();

                        requestSongMediaUrl(14883);
                    }
                });
    }

    /**
     * 登录
     */
    private void login(){
        LoginReq req = new LoginReq();
        req.setCmdid("6000");
        req.setMac(DeviceUtils.getSn());
        req.setOs_info("{\"cpuId\":\"0000000000000000\",\"dpi\":\"240\",\"height\":\"1080\",\"macEthernet\":\"bab7aa016cdb\",\"model\":\"C13S\",\"version\":\"6.0.1\",\"with\":\"1920\"}");
        getApi().login(GsonUtils.toJson(req))
                .compose(RxjavaUtils.obs_io_main())
                .subscribe(new Consumer<LoginResp>() {
                    @Override
                    public void accept(LoginResp loginResp) throws Throwable {
                        checkResp(loginResp);

                        mValidatecode = loginResp.getValidatecode();

                        String serverIp = loginResp.getServerip();
                        if (!serverIp.endsWith("/")){
                            serverIp = serverIp + "/";
                        }

                        setBaseUrl(serverIp);

                        requestToken();
                    }
                });
    }

    @Override
    protected void addHeader(Request.Builder builder) {
        builder.addHeader("Accept-Charset", "utf8");
        builder.addHeader("Accept-Encoding", "");
        builder.addHeader("Accept", "text/json");
        builder.addHeader("doubledecode", "");
        builder.addHeader("User-Agent", "C13S_karaoke/3.5.0/1.9.1/android/" + DeviceUtils.getSn());
        builder.addHeader("sessionid", "");
        builder.addHeader("validcode", mValidatecode);
        builder.addHeader("devicetag", DeviceUtils.getSn());
    }

    public DataCenterApi() {
        setBaseUrl("https://keigepro-daily.duochang.cc/Services/Index/");
    }

    public static DataCenterApi getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static DataCenterApi INSTANCE = new DataCenterApi();
    }

    @Override
    protected OkHttpClient.Builder createOkhttpBuilder() {
        OkHttpClient.Builder builder = OkhttpFactory.getBuilder();
        return builder;
    }
}
