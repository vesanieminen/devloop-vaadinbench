package com.example.employees;

import com.vaadin.flow.component.Html;

final class Ui {
    private Ui() {}

    static Html html(String markup) { return new Html(markup); }

    static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace("'", "&#39;");
    }

    static String icon(String name) {
        return "<img class='icon' src='employee-assets/" + name + ".png' alt='' aria-hidden='true'>";
    }
}
