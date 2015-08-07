package com.wmzl.wehelp;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by leo on 15/8/4.
 */
public class SiteStatisticsManager {
    String res = "";
    Context mContext;

    private static SiteStatisticsManager instance;

    private SiteStatisticsManager() {
    }

    public static SiteStatisticsManager getInstance() {
        if (instance == null) {
            instance = new SiteStatisticsManager();
        }
        return instance;
    }


    public String GetSitePVUV(Context context, List<String> list) {
        mContext = context;

        for (String item : list) {
            /**
             * 请不要添加key参数.
             */
            Parameters params = new Parameters();
            final String siteName = item;
            params.add("site", siteName);
            params.add("dtype", "json");
            /**
             * 请求的方法 参数: 第一个参数 当前请求的context;
             * 				  第二个参数 接口id;
             * 				  第三个参数 接口请求的url;
             * 				  第四个参数 接口请求的方式;
             * 				  第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型;
             * 				  第六个参数请求的回调方法,com.thinkland.sdk.android.DataCallBack;
             *
             */
            JuheData.executeWithAPI(mContext, 51, "http://v.juhe.cn/alexa/query",
                    JuheData.GET, params, new DataCallBack() {
                        /**
                         * 请求成功时调用的方法 statusCode为http状态码,responseString为请求返回数据.
                         */
                        @Override
                        public void onSuccess(int statusCode, String responseString) {
                            String result = "";
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                result += "\n" + siteName;
                                result += "\nDay PV Count:";
                                result += jsonObject.getJSONObject("result").getString("daypv");
                                result += "\nDay IP Count:";
                                result += jsonObject.getJSONObject("result").getString("dayip");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            res = result + "\n";
                        }

                        /**
                         * 请求完成时调用的方法,无论成功或者失败都会调用.
                         */
                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub
                            Toast.makeText(mContext, "finish",
                                    Toast.LENGTH_SHORT).show();
                        }

                        /**
                         * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
                         * statusCode:30002 没有检测到当前网络.
                         * 			  30003 没有进行初始化.
                         * 			  0 未明异常,具体查看Throwable信息.
                         * 			  其他异常请参照http状态码.
                         */
                        @Override
                        public void onFailure(int statusCode,
                                              String responseString, Throwable throwable) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
        return res;
    }

}
