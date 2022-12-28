package shape.model;

public class UserBusiness {
    Long id;
    Long userId;
    Long businessId;
    boolean active;

    Long years;
    Long dateStarted;
    String position;
    boolean partTime;

    Business business;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getYears() {
        return years;
    }

    public void setYears(Long years) {
        this.years = years;
    }

    public Long getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Long dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isPartTime() {
        return partTime;
    }

    public void setPartTime(boolean partTime) {
        this.partTime = partTime;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
