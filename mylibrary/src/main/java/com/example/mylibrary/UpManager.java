package com.example.mylibrary;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by guhongliang on 2017/9/26.
 * 文件上传管理类
 */

public class UpManager {
    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public static Object[] downloadSingleFile(String url) {
        return new Object[0];
    }

    /**
     * 上传文件
     *
     * @param file
     * @param request
     */
    public static String uploadFile(File file, String request) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString();//边界标识  随机生成
        String LINE_END = "\r\n";
        if (file == null) {
            return null;
        }
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);//允许输入流
            conn.setDoOutput(true);//允许输出流
            conn.setUseCaches(false);//不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");//设置编码
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content_Type", "multipart/form-data;boundary=" + BOUNDARY);

            /**
             * 文件不为空，则进行包装上传
             */
            DataOutputStream bos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = new StringBuffer();
            /**
             * name里为服务端需要的key值，only this key can make the server get the file
             * filename是文件的名字，包含后缀名，如：abc.txt
             */
            sb.append("Content-Disposition: form-data; name=\"uploadcrash\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream; charset=" + "UTF-8" + LINE_END);
            sb.append(LINE_END);
            bos.write(sb.toString().getBytes());

            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            is.close();

            bos.write(LINE_END.getBytes());
            bos.write(("--" + BOUNDARY + LINE_END).getBytes());
            bos.flush();
            /**
             * 获取响应码
             * 当响应成功，获取响应的流
             */
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                int ss;
                while ((ss = inputStream.read()) != -1) {
                    stringBuffer.append((char) ss);
                }
                result = stringBuffer.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
