package cn.edu.bupt.springmvc.web.model;

public class TypeRegular {
    private Long id;

    private String name;

    private String value;

    private String type;

    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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
    	this.value = "";
    	this.count = 0L;
    	this.name = "";
    	this.type = "";
    }

	@Override
	public String toString() {
		return "TypeRegular [id=" + id + ", name=" + name + ", value=" + value
				+ ", type=" + type + ", count=" + count + "]";
	}
}