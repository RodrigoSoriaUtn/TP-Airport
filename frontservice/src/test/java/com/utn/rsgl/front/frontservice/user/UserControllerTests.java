package com.utn.rsgl.front.frontservice.user;


import com.utn.rsgl.front.frontservice.controller.UserController;
import com.utn.rsgl.front.frontservice.models.User;
import com.utn.rsgl.front.frontservice.service.UserService;
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

	@Mock
	UserService service;
	UserController myController;

	@Before
	public void creatingController(){
		MockitoAnnotations.initMocks(this);
		myController = new UserController();
		myController.setUserService(service);
	}



}
