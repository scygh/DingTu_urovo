package com.lianxi.dingtu.dingtu_urovo.app.Utils.card;


import android.device.PiccManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lianxi.dingtu.dingtu_urovo.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_urovo.app.listening.RFCardListening;

import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;

import static com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils.concatAll;
import static com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils.getXor;
import static com.lianxi.dingtu.dingtu_urovo.app.Utils.card.BytesUtils.str2Bcd;

public class ReadCardUtils {

    boolean isClose = false;

    public ReadCardUtils() {
    }

    private int scan_card = -1;
    private int SNLen = -1;
    private PiccManager piccManager;
    private String TAG = "ReadCardUtils";

    public static final int MSG_AUTHEN_FAIL = 2;

    public static final int MSG_WRITE_SUCCESS = 3;

    public static final int MSG_WRITE_FAIL = 4;

    public static final int MSG_READ_FAIL = 5;

    public static final int MSG_READ_SUCCSS = 6;

    public static final int MSG_ACTIVE_FAIL = 7;

    public static final int MSG_APDU_FAIL = 8;

    public static final int MSG_SHOW_APDU = 9;

    public static final int MSG_BLOCK_DATA_NONE = 10;

    public static final int MSG_AUTHEN_BEFORE = 11;

    public static final int MSG_FOUND_UID = 12;

    public static final int MSG_NOFOUND_UID = 13;


    public RFCardListening RFCardListening = null;

