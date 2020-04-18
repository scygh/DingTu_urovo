package com.lianxi.dingtu.dingtu_urovo.app.entity;

public class VersionTo {

    /**
     * HighestVersion : 2
     * HighestFileName : Android_EM_2.apk
     * DownloadURI : /VersionRepository/android/em/Android_EM_2.apk
     */

    private int HighestVersion;
    private String HighestFileName;
    private String DownloadURI;

    public int getHighestVersion() {
        return HighestVersion;
    }

    public void setHighestVersion(int HighestVersion) {
        this.HighestVersion = HighestVersion;
    }

    public String getHighestFileName() {
        return HighestFileName;
    }

    public void setHighestFileName(String HighestFileName) {
        this.HighestFileName = HighestFileName;
    }

    public String getDownloadURI() {
        return DownloadURI;
    }

    public void setDownloadURI(String DownloadURI) {
        this.DownloadURI = DownloadURI;
    }
}
