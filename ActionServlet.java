

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ActionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action.contains("customer")) {

			CustomerController customerController = new CustomerController();

			if (action.equals("customer")) {
				customerController.readAllCustomers(request, response);
			}
			else if (action.equals("createcustomer")) {
				customerController.createCustomer(request, response);
			}
			else if (action.equals("updatecustomer")) {
				customerController.updateCustomer(request, response);
			}
			else if (action.equals("deletecustomer")) {
				customerController.deleteCustomer(request, response);
			}
		}

		else if (action.equals("admin")) {	
			CustomerController customerController =  new CustomerController();
			customerController.accessAdmin(request, response);		
		}

		else if (action.contains("supplier")) {

			SupplierController supplierController = new SupplierController();

			if (action.equals("supplier")) {
				supplierController.readAllSuppliers(request, response);
			}
			else if (action.equals("createsupplier")) {
				supplierController.createSupplier(request, response);
			}
			else if (action.equals("updatesupplier")) {
				supplierController.updateSupplier(request, response);
			}
			else if (action.equals("deletesupplier")) {
				supplierController.deleteSupplier(request, response);
			}
		}

		else if (action.contains("account")) {

			if (action.equals("verifyaccount")) {

				if (request.getParameter("accountType").equals("customer")) {
					CustomerController customerController = new CustomerController();
					customerController.verifyCustomer(request, response);
				} else if (request.getParameter("accountType").equals("supplier")) {
					SupplierController supplierController = new SupplierController();
					supplierController.verifySupplier(request, response);
				}
			}

			else if (action.equals("logoutaccount")) {
				request.getSession().invalidate();
				RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
				view.forward(request, response);
			}

			else if (action.equals("manageaccount")) {

				if (request.getSession().getAttribute("accountType").equals("customer")) {
					RequestDispatcher view = request.getRequestDispatcher("/jsp/manageAccountCustomer.jsp");
					view.forward(request, response);
				} else if (request.getSession().getAttribute("accountType").equals("supplier")) {
					RequestDispatcher view = request.getRequestDispatcher("/jsp/manageAccountSupplier.jsp");
					view.forward(request, response);
				} else {
					RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
					view.forward(request, response);
				}
			}

			else if (action.equals("accountproducts")) {

				if (request.getSession().getAttribute("accountType").equals("customer")) {
					ManyToManyController manyToManyController = new ManyToManyController();
					manyToManyController.readAllProductsForCustomer(request, response);
				} else if (request.getSession().getAttribute("accountType").equals("supplier")) {
					ManyToManyController manyToManyController = new ManyToManyController();
					manyToManyController.readAllProductsForSupplier(request, response);
				} else {
					RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
					view.forward(request, response);
				}
			}
		}

		else if (action.contains("product")) {

			ProductController productController = new ProductController();
			CartController cartController = new CartController();

			if (action.equals("product")) {
				productController.readAllProducts(request, response);
			}
			else if (action.equals("createproduct")) {
				productController.createProduct(request, response);
			}
			else if (action.equals("editproduct")) {
				productController.editProduct(request, response);
			}
			else if (action.equals("updateproduct")) {
				productController.updateProduct(request, response);
			}
			else if (action.equals("deleteproduct")) {
				productController.deleteProduct(request, response);
			}
			else if (action.equals("productcategory")) {
				productController.readAllProductsByCategory(request, response);
			}
			else if (action.equals("addproduct")) {
				cartController.addProduct(request, response);
			}
			else if (action.equals("checkoutproducts")) {
				cartController.checkoutProducts(request, response);
			}
			else if (action.equals("removeproduct")) {
				cartController.removeProduct(request, response);
			}
		}

		else if (action.equals("password")) {
			MessageController messageController = new MessageController();
			messageController.handleForgottenPassword(request, response);
		}
	}
}
