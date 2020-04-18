package com.lianxi.dingtu.dingtu_urovo.app.api;

public interface AppConstant {

    interface Api {
        String TOKEN = "token";
        String USERID = "userid";
        String BAIDU_TOKEN = "baidu_token";
        String PASSWORD = "password";
        String BLOCK_USER = "block_user";
        String BLOCK_REPORT = "block_report";
        String BLOCK_SETUP = "block_setup";

    }

    interface Receipt {
        String NO = "mac_number";
        String NAME = "receipt_name";
        String ADDRESS = "receipt_address";
        String PHONE = "receipt_phone";
        String isPrint = "print_state";
    }

    interface Print {
        String STUB1 = "stub1";
        String STUB2 = "stub2";
        String COST = "auto_cost";
    }

    interface ActivityIntent {
        String MODEL_DEPOSIT = "deposit";
        String MODEL_EXPENSE = "expense";
        String STEP1 = "step1";
        String STEP2 = "step2";
        String STEP3 = "step3";
    }

    interface NFC {
        String NFC_KEY = "nfc_key";
        String BALANCE = "balance";
    }

    interface KeyValue {
        String KEY_USER_INFO = "userInfo";
        String KEY_IS_LOGIN_INFO = "info";
    }

    String ACTIVITY_FRAGMENT_REPLACE = "ActivityFragmentReplace";

}
