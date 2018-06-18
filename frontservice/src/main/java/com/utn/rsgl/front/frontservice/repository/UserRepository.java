package com.utn.rsgl.front.frontservice.repository;

import com.utn.rsgl.front.frontservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	/** to find a user by its username
	 *
	 * @param username String
	 * @return an User
	 */
	public User findOneByUsername(String username);

	/** to find a user by its name
	 *
	 * @param name String
	 * @return an User
	 */
	public List<User> findByName(String name);

	/** to delete a user
	 *
	 * @param username String
	 */
	@Transactional
	@Modifying
	public void deleteByUsername(String username);

	/** to update a User
	 *
	 * @param name String
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param date String
	 */
	@Transactional
	@Modifying
	@Query("update User set name = :name , username = :username, password = :password, "
			+ "email = :email, birth_date = :birthDate  where username like :username")
	public void update(@Param("name") String name, @Param("username") String username, @Param("password") String password,
			@Param("email") String email, @Param("birthDate") String date);

}