    public void setRFCardListening(RFCardListening RFCardListening) {
        this.RFCardListening = RFCardListening;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FOUND_UID:
                    if (RFCardListening != null) {
                        RFCardListening.findRFCardListening((String) msg.obj);
                    }
                    break;
                case MSG_NOFOUND_UID:
                    if (RFCardListening != null) {
                        RFCardListening.noFindRFCardListening();
                    }
                    break;
                case MSG_READ_SUCCSS:
                    String data = (String) msg.obj;
                    if (RFCardListening != null&&data.length()>0&&data!="") {
                        RFCardListening.readRFCardListening(data);
                    }
                    break;
                case MSG_READ_FAIL:
                    if (RFCardListening != null) {
                        RFCardListening.failReadRFCardListening();
                    }
                    break;
            }
        }
    };

    Timer timer;
    TimerTask task;
    public void run() {
        task = new TimerTask() {
            @Override
            public void run() {
                check();
                Message message = new Message();
                handler.sendMessage(message);
            }
        };

        timer = new Timer();
        timer.schedule(task, 500, 500);//每五秒执行一次handler
    }

    public void init() {
        piccManager = new PiccManager();
        piccManager.open();
    }

    public void check() {
        Log.d(TAG, "SNLen = ========================");
        byte CardType[] = new byte[2];
        byte Atq[] = new byte[14];
        char SAK = 1;
        byte sak[] = new byte[1];
        sak[0] = (byte) SAK;
        byte SN[] = new byte[10];
        scan_card = piccManager.request(CardType, Atq);
        if (scan_card > 0) {
            SNLen = piccManager.antisel(SN, sak);
            Log.d(TAG, "SNLen = " + SNLen);
            Message msg = handler.obtainMessage(MSG_FOUND_UID);
            msg.obj = bytesToHexString(SN, SNLen);
            handler.sendMessage(msg);
        }else {
            Message msg = handler.obtainMessage(MSG_NOFOUND_UID);
            msg.obj = bytesToHexString(SN, SNLen);
            handler.sendMessage(msg);
        }
    }

    public CardInfoBean cardInfoBean(String data) {
        CardInfoBean mCardInfoBean = new CardInfoBean();
        String num = data.substring(0, 6);
        mCardInfoBean.setNum(Integer.parseInt(num, 16));
        mCardInfoBean.setName(BytesUtils2.getString(BytesUtils.hexStringToBytes(data.substring(8, 26))));
        String code = data.substring(26, 30);
        mCardInfoBean.setCode(code);
        String level_type = data.substring(30);
        mCardInfoBean.setType(Integer.parseInt(level_type.substring(1), 16));
        mCardInfoBean.setLevel(Integer.parseInt(level_type.substring(0, 1), 16));
        return mCardInfoBean;
    }

    public void close() {
        Log.e(TAG, "close: 关");
        timer.cancel();
        task.cancel();
        piccManager.close();
    }
    byte keyBuf[] = {
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
    };

    public boolean Authen(int block_num, String key) {
        int ret = -1;
        byte SN[] = new byte[10];
        if (!key.equals("") && isHexAnd16Byte(key)) {
            byte[] keyData = hexStringToBytes(key);
            ret = piccManager.m1_keyAuth(0, block_num, keyData.length, keyData, SNLen, SN);
            return true;
        } else {
            ret = piccManager.m1_keyAuth(0, block_num, 6, keyBuf, SNLen, SN);

            return false;
        }
    }

    public void Read(int block_num) {
        byte pReadBuf[] = new byte[16];
        int result = piccManager.m1_readBlock(block_num, pReadBuf);
        if (result == -1) {
            handler.sendEmptyMessage(MSG_READ_FAIL);
        } else {
            Message msg = handler.obtainMessage(MSG_READ_SUCCSS);
            msg.obj = bytesToHexString(pReadBuf, result);
            handler.sendMessage(msg);
        }
    }

    public boolean Write(CardInfoBean cardInfoBean) {
        /*
         * String str = etBlockNo.getText().toString(); if(str ==
         * null || str.equals("")){
         * handler.sendEmptyMessage(MSG_BLOCK_NO_NONE); return; }
         * int blkNo = Integer.valueOf(str); if(blkNo < 0 || blkNo >
         * 63){ handler.sendEmptyMessage(MSG_BLOCK_NO_ILLEGAL);
         * return; }
         */
        // byte SN[] = new byte [10];
        int iLenWriteBuf = 16;
        /*
         * int ret = piccReader.M1_KeyAuth(0, blkNo, 6, keyBuf,
         * SNLen, SN); if(ret == RspCode.RSPERR){
         * handler.sendEmptyMessage(MSG_AUTHEN_FAIL); return; }
         */
        if (cardInfoBean == null || cardInfoBean.equals("")) {
            return false;
        }
        int result4 = piccManager.m1_writeBlock(4, data_BLOCK4(cardInfoBean).length,
                data_BLOCK4(cardInfoBean));
        int result5 = piccManager.m1_writeBlock(5, data_BLOCK5(cardInfoBean).length,
                data_BLOCK5(cardInfoBean));
        int result6 = piccManager.m1_writeBlock(6, data_BLOCK6(cardInfoBean).length,
                data_BLOCK6(cardInfoBean));
        if (result4 == 0 && result5 == 0 && result6 == 0) {
            Log.e(TAG, "Write: 4 " + result4 + " 5 " + result5 + " 6 " + result6);
            return true;
        } else {
            return false;

        }
    }

    private byte[] data_BLOCK4(CardInfoBean cardInfoBean) {
        byte[] head = intToByte3(cardInfoBean.getNum());
        byte[] head_check = SumCheck(intToByte3(cardInfoBean.getNum()), 1);

        byte[] b_gbk = BytesUtils2.getBytes(cardInfoBean.getName().trim());
        byte[] b_9_gbk = (byte[]) arrayAddLength(b_gbk, 9 - b_gbk.length);
        byte[] body = hexStringToBytes(cardInfoBean.getCode());

        String s_level = Integer.toHexString(cardInfoBean.getLevel());
        String s_type = Integer.toHexString(cardInfoBean.getType());
        byte[] bottom = hexStringToBytes(s_level + s_type);
        byte[] totle1 = concatAll(head, head_check, b_9_gbk, body, bottom);
        return totle1;
    }

    private byte[] data_BLOCK5(CardInfoBean cardInfoBean) {
        int a = (int) cardInfoBean.getCash_account() * 100;
        byte[] byte_a = intToByte3(a);
        byte[] head_check_a = SumCheck(byte_a, 1);
        int b = (int) cardInfoBean.getAllowance_account() * 100;
        byte[] byte_b = intToByte3(b);
        byte[] head_check_b = SumCheck(byte_b, 1);

        String s = cardInfoBean.getSpending_time();
        s = s.replace("-", "");
        byte[] byte_c = str2Bcd(s.substring(2));
        byte[] byte_d = ubyteToBytes(cardInfoBean.getMeal_times());
        byte[] byte_xfcs = unsignedShortToByte2(cardInfoBean.getConsumption_num());
        byte[] byte_Xor = new byte[]{getXor(byte_xfcs)};
        byte[] totle1 = concatAll(byte_a, head_check_a, byte_b, head_check_b, byte_c, byte_d, new byte[]{(byte) 0x00}, byte_xfcs, byte_Xor);
        return totle1;
    }

    private byte[] data_BLOCK6(CardInfoBean cardInfoBean) {
        byte[] xfxe = intToByte3(cardInfoBean.getSpending_limit() * 100);
        byte[] bded = unsignedShortToByte2(cardInfoBean.getGuaranteed_amount());
        String cv = cardInfoBean.getCard_validity();
        cv = cv.replace("-", "");
        byte[] byte_cv = str2Bcd(cv.substring(2));
        String st = cardInfoBean.getSubsidies_time();
        st = st.replace("-", "");
        byte[] byte_st = str2Bcd(st.substring(2));

        byte X;
        boolean Y = false;
        X = (byte) (Y ? 0x01 : 0x00);
        byte[] bx = new byte[]{X};

        byte[] d = ubyteToBytes(cardInfoBean.getDiscount());
        byte[] totle1 = concatAll(xfxe, bded, byte_cv, byte_st, bx, d, new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        return totle1;
    }

    /**
     * int整数转换为1字节的byte数组
     *
     * @param -b整数
     * @return byte数组
     */
    public static byte[] ubyteToBytes(int n) {
        byte[] b = new byte[1];
        b[0] = (byte) (n & 0xff);
        return b;
    }

    /**
     * short整数转换为2字节的byte数组
     *
     * @param s short整数
     * @return byte数组
     */
    public static byte[] unsignedShortToByte2(int s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    /**
     * Description: Array add length
     *
     * @param oldArray
     * @param addLength
     * @return Object
     */
    public static Object arrayAddLength(Object oldArray, int addLength) {
        Class c = oldArray.getClass();
        if (!c.isArray()) return null;
        Class componentType = c.getComponentType();
        int length = Array.getLength(oldArray);
        int newLength = length + addLength;
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(oldArray, 0, newArray, 0, length);
        return newArray;
    }

    /**
     * 校验和
     *
     * @param msg    需要计算校验和的byte数组
     * @param length 校验和位数
     * @return 计算出的校验和数组
     */
    public static byte[] SumCheck(byte[] msg, int length) {
        long mSum = 0;
        byte[] mByte = new byte[length];

        /** 逐Byte添加位数和 */
        for (byte byteMsg : msg) {
            long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */

        /** 位数和转化为Byte数组 */
        for (int liv_Count = 0; liv_Count < length; liv_Count++) {
            mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
        } /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

        return mByte;
    }

    /**
     * int整数转换为3字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public static byte[] intToByte3(int i) {
        byte[] targets = new byte[3];
        targets[2] = (byte) (i & 0xFF);
        targets[1] = (byte) (i >> 8 & 0xFF);
        targets[0] = (byte) (i >> 16 & 0xFF);
        return targets;
    }

    public static boolean isHexAnd16Byte(String hexString) {
        if (hexString.matches("[0-9A-Fa-f]+") == false) {
            // Error, not hex.

            return false;
        }
        return true;
    }

    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        if (len <= 0) {
            return "";
        }
        for (int i = 0; i < len; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
