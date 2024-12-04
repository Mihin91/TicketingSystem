// src/main/java/lk/ac/iit/Mihin/Server/DTO/CustomerDTO.java
package lk.ac.iit.Mihin.Server.DTO;


public class CustomerDTO {
    private int customerId;
    private int retrievalInterval;

    public CustomerDTO() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
}
