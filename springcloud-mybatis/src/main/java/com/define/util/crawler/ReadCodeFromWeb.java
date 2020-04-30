package com.define.util.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 从国家统计局网站爬取2018年12位到村级别的行政区划代码
 *
 * @author
 */

public class ReadCodeFromWeb {
    public static final String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
    //设置utf-8发现有部分字符有乱码
    public static final String CHARSET = "GBK";

    public static StringBuffer result = new StringBuffer();

    /**
     * 读省的信息
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JDBCDao dao = new JDBCDao();
        dao.getConnection();
        String url = baseUrl + "index.html";
        //如果需要设置代理
//        initProxy("10.10.13.200", "80");
        String str = getPageContent(url).toUpperCase();
        String[] arrs = str.split("<A");

        for (String s : arrs) {
            if (s.indexOf("HREF") != -1 && s.indexOf(".HTML") != -1) {

                String a = s.substring(7, s.indexOf("'>"));
                String codeProvince = a.substring(0, 2) + "0000000000";
                String name = s.substring(s.indexOf("'>") + 2, s.indexOf("<BR/>"));
                /*if (!"北京市".equals(name)) {//这行代码代表只抓取云南省
                    continue;
                }*/
                String sql = "insert into sys_area_tab(id,code,name,parent_code,level) values('" + getUUID32() + "','" + codeProvince + "','" + name + "','" + 0 + "','" + 1 + "')";
                dao.updateByPreparedStatement(sql, null);
                readShi(a, dao, codeProvince);
            }
        }
        dao.releaseConn();
    }

    /**
     * 读市的数据
     *
     * @param
     * @throws Exception
     */
    public static void readShi(String url, JDBCDao dao, String codeProvince) throws Exception {
        String content = getPageContent(baseUrl + url).toUpperCase();
        String[] citys = content.split("CITYTR");
        for (int c = 1, len = citys.length; c < len; c++) {
            String[] strs = citys[c].split("<A HREF='");
            String cityUrl = "";
            String cityCode = "";
            String cityName = "";
            for (int j = 1; j < 3; j++) {
                if (j == 1) {//取链接和编码
                    cityUrl = strs[j].substring(0, strs[j].indexOf("'>"));
                    cityCode = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
                } else {
                    cityName = strs[j].substring(strs[j].indexOf("'>") + 2, strs[j].indexOf("</A>"));
                }
            }
            String sql = "insert into sys_area_tab(id,code,name,parent_code,level) values('" + getUUID32() + "','" + cityCode + "','" + cityName + "','" + codeProvince + "','" + 2 + "') ";
            dao.updateByPreparedStatement(sql, null);
            readXian(cityUrl.substring(0, cityUrl.indexOf("/") + 1), cityUrl, cityCode, dao);
        }
    }

    /**
     * 读县区的数据
     *
     * @param url
     * @throws Exception
     */
    public static void readXian(String prix, String url, String cityCode, JDBCDao dao) throws Exception {
        String content = getPageContent(baseUrl + url).toUpperCase();
        String[] citys = content.split("COUNTYTR");
        for (int i = 1; i < citys.length; i++) {
            String areaUrl = null;
            String areaCode = "";
            String areaName = "";
            //发现石家庄有一个县居然没超链接，特殊处理
            if (citys[i].indexOf("<A HREF='") == -1) {

            } else {
                String[] strs = citys[i].split("<A HREF='");
                for (int si = 1; si < 3; si++) {
                    if (si == 1) {//取链接和编码
                        areaUrl = strs[si].substring(0, strs[si].indexOf("'>"));
                        areaCode = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
                    } else {
                        areaName = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
                    }
                }
            }
            String sql = "insert into sys_area_tab(id,code,name,parent_code,level) values('" + getUUID32() + "','" + areaCode + "','" + areaName + "','" + cityCode + "','" + 3 + "') ";
            dao.updateByPreparedStatement(sql, null);
            /*if (null != areaUrl) {
                readZhen(prix, areaUrl, areaCode, dao);
            }*/
        }
    }

    /**
     * 读镇的数据
     *
     * @param url
     * @throws Exception
     */
    /*public static void readZhen(String prix, String url, String areaCode, JDBCDao dao) throws Exception {
        String content = getContent(baseUrl + prix + url).toUpperCase();
        String myPrix = (prix + url).substring(0, (prix + url).lastIndexOf("/") + 1);
        String[] citys = content.split("TOWNTR");
        for (int i = 1; i < citys.length; i++) {
            String[] strs = citys[i].split("<A HREF='");
            String cityUrl = null;
            String townCode = "";
            String townName = "";
            for (int si = 1; si < 3; si++) {
                if (si == 1) {//取链接和编码
                    cityUrl = strs[si].substring(0, strs[si].indexOf("'>"));
                    townCode = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
                } else {
                    townName = strs[si].substring(strs[si].indexOf("'>") + 2, strs[si].indexOf("</A>"));
                }
            }
            String sql = "insert into sys_area_tab(id,code,name,parent_code,level) values('" + getUUID32() + "','" + townCode + "','" + townName + "','" + areaCode + "','" + 4 + "') ";
            dao.updateByPreparedStatement(sql, null);
            readCun(myPrix, cityUrl, townCode, dao);
        }
    }*/

    /**
     * 读村/街道的数据
     *
     * @param
     * @throws Exception
     */
    /*public static void readCun(String prix, String url, String townCode, JDBCDao dao) throws Exception {
        String content = getContent(baseUrl + prix + url).toUpperCase();
        String[] citys = content.split("VILLAGETR");
        for (int i = 1; i < citys.length; i++) {
            String[] strs = citys[i].split("<A HREF='");
            String villageCode = "";
            String villageName = "";
            villageCode = strs[0].substring(strs[0].indexOf("'>") + 6, strs[0].indexOf("</TD>"));
            villageName = strs[0].substring(strs[0].indexOf("'>") + 39, strs[0].indexOf("</TD></TR>"));
            String sql = "insert into sys_area_tab(id,code,name,parent_code,level) values('" + getUUID32() + "','" + villageCode + "','" + villageName + "','" + townCode + "','" + 5 + "') ";
            dao.updateByPreparedStatement(sql, null);
        }
    }*/

    //设置代理
    public static void initProxy(String host, String port) {
        System.setProperty("http.proxyType", "4");
        System.setProperty("http.proxyPort", port);
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxySet", "true");
    }

    //获取网页的内容
    public static String getContent(String strUrl) throws Exception {
        BufferedReader br = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(strUrl);
            inputStream = url.openStream();
            br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(CHARSET)));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println("can't open url:" + strUrl);
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    public static String getPageContent(String url) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        /*RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        //设置请求和传输超时时间,10秒超时
        request.setConfig(requestConfig);*/
        //设置请求头，将爬虫伪装成浏览器
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "GBK");
                return html;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
//                System.out.println(EntityUtils.toString(response.getEntity(), "GBK"));
                System.out.println(response);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }
}