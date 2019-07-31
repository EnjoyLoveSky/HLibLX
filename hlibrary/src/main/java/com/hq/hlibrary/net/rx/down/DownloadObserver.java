package com.hq.hlibrary.net.rx.down;

import io.reactivex.observers.DefaultObserver;

/**
 * @author ruowuming
 * @version 1.0
 * @date 2018/4/3  17:16
 */
public class DownloadObserver<T> extends DefaultObserver<T> implements DownProgressListener {

    private DownloadListener downloadListener;
    private long readLength;
    private long countLength;


    public DownloadObserver(DownloadListener listener){
        this.downloadListener=listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (downloadListener!=null)
        downloadListener.onDownStart();
    }

    @Override
    public void onNext(T o) {
        if (downloadListener!=null)
        downloadListener.onDownSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("onError=="+e.getMessage());
        if (downloadListener!=null)
        downloadListener.onDownError(e.getMessage());
    }

    @Override
    public void onComplete() {
        if (downloadListener!=null)
        downloadListener.onDownComplete();
    }

    @Override
    public void onProgress(long read, long count, boolean done) {
        System.out.println("onProgress-readOld="+read);
        System.out.println("onProgress-countOld="+count);
//        if(downInfo.getCountLength()>count){
//            read=downInfo.getCountLength()-count+read;
//        }else{
//            downInfo.setCountLength(count);
//        }
//        downInfo.setReadLength(read);
        if (getCountLength()>count){
            read=getCountLength()-count+read;
        }else {
            setCountLength(count);
        }
        setReadLength(read);
        System.out.println("onProgress-readNew="+getReadLength());
        System.out.println("onProgress-countNew="+getCountLength());
        if (downloadListener!=null)
            downloadListener.onDownProgress((int) (read*100/count));
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

}
