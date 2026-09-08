package com.example.employees;

import com.vaadin.flow.component.html.Div;

/** Placement belongs to the route's wrapper; this component only lays out its contents. */
public class EmployeeDetailPanel extends Div {
    public EmployeeDetailPanel(Employee employee, Runnable onSave, Runnable onCancel, Runnable onRemove) {
        addClassName("detail-card");
        getElement().setAttribute("data-testid", "panel-surface");
        String header = "<div class='detail-header'><h2>" + Ui.escape(employee.name())
                + "</h2><span class='task-count'>13 assigned tasks</span>"
                + "<div class='tenure'>4 years 3 months in service</div></div>";
        String fields = "<div class='pair'>"
                + input("first-name", "First name", employee.firstName())
                + input("last-name", "Last name", employee.lastName()) + "</div>"
                + input("phone", "Phone", "+91 555 1213 456")
                + input("email", "Email", "liam.johnson@example.com")
                + input("dob", "Date of Birth", "22/06/1972");
        StringBuilder role = new StringBuilder("<section class='role'><h3>Role</h3><div class='pair'>")
                .append(select("department", "Department", employee.department()))
                .append(select("job-title", "Job title", employee.jobTitle()))
                .append("</div><div class='status-label'>Status</div><div class='radios' role='radiogroup' aria-label='Status'>");
        for (String status : new String[]{"active", "on_leave", "inactive"}) {
            String label = switch (status) {
                case "on_leave" -> "On leave";
                case "inactive" -> "Inactive";
                default -> "Active";
            };
            role.append("<label><input type='radio' name='status' ")
                    .append(employee.status().equals(status) ? "checked" : "")
                    .append("> ").append(label).append("</label>");
        }
        role.append("</div></section>");
        add(Ui.html("<div class='detail-body'>" + header + fields + role + "</div>"));
        Div footer = new Div();
        footer.addClassName("detail-footer");
        var remove = Ui.html("<button class='remove'>Remove</button>");
        remove.getElement().addEventListener("click", event -> onRemove.run());
        var cancel = Ui.html("<button class='cancel'>Cancel</button>");
        cancel.getElement().addEventListener("click", event -> onCancel.run());
        var save = Ui.html("<button class='primary'>Save changes</button>");
        save.getElement().addEventListener("click", event -> onSave.run());
        footer.add(remove, cancel, save);
        add(footer);
    }

    private static String input(String key, String label, String value) {
        return "<div class='field " + key + "'><label for='" + key + "' data-testid='label-" + key + "'>"
                + label + "</label><div class='control-wrap'><input type='text' id='" + key
                + "' data-testid='field-" + key + "' value='" + Ui.escape(value) + "'>"
                + (key.equals("dob") ? Ui.icon("calendar") : "") + "</div></div>";
    }

    private static String select(String key, String label, String value) {
        return "<div class='field'><label for='" + key + "'>" + label
                + "</label><div class='control-wrap'><select id='" + key + "' data-testid='field-" + key
                + "'><option>" + Ui.escape(value) + "</option></select>" + Ui.icon("chevron") + "</div></div>";
    }
}
