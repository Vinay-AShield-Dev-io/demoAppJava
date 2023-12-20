package com.sample.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.CDRLogging.CDRLoggingThread;
import com.sample.datapojo.Users;
import com.sample.dbservices.DBService;

@Controller
public class DemoAppController {
	@Autowired
	DBService demoDBServices;

	
	@PostMapping("/login")
	@ResponseBody
	String loginUser(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String pass) {
		Users user = demoDBServices.getByUsername(username);
		if (user != null) {
			String passHash = user.getHashPass();
			if (passHash.equals(pass)) {
				user.getAge();
				CDRLoggingThread.write("Login request,"+ username +",AS201" );
				return "Welcome back " + username;
			} else {
				CDRLoggingThread.write("Login request,"+ username +",AS1XX" );
				return "Incorrect Username or Password";
			}
		} else {
			CDRLoggingThread.write("Login request,"+ username +",AS1X2" );
			return "User not found";
		}
	}

	@PostMapping("/register")
	@ResponseBody
	String registerUser(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String pass,
			@RequestParam(value = "age", required = true) String age

	) {
		int parsedAge = 0;
		try {
			parsedAge = Integer.parseInt(age);
		} catch(NumberFormatException n) {
			return "Invalid Age. Age should be a numerical value.";
		}
		if(parsedAge == 0 || parsedAge > 200) {
			return "Invalid age. age should be between 1 to 200";
		}
		Users user = demoDBServices.getByUsername(username);
		if(user != null) {
			return "User already registered.";
		}
		
		Users u = new Users();
		u.setUsername(username);
		u.setHashPass(pass);
		u.setAge(parsedAge);
		demoDBServices.saveUserEntity(u);
		CDRLoggingThread.write("Registration request," + username + "," + "AS201");
		return "token";
	}

}
