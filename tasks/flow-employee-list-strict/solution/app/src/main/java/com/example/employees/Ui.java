package com.example.employees;
import com.vaadin.flow.component.Html;
final class Ui {
    static Html html(String markup) {return new Html(markup);}
    static String escape(String value) {return value.replace("&","&amp;").replace("<","&lt;").replace("'","&#39;");}
    static String icon() {return "<svg viewBox='0 0 24 24' width='24' height='24' fill='none' stroke='currentColor' stroke-width='1.7' aria-hidden='true'><path d='M3 10 12 3 21 10v11h-6v-8H9v8H3z'/></svg>";}
}
