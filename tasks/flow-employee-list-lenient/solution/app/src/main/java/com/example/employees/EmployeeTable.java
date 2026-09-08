package com.example.employees;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;
import java.util.*;
import java.util.function.Consumer;
public class EmployeeTable extends Div {
    private final Map<String,Element> rowElements=new LinkedHashMap<>();
    public EmployeeTable(List<Employee> rows,String selectedId,Consumer<Employee> onSelectRow) {
        addClassName("table-region");getElement().setAttribute("data-testid","employee-table");
        Element table=new Element("table"),head=new Element("thead"),hr=new Element("tr"),body=new Element("tbody");
        head.setAttribute("data-testid","table-header");
        for(String title:List.of("Name","Department","Job title","Status","Start date"))hr.appendChild(new Element("th").setText(title));
        head.appendChild(hr);table.appendChild(head,body);getElement().appendChild(table);
        for(Employee row:rows) {
            Element tr=new Element("tr");tr.setAttribute("data-row-id",row.id());tr.setAttribute("aria-selected","false");
            for(String value:List.of(row.name(),row.department(),row.jobTitle()))tr.appendChild(new Element("td").setText(value));
            Element status=new Element("td");status.appendChild(new StatusBadge(row.status()).getElement());tr.appendChild(status,new Element("td").setText(row.startDate()));
            tr.addEventListener("click",e->onSelectRow.accept(row));body.appendChild(tr);rowElements.put(row.id(),tr);
        }
        select(selectedId);
    }
    public void select(String id) {rowElements.forEach((key,row)->row.setAttribute("aria-selected",String.valueOf(key.equals(id))));}
}
