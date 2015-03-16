package com.mindteck.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindteck.businesslayer.CustomerDelegate;
import com.mindteck.businesslayer.DataDeletionException;
import com.mindteck.entities.Customer;

public class CustomerController {
	
	private CustomerDelegate customerDelegate = new CustomerDelegate();

	public void readAllCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("customerList", customerDelegate.readAllCustomers());	
		ViewDispatcher.showView("_____", request, response);

	}
	
	public void createCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Customer customer = new Customer();
		customer.setFirstName(request.getParameter("firstName"));
		customer.setLastName(request.getParameter("lastName"));
		customer.setAddress(request.getParameter("address"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			customer.setDob(sdf.parse(request.getParameter("dob")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		customer.setPhone(Long.parseLong(request.getParameter("phone")));
		customer.setEmail(request.getParameter("email"));
		
		boolean valid = validateEmail(request, response);
		
		customer.setPassword(request.getParameter("password"));
		
		customerDelegate.createCustomer(customer);
		
		if (!valid) {
			ViewDispatcher.showView("CustomerSignUp", request, response);
		} else {
			verifyCustomer(request, response);
		}
		
	}
	
	public boolean validateEmail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		List<Customer> customerList = customerDelegate.readAllCustomers();
		
		for (Customer c : customerList) {
			if (c.getEmail().equals(email)) {
				request.setAttribute("emailExisted", "isExisted");
				return false;
			} else {
				request.setAttribute("emailExisted", "isNotExisted");
			}
		}
		
		return true;
		
	}
	
	public void verifyCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Customer customer = new Customer();
		customer.setEmail(request.getParameter("email"));
		customer.setPassword(request.getParameter("password"));
		
		request.setAttribute("email", customer.getEmail());
		request.setAttribute("accountType", request.getParameter("accountType"));
		
		List<Customer> customerList = customerDelegate.readAllCustomers();
		
		for (Customer c : customerList) {
			if (c.getEmail().equals(customer.getEmail())) {
				if (c.getPassword().equals(customer.getPassword())) {
					customer = c;
					request.setAttribute("login", "success");
					HttpSession session = request.getSession(true);
					session.setAttribute("accountType", "customer");
					session.setAttribute("customer", customer);
					session.setAttribute("user", customer.getCustomerId());
					session.setAttribute("name", customer.getFirstName());
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

	public void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Customer customer = new Customer();
		customer.setCustomerId((Integer) request.getSession().getAttribute("user"));
		customer.setFirstName(request.getParameter("firstName"));
		customer.setLastName(request.getParameter("lastName"));
		customer.setAddress(request.getParameter("address"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			customer.setDob(sdf.parse(request.getParameter("dob")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		customer.setPhone(Long.parseLong(request.getParameter("phone")));
		customer.setEmail(request.getParameter("email"));
		customer.setPassword(request.getParameter("password"));
		
		customerDelegate.updateCustomer(customer);
		
		request.getSession().setAttribute("customer", customer);
		
		ViewDispatcher.showView("CustomerAccount", request, response);
		
	}

	public void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int customerId = Integer.parseInt(request.getParameter("id"));
		
		try {
			customerDelegate.deleteCustomer(customerId);
			request.getSession().invalidate();
			ViewDispatcher.showView("Home", request, response);
		} catch (DataDeletionException e) {
			ViewDispatcher.showView("CustomerAccount", request, response);
		}
		
	}

	public void accessAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ViewDispatcher.showView("Admin", request, response);
		
	}

}
