package com.hq.hlibrary.net.rx;

/**
 * 请求统一返回消息体
 * @author ruowuming
 * @version 1.0
 * @date 2018/3/29  11:29
 */
public class RestFulX<T> {

    /**
     * code : 200
     * message : success
     * data :
     */

    private int ret;
    private int error;
    private String message;
    private String url;
//    @SerializedName(value = "data", alternate = {"list","is_collection","leadList","province","collection_list"})
    private T data;
    private int count;
    private String newVersion;
    private String apk_url;
    private String apk_update_desc;


    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "RestFulX{" +
                "ret=" + ret +
                ", error=" + error +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", data=" + data +
                '}';
    }


    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getApk_update_desc() {
        return apk_update_desc;
    }

    public void setApk_update_desc(String apk_update_desc) {
        this.apk_update_desc = apk_update_desc;
    }
}
