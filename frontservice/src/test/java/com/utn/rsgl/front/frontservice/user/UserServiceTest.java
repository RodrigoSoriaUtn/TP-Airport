package com.utn.rsgl.front.frontservice.user;

import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import com.bootcampglobant.userregister.repository.UserRepository;
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

public class UserServiceTest {
	@Mock UserRepository userRepository;
	UserService userService;
	
	@Before
	public void requiredThingToRunTests(){
		userService = new UserService();
		MockitoAnnotations.initMocks(this);
		userService.setUserRepository(userRepository);
	}

	@Test
	public void addingAnUserTest() throws Exception {
		List<User> myList = new ArrayList<>();
		User u = new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01");

		when(userRepository.findOneByUsername("juan0")).thenReturn(null);
		userService.newUser("juan", "juan0", "1234", "some@mail.com", "1998-02-01");
		myList.add(u);

		verify(userRepository).save(u);
		when(userRepository.findAll()).thenReturn(myList);

		Assert.assertNotNull(userService.getAll().get(0));
		Assert.assertEquals(userService.getAll().size(), 1);
		Assert.assertEquals( "juan", userService.getAll().get(0).getName());
		Assert.assertEquals("juan0", userService.getAll().get(0).getUsername());
		Assert.assertEquals("1234", userService.getAll().get(0).getPassword());
		Assert.assertEquals("some@mail.com", userService.getAll().get(0).getEmail());

		Date birthDate = userService.getAll().get(0).getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(birthDate);

		Assert.assertEquals(date1, "1998-02-01");
	}

	@Test
	public void getAllTest(){
		List<User> myList = new ArrayList<>();
		List<User> myUserList;
		User u = new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01");
		myList.add(u);
		when(userRepository.findAll()).thenReturn(myList);

		myUserList = userService.getAll();
		verify(userRepository).findAll();
		Assert.assertNotNull(myList);
		Assert.assertEquals(1, myUserList.size());
	}

	@Test
	public void findAnUserByNameTest(){
		List<User> users = new ArrayList<>();
		users.add(new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01"));

		when(userRepository.findByName("juan"))
				.thenReturn(users);

		User user = userService.findAnUserByName("juan");
		verify(userRepository).findByName("juan");
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
	public void findUserByUsernameTest() {
		when(userRepository.findOneByUsername("juan0"))
				.thenReturn(new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01"));
		User user = userService.findAnUser("juan0");
		verify(userRepository).findOneByUsername("juan0");
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
		when(userRepository.findAll()).thenReturn(null);
		userService.deleteUser("juan0");
		verify(userRepository).deleteByUsername("juan0");
		Assert.assertNull(userService.getAll());
	}


}
