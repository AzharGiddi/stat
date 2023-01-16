package com.nissan.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.nissan.automation.core.utils.APIUtil;
import com.nissan.configuration.ConfigurationManager;

public class GetGWCaseDetailsUtil {

	private GetGWCaseDetailsUtil() {

	}

	public static String getCaseStatus(String caseId) {

		String url = ConfigurationManager.getBundle().getString("base.uri")
				+ String.format("cases/NSA-STAT-Work %s", caseId);

		String responseString = APIUtil.getResponseObject(url).asString();

		return new Gson().fromJson(responseString, GWDetails.class).caseIdStatus;

	}

	public class GWDetails {

		@SerializedName("status")
		public String caseIdStatus;
	}
}
