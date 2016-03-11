package model.page;

/**
 * Created by zql on 2015/10/10.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 左侧导航
 */
public class PageMenu
{
    private String id;
    private boolean collapsed = true;
    private String homePage;
    private String text ;
    private List<PageMenu> menu = new ArrayList<PageMenu>();
    private List<PageHref> items = new ArrayList<PageHref>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public List<PageMenu> getMenu() {
        return menu;
    }

    public void addMenu(PageMenu href)
    {
        this.menu.add(href);
    }

    public void setMenu(List<PageMenu> menu) {
        this.menu = menu;
    }

    public List<PageHref> getItems() {
        return items;
    }

    public void addItems(PageHref item)
    {
        this.items.add(item);
    }

    public void setItems(List<PageHref> items) {
        this.items = items;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
