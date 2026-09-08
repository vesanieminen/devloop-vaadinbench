package com.example.employees;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
public class AppShell extends Div {
    public AppShell(Component content) {
        addClassName("app-shell");
        Div sidebar=new Div(); sidebar.addClassName("sidebar"); sidebar.getElement().setAttribute("data-testid","sidebar");
        sidebar.add(Ui.html("<div class='brand'><b>ACME</b><br><i>CORP</i><svg viewBox='0 0 50 70' width='50' height='70'><path d='m10 3 20 30-10 7 15 12-11 7 22 10H25L12 54l13-8-17-9 9-10z' fill='#59b4ab'/></svg></div>"));
        for(String label:new String[]{"Dashboard","Sales","Orders","Deliveries","Reports","Resources","Employees","Utilisation","Payroll","Admin","Access management","Settings"}) {
            if(java.util.List.of("Sales","Resources","Admin").contains(label)) sidebar.add(Ui.html("<div class='nav-group'>"+label+"</div>"));
            else sidebar.add(Ui.html("<div class='nav-item "+(label.equals("Employees")?"active":"")+"' "+(label.equals("Employees")?"data-testid='nav-employees' aria-current='page'":"")+">"+Ui.icon()+"<span>"+label+"</span></div>"));
        }
        sidebar.add(Ui.html("<div class='account' data-testid='account'><span class='avatar'>FL</span>Firstname Lastname<span class='chevron'>⌄</span></div>"));
        var toggle=Ui.html("<button class='menu-toggle' data-testid='menu-toggle' aria-label='Menu'>☰</button>");
        toggle.getElement().addEventListener("click",e -> { var classes=sidebar.getElement().getClassList(); if(classes.contains("drawer-open")) classes.remove("drawer-open"); else classes.add("drawer-open"); });
        add(sidebar,toggle,content);
    }
}
