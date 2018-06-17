package com.utn.rsgl.front.frontservice.user;

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

public class UserRepositoryTest {
	@Mock UserRepository userRepository;

	@Before
	public void creatingRequiredThings(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findOneByUsernameTest(){
		when(userRepository.findOneByUsername("juan0"))
				.thenReturn(new User("juan", "juan0", "1234", "some@mail.com", "1998-02-01"));

		User user = userRepository.findOneByUsername("juan0");
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
	public void findByNameTest(){
		List<User> users = new ArrayList<>();
		users.add(new User("juan","juan0", "1234", "some@mail.com", "1998-02-01"));
		when(userRepository.findByName("juan")).thenReturn(users);
		List<User> user = userRepository.findByName("juan");
		verify(userRepository).findByName("juan");
		Assert.assertNotNull(user.get(0));
		Assert.assertEquals( "juan", user.get(0).getName());
		Assert.assertEquals("juan0", user.get(0).getUsername());
		Assert.assertEquals("1234", user.get(0).getPassword());
		Assert.assertEquals("some@mail.com", user.get(0).getEmail());

		Date birthDate = user.get(0).getBirthDate();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = date.format(birthDate);

		Assert.assertEquals(date1, "1998-02-01");
	}

	@Test
	public void deleteByUserNameTest(){
		when(userRepository.findOneByUsername("juan0")).thenReturn(null);
		userRepository.deleteByUsername("juan0");
		verify(userRepository).deleteByUsername("juan0");
		Assert.assertNull(userRepository.findOneByUsername("juan0"));
	}

	@Test
	public void uploadTest(){
		when(userRepository.findOneByUsername("juan0"))
				.thenReturn(new User("juan","juan0", "1234", "some@mail.com", "1998-02-01"));
		userRepository.update("juan","juan0", "1234", "some@mail.com", "1998-06-08");
		User user =userRepository.findOneByUsername("juan0");
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
}
