package com.utn.rsgl.front.frontservice.controller;

import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired UserService userService;
	@Autowired HttpSession session;

	@RequestMapping(value = "{username}/{password}", method = RequestMethod.POST)
	public ResponseEntity<User> Loggin(HttpServletRequest request,@RequestBody UserRequest userRequest) throws Exception {
		session = request.getSession();
		User user = userService.findAnUser(username);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "UserLogInController");
		if(Objects.nonNull(user))
			if(user.getPassword().equals(passsword)) {
				Optional.ofNullable(session)
						.ifPresent(session -> clearSession((HttpSession) session));
				session.setAttribute("user", user);
			}
			else{
				throw  new  Exception("your password is incorrect");
			}
		else{
			throw  new Exception("you must sign in first");
		}
		return ResponseEntity.accepted().headers(headers).body(user);
	}

}
