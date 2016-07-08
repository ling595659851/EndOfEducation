package cn.edu.bupt.springmvc.web.model;

public class ComRegular {
    private Long id;

    private String company;

    private String name;

    private String type;

    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    
    public void clear() {
    	this.id = 0L;
    	this.company = "";
    	this.count = 0L;
    	this.name = "";
    	this.type = "";
    }

	@Override
	public String toString() {
		return "ComRegular [id=" + id + ", company=" + company + ", name="
				+ name + ", type=" + type + ", count=" + count + "]";
	}
    
}