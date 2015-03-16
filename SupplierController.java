
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindteck.businesslayer.DataDeletionException;
import com.mindteck.businesslayer.SupplierDelegate;
import com.mindteck.entities.Supplier;

public class SupplierController {

	private SupplierDelegate supplierDelegate = new SupplierDelegate();

	public void readAllSuppliers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("supplierList", supplierDelegate.readAllSuppliers());	
		ViewDispatcher.showView("_____", request, response);
		
	}
	
	public void createSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Supplier supplier = new Supplier();
		supplier.setName(request.getParameter("name"));
		supplier.setAddress(request.getParameter("address"));
		supplier.setPhone(Long.parseLong(request.getParameter("phone")));
		supplier.setEmail(request.getParameter("email"));
		supplier.setPassword(request.getParameter("password"));
		
		supplierDelegate.createSupplier(supplier);
		
		verifySupplier(request, response);

	}
	
	public void verifySupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Supplier supplier = new Supplier();
		supplier.setEmail(request.getParameter("email"));
		supplier.setPassword(request.getParameter("password"));
		
		request.setAttribute("email", supplier.getEmail());
		request.setAttribute("accountType", request.getParameter("accountType"));
		
		List<Supplier> supplierList = supplierDelegate.readAllSuppliers();
		
		for (Supplier c : supplierList) {
			if (c.getEmail().equals(supplier.getEmail())) {
				if (c.getPassword().equals(supplier.getPassword())) {
					supplier = c;
					request.setAttribute("login", "success");
					HttpSession session = request.getSession(true);
					session.setAttribute("accountType", "supplier");
					session.setAttribute("supplier", supplier);
					session.setAttribute("user", supplier.getSupplierId());
					session.setAttribute("name", supplier.getName());
					session.setMaxInactiveInterval(60*60);
				} else {
					request.setAttribute("login", "passwordFailure");
				}
			}
		}
		
		if (request.getAttribute("login") == null) {
			request.setAttribute("login", "emailFailure");
		}
		
		ViewDispatcher.showView("Home", request, response);
				
	}

	public void updateSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Supplier supplier = new Supplier();
		supplier.setSupplierId((Integer) request.getSession().getAttribute("user"));
		supplier.setName(request.getParameter("name"));
		supplier.setAddress(request.getParameter("address"));
		supplier.setPhone(Long.parseLong(request.getParameter("phone")));
		supplier.setEmail(request.getParameter("email"));
		supplier.setPassword(request.getParameter("password"));
		
		supplierDelegate.updateSupplier(supplier);
		
		request.getSession().setAttribute("supplier", supplier);
		
		ViewDispatcher.showView("SupplierAccount", request, response);
	}

	public void deleteSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int supplierId = Integer.parseInt(request.getParameter("id"));
		
		try {
			supplierDelegate.deleteSupplier(supplierId);
			request.setAttribute("delete", "success");
			request.getSession().invalidate();
			ViewDispatcher.showView("Home", request, response);
		} catch (DataDeletionException e) {
			request.setAttribute("delete", "failure");
			ViewDispatcher.showView("SupplierAccount", request, response);
		}

	}

}
