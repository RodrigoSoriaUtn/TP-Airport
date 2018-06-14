package com.utn.rsgl.airport.models;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user")
	private Long id;

	/** the nickname of the user
	 *
	 */
	@Column(name= "name", nullable = false)
	private String name;

	/** the username of the user. it must be unique.
	 *
	 */
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	/** the password of the user
	 *
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/** the email of the user
	 *
	 */
	@Column(name = "email", nullable = false)
	private String email;

	/** the birthDate. is type Date.
	 *
	 */
	@Column(name = "birth_date", nullable = true)
	private Date birthDate;

	public User(){}

	public User(String name, String username, String password, String email, String birthDate) {
		try{

			this.name = name;
			this.username=username;
			this.email=email;
			this.password=password;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.birthDate= sdf.parse(birthDate);

		}catch (Exception e){
			e.getMessage();
		}
	}
}
