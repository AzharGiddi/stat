package com.nissan.utils;

import java.util.Comparator;
import java.util.Date;

import com.nissan.automation.core.utils.DateUtil;

public class DateComparator implements Comparator<Date>{

	@Override
	public int compare(Date date1, Date date2) {
		// TODO Auto-generated method stub
		int i = DateUtil.getDateDifference(date2, date1);
		return i;
	}

}
