package com.example.employees;
import com.vaadin.flow.component.html.Div;
public class EmployeeDetailPanel extends Div {
    public EmployeeDetailPanel(Employee employee,Runnable onSave,Runnable onCancel,Runnable onRemove) {
        addClassName("detail");getElement().setAttribute("data-testid","employee-detail");
        String header="<div class='detail-header'><h2>"+Ui.escape(employee.name())+"</h2><span class='task-count'>13 assigned tasks</span><div>4 years 3 months in service</div></div>";
        String fields="<div class='pair'>"+input("first-name","First name",employee.firstName())+input("last-name","Last name",employee.lastName())+"</div>"+input("phone","Phone","+91 555 1213 456")+input("email","Email","liam.johnson@example.com")+input("dob","Date of Birth","22/06/1972");
        String role="<h3>Role</h3><div class='pair'>"+select("department","Department",employee.department())+select("job-title","Job title",employee.jobTitle())+"</div><label>Status</label><div class='radios'>";
        for(String status:new String[]{"active","on_leave","inactive"})role+="<label><input type='radio' name='status' "+(employee.status().equals(status)?"checked":"")+">"+switch(status){case "on_leave"->"On leave";case "inactive"->"Inactive";default->"Active";}+"</label>";
        add(Ui.html("<div class='detail-body'>"+header+fields+role+"</div></div>"));
        Div footer=new Div();footer.addClassName("detail-footer");
        var remove=Ui.html("<button class='remove'>Remove</button>");remove.getElement().addEventListener("click",e->onRemove.run());
        var cancel=Ui.html("<button class='cancel'>Cancel</button>");cancel.getElement().addEventListener("click",e->onCancel.run());
        var save=Ui.html("<button class='primary'>Save changes</button>");save.getElement().addEventListener("click",e->onSave.run());
        footer.add(remove,cancel,save);add(footer);
    }
    private String input(String key,String label,String value) {
        return "<div class='field "+key+"'><label for='"+key+"' data-testid='label-"+key+"'>"+label+"</label><input id='"+key+"' data-testid='field-"+key+"' value='"+Ui.escape(value)+"'></div>";
    }
    private String select(String key,String label,String value) {
        return "<div class='field'><label for='"+key+"'>"+label+"</label><select id='"+key+"' data-testid='field-"+key+"'><option>"+Ui.escape(value)+"</option></select></div>";
    }
}
