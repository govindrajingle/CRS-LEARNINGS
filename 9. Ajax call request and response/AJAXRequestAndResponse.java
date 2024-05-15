package in.cdacnoida.tdc.allocator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import in.cdacnoida.tdc.allocator.forms.GovindForm;
import in.cdacnoida.tdc.gl.adaptor.DBQueryManager;

public class GovindAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response) {

	    System.out.println("Inside GovindAction form");

	    // Cast the form to GovindForm
	    GovindForm govindForm = (GovindForm) form;
	    
	    String inputField = govindForm.getInputField();
	    
	    System.out.println("input name = " + govindForm.getInputField());

	    DBQueryManager queryManager = new DBQueryManager();

	    String saveQuery = "INSERT INTO govind_dev (Name) VALUES ('" + inputField + "')";

	    try {
	        // Execute the SQL query
	        boolean queryStatus = queryManager.insert(saveQuery);

	        // Check if the query was successful
	        if (queryStatus) {
	            // Send response 1 for success
	            response.getWriter().write("1");
	        } else {
	            // Send response 0 for failure
	            response.getWriter().write("0");
	        }
	    } catch (Exception e) {
	        // Handle any exceptions
	        e.printStackTrace();
	        // Set error message
	        return mapping.findForward("error");
	    }

	    // Forward to the success page
	    return null; // No need to forward, response is handled above
	}
}










//JSP FILE

<html>
<head>
<meta charset="utf-8">
<title>Revision of standard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#submitButton").click(function(e) {
			e.preventDefault();
			var inputValue = $("#inputField").val();
			$.ajax({
				type : "POST",
				url : "govindAction.do",
				data : {
					inputField : inputValue
				},
				success : function(response) {
					if (response.trim() === "1") {
						Swal.fire({
							icon : 'success',
							title : 'Form submitted successfully!'
						});
					} else if (response.trim() === "0") {
						Swal.fire({
							icon : 'error',
							title : 'Error!',
							text : 'Failed to save data.'
						});
					} else {
						Swal.fire({
							icon : 'error',
							title : 'Error!',
							text : 'Unknown response received.'
						});
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="page-header text-center">
		<h1>
			<small>Add revision of standard / amendments</small>
		</h1>
	</div>
	<form id="myForm">
		<input type="text" id="inputField" name="inputField" required />
		<button type="button" id="submitButton">Submit</button>
	</form>
</body>
</html>