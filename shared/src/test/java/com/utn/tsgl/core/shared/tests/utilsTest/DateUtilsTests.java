package com.utn.tsgl.core.shared.tests.utilsTest;

import com.utn.rsgl.core.shared.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class DateUtilsTests {
	DateUtils dateUtils;

	@Test
	public void StringToDateTests(){
		String strdate = "1998-04-01";
		Date mydate = DateUtils.StringToDate(strdate);
		Assert.assertNotNull(mydate);
		Assert.assertTrue(mydate instanceof Date);
	}

	@Test
	public void DateToStringTests(){
		String strdate = "1998-04-01";
		Date mydate = DateUtils.StringToDate(strdate);
		String newStrDate = DateUtils.DateToString(mydate);
		Assert.assertNotNull(newStrDate);
		Assert.assertTrue(newStrDate instanceof String);
		Assert.assertEquals(strdate, newStrDate);
	}
}
