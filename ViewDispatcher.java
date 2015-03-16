
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDispatcher {

	private static final String viewLocation = "/jsp/";
	private static final String actionLocation = "/action.do?";
	
	public static void showView(String viewName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = null;
		
		if (viewName.equals("Home")) { 
			view = request.getRequestDispatcher("/index.jsp"); 
		}
		else if (viewName.equals("CustomerAccount")) { 
			view = request.getRequestDispatcher(viewLocation + "manageAccountCustomer.jsp");
		}
		else if (viewName.equals("SupplierAccount")) { 
			view = request.getRequestDispatcher(viewLocation + "manageAccountSupplier.jsp");
		}
		else if (viewName.equals("CustomerSignUp")) { 
			view = request.getRequestDispatcher(viewLocation + "signUpCustomer.jsp");
		}
		else if (viewName.equals("SupplierSignUp")) { 
			view = request.getRequestDispatcher(viewLocation + "signUpSupplier.jsp");
		}
		else if (viewName.equals("CustomerProducts")) { 
			view = request.getRequestDispatcher(viewLocation + "customerProducts.jsp");
		}
		else if (viewName.equals("SupplierProducts")) { 
			view = request.getRequestDispatcher(viewLocation + "supplierProducts.jsp");
		}
		else if (viewName.equals("AccountProducts")) { 
			view = request.getRequestDispatcher(actionLocation + "action=accountproducts");
		}
		else if (viewName.equals("EditProduct")) { 
			view = request.getRequestDispatcher(viewLocation + "editProduct.jsp");
		}
		else if (viewName.equals("UploadProduct")) { 
			view = request.getRequestDispatcher(viewLocation + "uploadProduct.jsp");
		}
		else if (viewName.equals("Category")) { 
			view = request.getRequestDispatcher(viewLocation + "categoryPage.jsp");
		}
		else if (viewName.equals("Admin")) { 
			view = request.getRequestDispatcher(viewLocation + "adminPage.jsp");
		}
		else {
			showView("Home", request, response);
		}
		
		view.forward(request, response);
	}
	
}
