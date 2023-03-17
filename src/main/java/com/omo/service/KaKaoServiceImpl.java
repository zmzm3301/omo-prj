package com.omo.service;

import com.google.gson.JsonParser;
import com.omo.dao.KPersonRepository;
import com.omo.dto.KPerson;
import com.google.gson.JsonElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Service
public class KaKaoServiceImpl implements KaKaoService{
	
	@Autowired
	KPersonRepository dao;

    public String getKakaoAccessToken (String code) {
    	String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            System.out.println(conn);
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=8ef1d7ff4079cc081e948ea0988aafea");
            sb.append("&redirect_uri=http://localhost:3000/klogin");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        String ar = access_Token + " " + refresh_Token;
        return ar;
    }
    
    public HashMap<String, Object> getUserInfo (String access_Token) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            int id = element.getAsJsonObject().get("id").getAsInt();
            String nickName = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            userInfo.put("id", id);
            userInfo.put("nickName", nickName);
            userInfo.put("email", email);
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return userInfo;
    }

	@Override
	public String addkperson(KPerson kperson) {
		KPerson joinKperson = dao.save(kperson);
		return joinKperson.getEmail();
	}

	@Override
	public List<KPerson> Kpersons() {
		List<KPerson> kperson = dao.findAll();
		
		return kperson;
	}

	@Override
	public void delKperosn(String email) {
		dao.deleteById(email);
	}

	@Override
	public int updateKPerson(String email, String authority) {
		return dao.updateKPerson(email, authority);
	}

}