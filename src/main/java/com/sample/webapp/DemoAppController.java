package com.sample.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.logger.Logging;
import com.sample.datapojo.Users;
import com.sample.dbservices.DBService;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class LoginRequestObjectBody {
	String username;
	String password;
}

@Setter
@Getter
class RegistrationRequestObjectBody {
	String username;
	String password;
	String age;
}

@Controller
public class DemoAppController {
	
	@Autowired
	DBService demoDBServices;
	
	@PostMapping("/login")
	@ResponseBody
	String loginUser(@RequestBody LoginRequestObjectBody body) {
		Logging log = new Logging();
		try {
			String username = body.getUsername();
			String pass = body.getPassword();
			Users user = demoDBServices.getByUsername(username);
			if (user != null) {
				String passHash = user.getHashPass();
				if (passHash.equals(pass)) {
					user.getAge();
					log.setInfoMessage("Authcontroller", " loginUser ", "User " + username + " was Logged in");
					log.start();
					return "Welcome back " + username;
				} else {
					log.setInfoMessage("Authcontroller", "loginUser",
							"User " + username + "Incorrect Username or Password");
					log.start();
					return "Incorrect Username or Password";
				}
			} else {
				log.setInfoMessage("Authcontroller", " loginUser ", "User " + username + "User not found");
				log.start();
				return "User not found";
			}
		} catch (Exception exp) {
			log.setErrorMessage("AppController.java ", "Login", ""+exp);
			log.start();
			System.out.println(exp);
			return "Sorry, Something went wrong!";
		}
	}

	@PostMapping("/register")
	@ResponseBody
	String registerUser(@RequestBody RegistrationRequestObjectBody body) {
		Logging log = new Logging();
		try {
			String age = body.getAge();
			String username = body.getUsername();
			String pass = body.getPassword();
			int parsedAge = 0;
			try {
				parsedAge = Integer.parseInt(age);
			} catch (NumberFormatException n) {
				return "Invalid Age. Age should be a numerical value.";
			}
			if (parsedAge == 0 || parsedAge > 200) {
				return "Invalid age. age should be between 1 to 200";
			}
			Users user = demoDBServices.getByUsername(username);
			if (user != null) {
				return username + " already registered.";
			}

			Users u = new Users();
			u.setUsername(username);
			u.setHashPass(pass);
			u.setAge(parsedAge);
			demoDBServices.saveUserEntity(u);
			log.setInfoMessage("Authcontroller", "loginUser",
					"Registration request for " + username + " was " + "successful");
			log.start();
			return "token";
		} catch (Exception exp) {
			System.out.println(exp);
			return "Sorry, Something went wrong!";
		}
	}

}
