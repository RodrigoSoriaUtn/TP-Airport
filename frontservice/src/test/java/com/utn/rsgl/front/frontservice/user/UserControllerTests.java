package com.utn.rsgl.front.frontservice.user;

import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import com.bootcampglobant.userregister.service.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTests {

	@Mock UserService service;
	UserController myController;

	@Before
	public void creatingController(){
		MockitoAnnotations.initMocks(this);
		myController = new UserController();
		myController.setUserService(service);
	}

	@Test
	public void addingAnUserTest() throws Exception {
		List<User> myList = new ArrayList<>();
		User u = new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01");

		myController.addUser("juan", "juan0", "1234", "some@mail.com", "1998-02-01");
		myList.add(u);

		verify(service).newUser("juan", "juan0", "1234", "some@mail.com", "1998-02-01");
		when(service.getAll()).thenReturn(myList);
		
		Assert.assertNotNull(myController.getAllUsers().get(0));
		Assert.assertEquals(myController.getAllUsers().size(), 1);
		Assert.assertEquals( "juan", myController.getAllUsers().get(0).getName());
		Assert.assertEquals("juan0", myController.getAllUsers().get(0).getUsername());
		Assert.assertEquals("1234", myController.getAllUsers().get(0).getPassword());
		Assert.assertEquals("some@mail.com", myController.getAllUsers().get(0).getEmail());

		Date birthDate = myController.getAllUsers().get(0).getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(birthDate);

		Assert.assertEquals(date1, "1998-02-01");
	}

	@Test
	public void getAllusersTest(){
		List<User> myList = new ArrayList<>();
		List<User> myUserList;
		User u = new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01");
		myList.add(u);
		when(service.getAll()).thenReturn(myList);

		myUserList = myController.getAllUsers();
		verify(service).getAll();
		Assert.assertNotNull(myList);
		Assert.assertEquals(1, myUserList.size());
	}

	@Test
	public void getUserByNameTest(){
		when(service.findAnUserByName("juan"))
				.thenReturn(new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01"));

		User user = myController.getByName("juan");
		verify(service).findAnUserByName("juan");
		Assert.assertNotNull(user);
		Assert.assertEquals( "juan", user.getName());
		Assert.assertEquals("juan0", user.getUsername());
		Assert.assertEquals("1234", user.getPassword());
		Assert.assertEquals("some@mail.com", user.getEmail());

		Date birthDate = user.getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(birthDate);

		Assert.assertEquals(date1, "1998-02-01");
	}

	@Test
	public void getUserByUsernameTest() {
		when(service.findAnUser("juan0"))
				.thenReturn(new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01"));
		User user = myController.getByUsername("juan0");
		verify(service).findAnUser("juan0");
		Assert.assertNotNull(user);
		Assert.assertEquals( "juan", user.getName());
		Assert.assertEquals("juan0", user.getUsername());
		Assert.assertEquals("1234", user.getPassword());
		Assert.assertEquals("some@mail.com", user.getEmail());

		Date birthDate = user.getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(birthDate);

		Assert.assertEquals(date1, "1998-02-01");
	}

	@Test
	public void deleteAnUserTest(){
		when(service.getAll()).thenReturn(null);
		myController.deleteAnUser("juan0");
		verify(service).deleteUser("juan0");
		Assert.assertNull(myController.getAllUsers());
	}


}
