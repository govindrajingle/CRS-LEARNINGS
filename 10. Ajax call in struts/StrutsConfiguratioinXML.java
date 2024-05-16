//struts 
//path = controller mapping url
//name = form bean name
<form-bean name="std_rev_form" type="in.cdacnoida.tdc.allocator.forms.RevisionOfStandardsForm"></form-bean> //path for form bean 

<action path = "/showTextMethod" scope="request" parameter="hmode" name="std_rev_form" type="in.cdacnoida.tdc.allocator.action.RevisionOfStandards"> // path of Action form
<forward name="RevisionOfStandards" path="RevisionOfStandards.page"/> //jsp name and its tile confi
</action>

//tiles xml
   <definition name="RevisionOfStandards.page" extends="design.layout">
    <put name="body" value="/app_srv/tdc/mm04/jsp/RevisionOfStandards.jsp"/>
 </definition>
  
//how send ajax call
		        $.ajax({
		            type: "POST",
		            url: "showTextMethod.do?hmode=Save_std_rev",
		            data: {
		                inputField: formDataString
		            },
					

//defination of controller
package in.cdacnoida.tdc.allocator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import in.cdacnoida.tdc.allocator.forms.RevisionOfStandardsForm;
import in.cdacnoida.tdc.gl.adaptor.DBQueryManager;

public class RevisionOfStandards extends DispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
	{
		System.out.println("inside RevisionOfStandards");
	    return mapping.findForward("RevisionOfStandards");
	}

	
	public ActionForward Save_std_rev(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
	{
	System.out.println("Inside RevisionOfStandards form");

	// Cast the form to 
	RevisionOfStandardsForm revisionOfStandardsForm = (RevisionOfStandardsForm) form;

	String inputField = revisionOfStandardsForm.getInputField();

	System.out.println("input name = " + revisionOfStandardsForm.getInputField());

	if (inputField == null || inputField == "") {
		return mapping.findForward("error");
	}

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
					