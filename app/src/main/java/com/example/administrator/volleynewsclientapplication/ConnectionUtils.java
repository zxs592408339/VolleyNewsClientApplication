package com.example.administrator.volleynewsclientapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ConnectionUtils {
    public static final String SHARE_PATER_CACHE_NAME = "com.example.administrator.newsclientapplication";
    private Context mContext;

        public static boolean isNetAvailable(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return (info != null && info.isAvailable());
        }

        public enum Method {
        GET, POST
    }

    public enum Cache {
        TRUE, FALSE
    }

    public ConnectionUtils(Context context) {
        mContext = context;
    }

    public interface HttpConnectionInterface {
        void execute(String content);
    }

    /**
     * 无参无缓存请求
     */
    public void asycnConnect(final String httpUrl, final Method method, final HttpConnectionInterface httpConnectionInterface) {
        asyncConnect(httpUrl, null, method, Cache.FALSE, httpConnectionInterface);
    }

    /**
     * 无参有缓存
     */
    public void asycnConnect(final String httpUrl, final Method method, Cache isCache, final HttpConnectionInterface httpConnectionInterface) {
        asyncConnect(httpUrl, null, method, isCache, httpConnectionInterface);
    }

    /**
     * 有参无缓存
     */
    public void asycnConnect(final String httpUrl, final HashMap<String, String> paramtMap, final Method method, final HttpConnectionInterface httpConnectionInterface) {
        asyncConnect(httpUrl, paramtMap, method, Cache.FALSE, httpConnectionInterface);
    }

    /**
     * 有参有缓存请求
     * 异步联网获取Http响应字符串数据
     */
    public void asyncConnect(final String httpUrl, final HashMap<String, String> paramtMap, final Method method, final Cache isCache, final HttpConnectionInterface httpConnectionInterface) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String httpUrl = params[0];
                return doHttpConnection(httpUrl, paramtMap, method, isCache);
            }

            @Override
            protected void onPostExecute(String content) {
                httpConnectionInterface.execute(content);
            }
        }.execute(httpUrl);
    }

    public String doHttpConnection(String httpUrl, HashMap<String, String> paramtMap, Method method, Cache isCache) {
        if (method == Method.GET) {
            if (paramtMap != null) {
                String paramUrl = "?";
                paramUrl = doParameterHttp(paramUrl, paramtMap); //?userName=admin1&passWord=1234566
                httpUrl = httpUrl + paramUrl; // requestUrl = http://192.168.5.11:8080/webapp/login?userName=admin1&passWord=1234566
            }
            return doGetPostHttp(httpUrl, paramtMap, method, isCache);
        } else {
            return doGetPostHttp(httpUrl, paramtMap, method, isCache);
        }
    }

    public String doGetPostHttp(String httpUrl, HashMap<String, String> paramtMap, Method method, Cache isCache) {
        String message = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (method == Method.GET) {
                urlConnection.setRequestMethod("GET");  //请求方法 GET POST
            } else {
                urlConnection.setRequestMethod("POST");  //请求方法 GET POST
            }
            urlConnection.setConnectTimeout(10000); //连接超时时间
            urlConnection.setReadTimeout(15000);    //读数据超时
            urlConnection.connect();

            if (method == Method.POST) {
                // post请求的参数
                if (paramtMap != null) {
                    String data = doParameterHttp("", paramtMap); //userName=admin&passWord=1234566
                    // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
                    OutputStream out = urlConnection.getOutputStream();// 获得一个输出流,向服务器写数据
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                }
            }

            int code = urlConnection.getResponseCode();  //状态行:状态码 200 OK
            Log.e("code ","" + code);
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                message = readInput(is);
                if (isCache == Cache.TRUE)
                    mContext.getSharedPreferences(SHARE_PATER_CACHE_NAME, Context.MODE_PRIVATE).edit().putString(httpUrl, message).commit(); //添加缓存

            }
        } catch (MalformedURLException ignored) {
        } catch (IOException e) {
            return mContext.getSharedPreferences(SHARE_PATER_CACHE_NAME, Context.MODE_PRIVATE).getString(httpUrl, null);
        } finally {
            urlConnection.disconnect();
        }
        return message;
    }


    public String doParameterHttp(String paramUrl, HashMap<String, String> paramtMap) {
        for (String key : paramtMap.keySet()) {
            String value = paramtMap.get(key);
            paramUrl = paramUrl + key + "=" + value + "&";
        }
        paramUrl = paramUrl.substring(0, paramUrl.length() - 1);
        return paramUrl;
    }

    /**
     * 输入流转字符串
     */
    public String readInput(InputStream is) {
        Reader reader = new InputStreamReader(is);  //字节转字符流
        BufferedReader br = new BufferedReader(reader); //字符缓存流
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                reader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
