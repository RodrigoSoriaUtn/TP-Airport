package com.utn.rsgl.front.frontservice.controller;

import com.bootcampglobant.userregister.models.User;
import com.utn.rsgl.front.frontservice.request.UserResponse;
import com.utn.rsgl.front.frontservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> Loggin(HttpServletRequest request,@RequestBody UserResponse userResponse) throws Exception {
		session = request.getSession();
		User user = userService.findAnUser(userResponse.getUsername());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Responded", "UserLogInController");
		if(Objects.nonNull(user))
			if(user.getPassword().equals(userResponse.getPassword())) {
				Optional.ofNullable(session)
						.ifPresent(session -> rechargeSession((HttpSession) session));
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

	public void rechargeSession(HttpSession session){
		session.removeAttribute("user");
	}
}
