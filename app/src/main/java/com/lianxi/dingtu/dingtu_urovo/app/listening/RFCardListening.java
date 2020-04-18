package com.lianxi.dingtu.dingtu_urovo.app.listening;

public interface RFCardListening {
    void findRFCardListening(String s);

    void readRFCardListening(String data);

    void noFindRFCardListening();

    void failReadRFCardListening();
}
