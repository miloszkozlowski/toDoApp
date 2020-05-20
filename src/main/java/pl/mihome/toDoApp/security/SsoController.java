package pl.mihome.toDoApp.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SsoController {

	@GetMapping("/logout")
	String logout(HttpServletRequest req, Model model) throws ServletException {
		req.logout();
		model.addAttribute("wiadomosc", "Użytkownik wylogowany");
		return "index";
	}
}
 