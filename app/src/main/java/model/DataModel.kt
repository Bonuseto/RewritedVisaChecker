package model;

public class DataModel {

    String applicationNumber;
    String status;

    public DataModel(String applicationNumber, String status) {
        this.applicationNumber = applicationNumber;
        this.status = status;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public String getStatus() {
        return status;
    }

}
