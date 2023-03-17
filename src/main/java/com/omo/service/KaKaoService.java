package com.omo.service;

import java.util.HashMap;
import java.util.List;

import com.omo.dto.KPerson;

public interface KaKaoService {
	String getKakaoAccessToken(String code);
	HashMap<String, Object> getUserInfo (String access_Token);
	String addkperson(KPerson kperson);
	List<KPerson> Kpersons();
	void delKperosn(String email);
	int updateKPerson(String email, String authority);
}
