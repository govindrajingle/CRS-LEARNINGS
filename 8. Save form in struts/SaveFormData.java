
//Displaying the JSP via button(xml)
<action path="/showTextMethod" scope="request" parameter="hmode" name="" type="in.cdacnoida.tdc.allocator.action.Govind">
   <forward name="HelloGovind" path="HelloGovind.page"/>
</action>

//To display webpage (java)
package in.cdacnoida.tdc.allocator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class Govind extends DispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
	{
		System.out.println("inside Govind");
	    return mapping.findForward("HelloGovind");
	}
}


//Saving data to db (xml)
<action path="/govindAction" type="in.cdacnoida.tdc.allocator.action.GovindAction" name="GovindForm" scope="request" parameter="hmode">
</action>

//Save Action form (controller) (java)

package in.cdacnoida.tdc.allocator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import in.cdacnoida.tdc.allocator.forms.GovindForm;
import in.cdacnoida.tdc.gl.adaptor.DBQueryManager;

public class GovindAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
    	
    	System.out.println("inside GovindAction form");

        // Cast the form to GovindForm
        GovindForm govindForm = (GovindForm) form;

        // Get input field value from the form
        String inputField = "OM";
        
        if(request.getParameter("inputField")!=null)
        {
        	inputField = request.getParameter("inputField").toString().trim();
        }	
        // Create an instance of DBQueryManager to perform database operations
        DBQueryManager queryManager = new DBQueryManager();

        // Construct the SQL query
        String saveQueryOne = "INSERT INTO govind_dev (Name) VALUES ('" + inputField + "')";

        try {
            // Execute the SQL query
            boolean queryStatus = queryManager.insert(saveQueryOne);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            // Optionally, you can forward to an error page
            return mapping.findForward("error");
        }

        // Forward to the success page
        return mapping.findForward("success");
    }
}


//Form bean (xml)
<form-bean name="GovindForm" type="in.cdacnoida.tdc.allocator.forms.GovindForm"/>

//Form bean class (java)
package in.cdacnoida.tdc.allocator.forms;

import org.apache.struts.action.ActionForm;

public class GovindForm extends ActionForm {
    private String inputField;

    public String getInputField() {
        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }
}


//WebPage (jsp)
<form action="govindAction.do" method="post" onsubmit="handleFormSubmit()">
        <h2>Enter your name</h2>
        <input type="text" name="inputField" id="inputField">
        <input type="submit" value="Submit">
</form>

