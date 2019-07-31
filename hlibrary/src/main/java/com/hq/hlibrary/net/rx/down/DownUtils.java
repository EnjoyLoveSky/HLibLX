package com.hq.hlibrary.net.rx.down;

import com.hq.hlibrary.net.rx.HttpsUtils;
import com.hq.hlibrary.net.rx.RetryWhenNetworkException;
import com.hq.hlibrary.net.rx.TimeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 下载
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/3  16:44
 */
public class DownUtils {

    private DownloadListener downloadListener;
    private  DownloadObserver downloadObserver;
    private String baseUrl="http://www.oitsme.com/download/";
    private static final long DEFAULT_TIMEOUT = 30;
    private volatile static DownUtils instance;


    public static DownUtils getInstance(){
        if (instance == null) {
            synchronized (DownUtils.class){
                if (instance==null)
                instance=new DownUtils(null);
            }
        }
        return instance;
    }

    public DownUtils(DownloadListener listener){
        this.downloadListener=listener;
    }

    public void downStart(String url){
        if (downloadObserver==null)
        downloadObserver=new DownloadObserver(downloadListener);

        final DownInfo downInfo=new DownInfo();
        downInfo.setCountLength(downloadObserver.getCountLength());
        downInfo.setReadLength(downloadObserver.getReadLength());
//        downInfo.setSavePath(Constant.savePath+"oitsme.apk");
//        if (FileUtils.createOrExistsDir(downInfo.getSavePath())){
//
//        }
        System.out.println("savepath="+downInfo.getSavePath());

        getRetrofit().create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        writeFile(inputStream,downInfo.getSavePath());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(downloadObserver);

    }


    /**
     * 停止下载
     */
    public void stopDown() {
        downloadListener.onDownComplete();
    }



    public Retrofit getRetrofit(){
        return getRetrofit(baseUrl);
    }

    public Retrofit getRetrofit(String baseUrl){
        downloadObserver=new DownloadObserver(downloadListener);

        DownloadInterceptor interceptor=new DownloadInterceptor(downloadObserver);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new GZipInterceptor());
        httpClient.sslSocketFactory(HttpsUtils.createSSLSocketFactory(),new HttpsUtils.TrustAllManager());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit;
    }


    /**
     * 写入文件
     *
     * @param file
     * @throws IOException
     */
    public static void writeCaches(ResponseBody responseBody, File file, DownInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new TimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new TimeException(e.getMessage());
        }
    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private void writeFile(InputStream inputString, String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            downloadListener.onDownError("FileNotFoundException");
        } catch (IOException e) {
            downloadListener.onDownError("IOException");
        }

    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


}
