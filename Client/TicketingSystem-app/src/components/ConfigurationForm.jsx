// src/components/ConfigurationForm.jsx
import React, { useState } from 'react';

function ConfigurationForm({ onSave }) {
  const [formData, setFormData] = useState({
    totalTickets: '',
    ticketReleaseRate: '',
    customerRetrievalRate: '',
    maxTicketCapacity: '',
    numberOfVendors: '',
    numberOfCustomers: ''
  });

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    // totalTickets
    if (!formData.totalTickets) {
      newErrors.totalTickets = 'Total Tickets is required.';
    } else if (!Number.isInteger(Number(formData.totalTickets)) || Number(formData.totalTickets) < 1) {
      newErrors.totalTickets = 'Total Tickets must be an integer greater than 0.';
    }

    // ticketReleaseRate
    if (!formData.ticketReleaseRate) {
      newErrors.ticketReleaseRate = 'Ticket Release Rate is required.';
    } else if (!Number.isInteger(Number(formData.ticketReleaseRate)) || Number(formData.ticketReleaseRate) < 1) {
      newErrors.ticketReleaseRate = 'Ticket Release Rate must be an integer greater than 0 ms.';
    }

    // customerRetrievalRate
    if (!formData.customerRetrievalRate) {
      newErrors.customerRetrievalRate = 'Customer Retrieval Rate is required.';
    } else if (!Number.isInteger(Number(formData.customerRetrievalRate)) || Number(formData.customerRetrievalRate) < 1) {
      newErrors.customerRetrievalRate = 'Customer Retrieval Rate must be an integer greater than 0 ms.';
    }

    // maxTicketCapacity
    if (!formData.maxTicketCapacity) {
      newErrors.maxTicketCapacity = 'Max Ticket Capacity is required.';
    } else if (!Number.isInteger(Number(formData.maxTicketCapacity)) || Number(formData.maxTicketCapacity) < 1) {
      newErrors.maxTicketCapacity = 'Max Ticket Capacity must be an integer greater than 0.';
    }

    // numberOfVendors
    if (!formData.numberOfVendors) {
      newErrors.numberOfVendors = 'Number of Vendors is required.';
    } else if (!Number.isInteger(Number(formData.numberOfVendors)) || Number(formData.numberOfVendors) < 1) {
      newErrors.numberOfVendors = 'Number of Vendors must be an integer greater than 0.';
    }

    // numberOfCustomers
    if (!formData.numberOfCustomers) {
      newErrors.numberOfCustomers = 'Number of Customers is required.';
    } else if (!Number.isInteger(Number(formData.numberOfCustomers)) || Number(formData.numberOfCustomers) < 1) {
      newErrors.numberOfCustomers = 'Number of Customers must be an integer greater than 0.';
    }

    // Additional validation: maxTicketCapacity >= numberOfVendors and >= numberOfCustomers
    if (formData.maxTicketCapacity && formData.numberOfVendors && formData.numberOfCustomers) {
      const maxCapacity = Number(formData.maxTicketCapacity);
      const vendors = Number(formData.numberOfVendors);
      const customers = Number(formData.numberOfCustomers);
      if (maxCapacity < vendors || maxCapacity < customers) {
        newErrors.maxTicketCapacity = 'Max Ticket Capacity cannot be less than the number of vendors or customers.';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validate()) {
      // Convert to integers
      const configData = {
        totalTickets: parseInt(formData.totalTickets, 10),
        ticketReleaseRate: parseInt(formData.ticketReleaseRate, 10),
        customerRetrievalRate: parseInt(formData.customerRetrievalRate, 10),
        maxTicketCapacity: parseInt(formData.maxTicketCapacity, 10),
        numberOfVendors: parseInt(formData.numberOfVendors, 10),
        numberOfCustomers: parseInt(formData.numberOfCustomers, 10)
      };
      onSave(configData);
      // Optionally, reset form
      setFormData({
        totalTickets: '',
        ticketReleaseRate: '',
        customerRetrievalRate: '',
        maxTicketCapacity: '',
        numberOfVendors: '',
        numberOfCustomers: ''
      });
      setErrors({});
    }
  };

  return (
      <div style={styles.formContainer}>
        <form onSubmit={handleSubmit} style={styles.form}>
          <h2 style={styles.formTitle}>Create New Configuration</h2>
          <div style={styles.formGroup}>
            <label htmlFor="totalTickets" style={styles.label}>Total Tickets:</label>
            <input
                type="number"
                id="totalTickets"
                name="totalTickets"
                value={formData.totalTickets}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.totalTickets && <span style={styles.error}>{errors.totalTickets}</span>}
          </div>

          <div style={styles.formGroup}>
            <label htmlFor="ticketReleaseRate" style={styles.label}>Ticket Release Rate (ms):</label>
            <input
                type="number"
                id="ticketReleaseRate"
                name="ticketReleaseRate"
                value={formData.ticketReleaseRate}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.ticketReleaseRate && <span style={styles.error}>{errors.ticketReleaseRate}</span>}
          </div>

          <div style={styles.formGroup}>
            <label htmlFor="customerRetrievalRate" style={styles.label}>Customer Retrieval Rate (ms):</label>
            <input
                type="number"
                id="customerRetrievalRate"
                name="customerRetrievalRate"
                value={formData.customerRetrievalRate}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.customerRetrievalRate && <span style={styles.error}>{errors.customerRetrievalRate}</span>}
          </div>

          <div style={styles.formGroup}>
            <label htmlFor="maxTicketCapacity" style={styles.label}>Max Ticket Capacity:</label>
            <input
                type="number"
                id="maxTicketCapacity"
                name="maxTicketCapacity"
                value={formData.maxTicketCapacity}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.maxTicketCapacity && <span style={styles.error}>{errors.maxTicketCapacity}</span>}
          </div>

          <div style={styles.formGroup}>
            <label htmlFor="numberOfVendors" style={styles.label}>Number of Vendors:</label>
            <input
                type="number"
                id="numberOfVendors"
                name="numberOfVendors"
                value={formData.numberOfVendors}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.numberOfVendors && <span style={styles.error}>{errors.numberOfVendors}</span>}
          </div>

          <div style={styles.formGroup}>
            <label htmlFor="numberOfCustomers" style={styles.label}>Number of Customers:</label>
            <input
                type="number"
                id="numberOfCustomers"
                name="numberOfCustomers"
                value={formData.numberOfCustomers}
                onChange={handleChange}
                style={styles.input}
            />
            {errors.numberOfCustomers && <span style={styles.error}>{errors.numberOfCustomers}</span>}
          </div>

          <button type="submit" style={styles.submitButton}>Save Configuration</button>
        </form>
      </div>
  );
}

const styles = {
  formContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '20px'
  },
  form: {
    backgroundColor: '#ffffff',
    padding: '30px',
    borderRadius: '10px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
    width: '100%',
    maxWidth: '500px',
    boxSizing: 'border-box'
  },
  formTitle: {
    textAlign: 'center',
    marginBottom: '20px',
    color: '#333333'
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '15px'
  },
  label: {
    marginBottom: '5px',
    color: '#555555',
    fontSize: '14px'
  },
  input: {
    padding: '10px',
    borderRadius: '5px',
    border: '1px solid #cccccc',
    fontSize: '14px'
  },
  submitButton: {
    width: '100%',
    padding: '12px',
    backgroundColor: '#28a745',
    color: '#ffffff',
    border: 'none',
    borderRadius: '5px',
    fontSize: '16px',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease'
  },
  error: {
    color: '#dc3545',
    fontSize: '12px',
    marginTop: '5px'
  }
};

export default ConfigurationForm;
