package lk.ac.iit.Mihin.Server.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO {

    @NotNull(message = "Customer ID is required.")
    @Min(value = 1, message = "Customer ID must be a at least 1.")
    private Integer customerId;

    @NotNull(message = "Retrieval interval is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    private Integer retrievalInterval;

    public CustomerDTO() {}

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(Integer retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }
}
