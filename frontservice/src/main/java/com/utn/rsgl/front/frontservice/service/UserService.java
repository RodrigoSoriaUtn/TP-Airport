package com.utn.rsgl.front.frontservice.service;

import com.utn.rsgl.front.frontservice.models.User;
import com.utn.rsgl.front.frontservice.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Data
@Service
public class UserService {

	/** The persistence
	 *
	 */
	@Autowired
	UserRepository userRepository;

	/** to add a new User
	 *
	 * @param name String
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param birthDate String
	 * @throws Exception if the username already exists in the persistence
	 */
	public void newUser(String name, String username, String password, String email, String birthDate) throws Exception {
		User user = new User( name, username, password, email, birthDate);
		if(Objects.isNull(userRepository.findOneByUsername(username)))
			userRepository.save(user);
		else
			throw new Exception("The user already exists in the system, please select other username");
	}

	/** to look in the persistence and find an user by it username
	 *
	 * @param username String
	 * @return an object user
	 */
	public User findAnUser(String username){
		return userRepository.findOneByUsername(username);
	}

	/** to look in the persistence and find an user by it name
	 *
	 * @param name String
	 * @return an object User
	 */
	public User findAnUserByName(String name){

		return userRepository.findByName(name).get(0);
	}

	/** to look in the persistence and find all the users
	 *
	 * @return a List of User
	 */
	public List<User> getAll(){
		return userRepository.findAll();
	}

	/** to look in the persistence and after find the User with the username provided in the param, delete the User
	 *
	 * @param username String
	 */
	public  void deleteUser(String username){
		userRepository.deleteByUsername(username);
	}

	/** to look in the persistence by the username, and if the User exists, this method updates all its properties with the params
	 * else, the method creates an User.
	 *
	 * @param username String
	 * @param name String
	 * @param email String
	 * @param password String
	 * @param birthDate String
	 */
	public void upadateUser(String username, String name, String email, String password, String birthDate){
		if(!Objects.isNull(userRepository.findOneByUsername(username))){
			userRepository.update(name,username,password,email,birthDate);
		}else{
			try {
				this.newUser(name, username, password, email, birthDate);
			}catch(Exception e){
				e.getMessage();
			}

		}

	}

}
