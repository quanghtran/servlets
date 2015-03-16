

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.mindteck.entities.Product;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*50)
public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public UploadServlet() {    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//CODE FOR AJAX 
		ProductController productController = new ProductController();
		List<Product> products = productController.readRandomProducts(5);
		String json = new Gson().toJson(products);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String appPath = getServletContext().getRealPath("/");
		String accessPath = "uploadedImages\\";
		String savePath = appPath + "\\" + accessPath;
		
		Part filePart = request.getPart("image");
	    String contentDisp = filePart.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String fileName = "";
       
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        
        String imagePath = savePath + fileName;
        filePart.write(imagePath);
        
        request.setAttribute("imagePath", accessPath + fileName);
       
        String action = request.getParameter("action");
        
        if (action.equals("uploadproduct")) {
        	ViewDispatcher.showView("UploadProduct", request, response);
		} else if (action.equals("editproduct")) {
			ViewDispatcher.showView("EditProduct", request, response);
		}
        
	}
}

