

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindteck.businesslayer.DataDeletionException;
import com.mindteck.businesslayer.CartDelegate;
import com.mindteck.entities.Product;

public class CartController {

	private CartDelegate cartDelegate = new CartDelegate();
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("id"));
		int customerId = (Integer) request.getSession().getAttribute("user");
		
		cartDelegate.addProduct(productId, customerId);
		
		ViewDispatcher.showView("AccountProducts", request, response);
		
	}
	
	public void checkoutProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Product> shoppingCart = (ArrayList<Product>) request.getSession().getAttribute("shoppingCart");
		
		int customerId = (Integer) request.getSession().getAttribute("user");
		
		try {
			cartDelegate.checkoutProducts(shoppingCart, customerId);
			request.setAttribute("checkout", "success");
		} catch (DataDeletionException e) {
			request.setAttribute("checkout", "failure");
		}
		
		ViewDispatcher.showView("AccountProducts", request, response);
		
	}

	public void removeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int productId = Integer.parseInt(request.getParameter("id"));
		int customerId = (Integer) request.getSession().getAttribute("user");
		
		try {
			cartDelegate.removeProduct(productId, customerId);
		} catch (DataDeletionException e) {
			e.printStackTrace();
		}
		finally {
			ViewDispatcher.showView("AccountProducts", request, response);
		}
		
	}

}
