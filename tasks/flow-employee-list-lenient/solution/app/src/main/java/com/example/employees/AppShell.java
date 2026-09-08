package com.example.employees;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class AppShell extends Div {
    public AppShell(Component content) {
        addClassName("app-shell");
        Div sidebar = new Div();
        sidebar.addClassName("sidebar");
        sidebar.getElement().setAttribute("data-testid", "sidebar");
        sidebar.add(Ui.html("<div class='brand'><img src='employee-assets/acme.png' alt='ACME CORP'></div>"));
        String[][] entries = {
                {"Dashboard", "dashboard"}, {"Sales", ""}, {"Orders", "orders"},
                {"Deliveries", "deliveries"}, {"Reports", "reports"}, {"Resources", ""},
                {"Employees", "employees"}, {"Utilisation", "utilisation"}, {"Payroll", "payroll"},
                {"Admin", ""}, {"Access management", "access"}, {"Settings", "settings"}
        };
        for (String[] entry : entries) {
            String label = entry[0];
            if (entry[1].isEmpty()) {
                sidebar.add(Ui.html("<div class='nav-group'>" + label + "</div>"));
            } else {
                boolean active = label.equals("Employees");
                sidebar.add(Ui.html("<div class='nav-item " + (active ? "active" : "") + "' "
                        + (active ? "data-testid='nav-employees' aria-current='page'" : "")
                        + ">" + Ui.icon(entry[1]) + "<span>" + label + "</span></div>"));
            }
        }
        sidebar.add(Ui.html("<div class='account' data-testid='account'><span class='avatar'>FL</span>"
                + "<span>Firstname Lastname</span><span class='account-chevron'>" + Ui.icon("account-chevron") + "</span></div>"));
        var toggle = Ui.html("<button class='menu-toggle' data-testid='menu-toggle' aria-label='Menu' aria-expanded='false'>☰</button>");
        toggle.getElement().addEventListener("click", event -> {
            var classes = sidebar.getElement().getClassList();
            boolean open = !classes.contains("drawer-open");
            if (open) classes.add("drawer-open"); else classes.remove("drawer-open");
            toggle.getElement().setAttribute("aria-expanded", String.valueOf(open));
        });
        add(sidebar, toggle, content);
    }
}
