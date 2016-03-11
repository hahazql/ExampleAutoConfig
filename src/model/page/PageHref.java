package model.page;

/**
 * Created by zql on 2015/10/10.
 */

/**
 * 导航链接
 */
public class PageHref
{
    private String id;
    private String text;
    private String href;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
