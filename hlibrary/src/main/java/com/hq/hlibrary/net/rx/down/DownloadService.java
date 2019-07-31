package com.hq.hlibrary.net.rx.down;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/3  10:50
 */
public interface DownloadService {

    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);
}
