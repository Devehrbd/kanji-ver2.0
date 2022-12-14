package org.kanji.common.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.kanji.member.entity.Member;
import org.kanji.member.service.MemberServiceImpl;
import org.kanji.social.api.SocialAPI;
import org.kanji.social.api.SocialAccessTokenURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/social")
public class SocialController {

	@Autowired
	private MemberServiceImpl mService;

	@Autowired
	private SocialAccessTokenURL socialAccess;

	@Autowired
	private SocialAPI socialAPI;

	@RequestMapping("/naver")
	public String naver(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception {

		String sessionState = (String) session.getAttribute("N_state");
		if (sessionState.equals(state)) {

			// Access Token 발급
			JSONObject accessTokenJSON = socialAccess.getAccessTokenJSONObject("NAVER", code, state);

			// API 사용 - profile 조회
			JSONObject responseJSON = socialAPI.getProfileUsers("NAVER", (String) accessTokenJSON.get("access_token"));
			JSONObject profileJSON = (JSONObject) responseJSON.get("response");

			// 멤버객체 저장, 세션등록 및 DB에 ID저장
			Member member = new Member();
			member.setMemberId((String) profileJSON.get("id"));
			member.setLoginMethod("NAVER");

			session.setAttribute("login_member_id", member.getMemberId());
			session.setAttribute("login_member", member);

			if (mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}

			return "redirect:/course/select";

		}

		return "redirect:/member/loginPage";
	}

	@RequestMapping("/google")
	public String google(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception {

		String sessionState = (String) session.getAttribute("G_state");
		if (sessionState.equals(state)) {

			// Access Token 발급
			JSONObject accessTokenJSON = socialAccess.getAccessTokenJSONObject("GOOGLE", code, state);

			// API 사용 - profile 조회
			JSONObject profileJSON = socialAPI.getProfileUsers("GOOGLE", (String) accessTokenJSON.get("access_token"));

			// 멤버객체 저장, 세션등록 및 DB에 ID저장
			Member member = new Member();
			member.setMemberId(String.valueOf(profileJSON.get("id")));
			member.setLoginMethod("GOOGLE");

			session.setAttribute("login_member_id", member.getMemberId());
			session.setAttribute("login_member", member);

			if (mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}

			return "redirect:/course/select";

		}

		return "redirect:/member/loginPage";
	}

	@RequestMapping("/kakao")
	public String kakao(HttpSession session, @RequestParam String code, @RequestParam String state) throws Exception {

		String sessionState = (String) session.getAttribute("K_state");
		if (sessionState.equals(state)) {

			// Access Token 발급
			JSONObject accessTokenJSON = socialAccess.getAccessTokenJSONObject("KAKAO", code, state);

			// API 사용 - profile 조회
			JSONObject profileJSON = socialAPI.getProfileUsers("KAKAO", (String) accessTokenJSON.get("access_token"));

			// 멤버객체 저장, 세션등록 및 DB에 ID저장
			Member member = new Member();
			member.setMemberId(String.valueOf(profileJSON.get("id")));
			member.setLoginMethod("KAKAO");

			session.setAttribute("login_member_id", member.getMemberId());
			session.setAttribute("login_member", member);

			if (mService.getMember(member).isEmpty()) {
				mService.joinMember(member);
			}

			return "redirect:/course/select";

		}

		return "redirect:/member/loginPage";
	}
}