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
    const {name, value} = e.target;
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
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <label htmlFor="totalTickets">Total Tickets:</label>
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
          <label htmlFor="ticketReleaseRate">Ticket Release Rate (ms):</label>
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
          <label htmlFor="customerRetrievalRate">Customer Retrieval Rate (ms):</label>
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
          <label htmlFor="maxTicketCapacity">Max Ticket Capacity:</label>
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
          <label htmlFor="numberOfVendors">Number of Vendors:</label>
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
          <label htmlFor="numberOfCustomers">Number of Customers:</label>
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
  );
}
  const styles = {
    form: {
      display: 'flex',
      flexDirection: 'column',
      gap: '10px'
    },
    formGroup: {
      display: 'flex',
      flexDirection: 'column'
    },
    input: {
      padding: '8px',
      borderRadius: '4px',
      border: '1px solid #ccc',
      fontSize: '14px'
    },
    submitButton: {
      padding: '10px',
      backgroundColor: '#007bff',
      color: '#fff',
      border: 'none',
      borderRadius: '5px',
      cursor: 'pointer',
      fontSize: '16px'
    },
    error: {
      color: 'red',
      fontSize: '12px',
      marginTop: '2px'
    }
  };

export default ConfigurationForm;
