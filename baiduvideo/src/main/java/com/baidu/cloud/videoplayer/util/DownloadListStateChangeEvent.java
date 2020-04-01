package com.baidu.cloud.videoplayer.util;

public class DownloadListStateChangeEvent {
    public String flag;
    public String hintName;
    public String key;
    public int state;
    public boolean isMsbRedBg;

    public DownloadListStateChangeEvent(String flag, String key, int state) {
        this.flag = flag;
        this.key = key;
        this.state = state;
    }

    public DownloadListStateChangeEvent(String flag) {
        this.flag = flag;
    }

    public DownloadListStateChangeEvent(String flag, String hintName, boolean isMsbRedBg) {
        this.flag = flag;
        this.hintName = hintName;
        this.isMsbRedBg = isMsbRedBg;
    }
}
