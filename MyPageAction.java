package com.internousdev.leisurepass.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.leisurepass.dao.UserInfoDAO;
import com.internousdev.leisurepass.dto.MCategoryDTO;
import com.internousdev.leisurepass.dto.UserInfoDTO;
import com.internousdev.leisurepass.util.SearchConditionLoader;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware {

	private String categoryId;
	private String keywords;
	private List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
//	private List<UserInfoDTO> userInfoDTOList = new ArrayList<UserInfoDTO>();
	private Map<String, Object> session;

	public String execute() {
	/*	String result = ERROR;*/

		UserInfoDAO userInfoDAO = new UserInfoDAO();
		UserInfoDTO userInfoDTO = new UserInfoDTO();

		userInfoDTO = userInfoDAO.getUserInfo(String.valueOf(session.get("loginId")));
System.out.println(1111);
		// userInfoDTOに値があれば、セッションへ名前・性別・メールアドレスを保存する
		if(userInfoDTO != null) {
			session.put("familyName", userInfoDTO.getFamilyName());
			session.put("firstName", userInfoDTO.getFirstName());
			session.put("familyNameKana", userInfoDTO.getFamilyNameKana());
			session.put("firstNameKana", userInfoDTO.getFirstNameKana());
			session.put("sex", userInfoDTO.getSex());
			session.put("email", userInfoDTO.getEmail());

/*			UserInfoDAO userDAO=new UserInfoDAO();
			userInfoDTOList=userDAO.getUserInfo(loginId);
*/

		}

/*ログイン情報がなければマイページに入れないようにする*/

		//sessionにloginIdがなければERRORを返す
			if(!session.containsKey("loginId")){
				return ERROR;
			}

		// 管理者の場合"admin"を返す
			UserInfoDTO d = (UserInfoDTO) session.get("userInfo");
			if ((d.getStatus().equals("1"))) {
				String result = "admin";
				return result;
			}

		// navigation情報を取得
		SearchConditionLoader loader = new SearchConditionLoader();
		loader.execute(session);

		return SUCCESS;

	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<MCategoryDTO> getMCategoryDTOList() {
		return mCategoryDTOList;
	}

	public void setMCategoryDTOList(List<MCategoryDTO> mCategoryDTOList) {
		this.mCategoryDTOList = mCategoryDTOList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

/*
	public List<UserInfoDTO> getUserInfoDTOList(){
		return userInfoDTOList;
	}
	public void setUserInfoDTOList(List<UserInfoDTO> userInfoDTOList){
		this.userInfoDTOList=userInfoDTOList;
	}
*/
}