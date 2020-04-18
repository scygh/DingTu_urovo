/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lianxi.dingtu.dingtu_urovo.app.api;


/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>http://dev.machine_api.dt128.com
 * http://machine_api.dt128.com
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    String APP_DOMAIN = "http://open.api.dt128.com";
    int RequestSuccess = 200;

    String BAIDU_DOMAIN_NAME = "baidu";
    String BAIDU_DOMAIN = "https://aip.baidubce.com";

    String EASYDL_DOMNAIN_NAME = "easydl";
    String EASYDL_DOMNAIN = "http://open.api.dt128.com";
    String ReturnSuccess = "0";

    String UPDATECENTER_NAME = "UpdateCenter";
    String UPDATECENTER_DOMAIN = "http://updatecenter.dt128.com";

    /*String APP_DOMAIN = "http://dev.open.api.dt128.com";
    String EASYDL_DOMNAIN = "http://dev.open.api.dt128.com";*/

}