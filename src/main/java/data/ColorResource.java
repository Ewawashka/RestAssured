package data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class ColorResource {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private Support support;
    private List<Color> data;

    public ColorResource(Integer page, Integer per_page, Integer total, Integer total_pages, Support support, List<Color> data) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.support = support;
        this.data = data;
    }

    public ColorResource() { super();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public List<Color> getData() {
        return data;
    }

    public void setData(List<Color> data) {
        this.data = data;
    }
}
