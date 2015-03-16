

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindteck.businesslayer.DataDeletionException;
import com.mindteck.businesslayer.ProductDelegate;
import com.mindteck.entities.Product;

public class ProductController {

	private ProductDelegate productDelegate = new ProductDelegate();

	public void readAllProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("productList", productDelegate.readAllProducts());	
		ViewDispatcher.showView("_____", request, response);
		
	}
	
	public void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product product = new Product();
		product.setBrand(request.getParameter("brand"));
		product.setName(request.getParameter("name"));
		product.setCategory(request.getParameter("category"));
		product.setSubcategory(request.getParameter("subcategory"));
		product.setPrice(Double.parseDouble(request.getParameter("price")));
		product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		product.setDescription(request.getParameter("description"));
		product.setImage(request.getParameter("image"));
		
		int supplierId = (Integer) request.getSession().getAttribute("user");
		
		productDelegate.createProduct(product, supplierId);
		
		ViewDispatcher.showView("AccountProducts", request, response);

	}
	
	public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDelegate.readProduct(productId);
		request.setAttribute("product", product);

		ViewDispatcher.showView("EditProduct", request, response);
		
	}

	public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
	
		Product product = new Product();
		product.setProductId(Integer.parseInt(request.getParameter("id")));
		product.setBrand(request.getParameter("brand"));
		product.setName(request.getParameter("name"));
		product.setCategory(request.getParameter("category"));
		product.setSubcategory(request.getParameter("subcategory"));
		product.setPrice(Double.parseDouble(request.getParameter("price")));
		product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		product.setDescription(request.getParameter("description"));
		product.setImage(request.getParameter("image"));
		
		productDelegate.updateProduct(product);
		
		ViewDispatcher.showView("AccountProducts", request, response);
		
	}

	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("id"));
		
		try {
			productDelegate.deleteProduct(productId);
			request.setAttribute("delete", "success");
		} catch (DataDeletionException e) {
			request.setAttribute("delete", "failure");
		}
		finally {
			ViewDispatcher.showView("AccountProducts", request, response);
		}
		
	}

	public void readAllProductsByCategory(HttpServletRequest request, HttpServletResponse response) {
		
		String category = request.getParameter("category");
		String capCategory = category.substring(0, 1).toUpperCase() + category.substring(1);
		
		try {
			List<Product> productList = productDelegate.readAllProductsByCategory(category);
			request.setAttribute("productList", productList);
			request.setAttribute("category", capCategory);
			
			ViewDispatcher.showView("Category", request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<Product> readRandomProducts(int numberOfProducts) throws RemoteException {
		
		return productDelegate.readRandomProducts(numberOfProducts);
		
	}

}
