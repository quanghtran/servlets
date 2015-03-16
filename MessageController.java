

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindteck.businesslayer.MessageDelegate;

public class MessageController {

	private MessageDelegate messageDelegate = new MessageDelegate();
	
	public void handleForgottenPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		messageDelegate.handleForgottenPassword(email);
		
		request.setAttribute("email", email);
		request.setAttribute("recovery", "success");
		
		ViewDispatcher.showView("Home", request, response);
	}
	
}
