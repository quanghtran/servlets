

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindteck.businesslayer.ManyToManyDelegate;
import com.mindteck.entities.Product;

public class ManyToManyController {
	
	private ManyToManyDelegate manyToManyDelegate = new ManyToManyDelegate();
	
	public void readAllProductsForCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int customerId = (Integer) request.getSession().getAttribute("user");
		
		List<Product> shoppingCart = manyToManyDelegate.readShoppingCart(customerId);
		request.getSession().setAttribute("shoppingCart", shoppingCart);
		
		double total = 0;
		for (Product product : shoppingCart) {		
			total += product.getQuantity() * product.getPrice();		
		}
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		request.setAttribute("total", cf.format(total));
		
		request.setAttribute("productList", manyToManyDelegate.readAllProductsForCustomer(customerId));	
		ViewDispatcher.showView("CustomerProducts", request, response);
	
	}
	
	public void readAllProductsForSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int supplierId = (Integer) request.getSession().getAttribute("user");
		request.setAttribute("productList", manyToManyDelegate.readAllProductsForSupplier(supplierId));	
		ViewDispatcher.showView("SupplierProducts", request, response);
	
	}

}
