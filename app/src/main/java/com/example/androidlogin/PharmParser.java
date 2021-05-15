package com.example.androidlogin;

import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class PharmParser {
    EditText edit;
    TextView text;

    XmlPullParser xpp;
    String key = "45tjino2la7c%2BD%2BQNIm5f%2B9NAsZ7YLaBeMQ4kYuj6wXR1V%2FtNkMTJlir%2BO5%2FEmAXQ7gaV3mgFTtEsaK%2FjfCMrw%3D%3D"; //약국 공공데이터 서비스키


    String pharmname;
    String pharmadd;
    String pharmtel;

    public String getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = null;
        try {
            location = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyLcinfoInqire?serviceKey="//요청 URL
                + key +"&numOfRows=100" + "&emdongNm=" + location; //동 이름으로 검색

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            pharmname="";
            pharmadd="";
            pharmtel="";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 태그값이랑 비교
                        else if (tag.equals("addr")) {
                            xpp.next();
                            pharmadd = xpp.getText();
                        } else if (tag.equals("yadmNm")) {
                            buffer.append("\n");
                            xpp.next();
                            pharmname = xpp.getText();
                            buffer.append("약국명 : " + pharmname +"\n" + "주소 : " + pharmadd + "\n" + "전화번호 : " + pharmtel);
                            buffer.append("\n"+"_____________________________________________________");
                        } else if (tag.equals("telno")) {
                            xpp.next();
                            pharmtel = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기
                        if (tag.equals("item"))
                            buffer.append("\n");// 첫번째 검색결과종료 후 줄바꿈
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }
}