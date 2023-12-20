package com.sample.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.CDRLogging.CDRLoggingThread;
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
		try {
			String username = body.getUsername();
			String pass = body.getPassword();
			Users user = demoDBServices.getByUsername(username);
			if (user != null) {
				String passHash = user.getHashPass();
				if (passHash.equals(pass)) {
					user.getAge();
					CDRLoggingThread.write("User Logged in");
					return "Welcome back " + username;
				} else {
					CDRLoggingThread.write("Incorrect Username or Password");
					return "Incorrect Username or Password";
				}
			} else {
				CDRLoggingThread.write("User not found");
				return "User not found";
			}
		} catch (Exception exp) {
			System.out.println(exp);
			return "Sorry, Something went wrong!";
		}
	}

	@PostMapping("/register")
	@ResponseBody
	String registerUser(@RequestBody RegistrationRequestObjectBody body) {
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
			CDRLoggingThread.write("Registration request for " + username + " was " + "successful");
			return "token";
		} catch (Exception exp) {
			System.out.println(exp);
			return "Sorry, Something went wrong!";
		}
	}

}
