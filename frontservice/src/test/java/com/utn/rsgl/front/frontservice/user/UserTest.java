package com.utn.rsgl.front.frontservice.user;


import com.utn.rsgl.front.frontservice.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserTest {

	@Test
	public void newUserTest(){
		User anyUser = new User("joan", "jails1","1234", "any@mail.com", "1998-12-03");
		Assert.assertNotNull(anyUser);
		Assert.assertEquals("joan", anyUser.getName());
		Assert.assertEquals("jails1", anyUser.getUsername());
		Assert.assertEquals("1234", anyUser.getPassword());
		Assert.assertEquals("any@mail.com", anyUser.getEmail());
		Date theDate= anyUser.getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(theDate);
		Assert.assertEquals(date1, "1998-12-03");
	}
}
