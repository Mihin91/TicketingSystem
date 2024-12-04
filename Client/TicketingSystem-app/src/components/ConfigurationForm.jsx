// src/components/ConfigurationForm.jsx
import React, { useState } from 'react';

function ConfigurationForm({ onStart }) {
  const [config, setConfig] = useState({
    totalTickets: '',
    ticketReleaseRate: '',
    customerRetrievalRate: '',
    maxTicketCapacity: ''
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setConfig({ ...config, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: '' }); // Clear error on change
  };

  const validate = () => {
    const newErrors = {};
    Object.entries(config).forEach(([key, value]) => {
      if (!value) {
        newErrors[key] = 'This field is required.';
      } else if (Number(value) <= 0) {
        newErrors[key] = 'Please enter a positive number.';
      }
    });
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length === 0) {
      onStart(config);
      setConfig({
        totalTickets: '',
        ticketReleaseRate: '',
        customerRetrievalRate: '',
        maxTicketCapacity: ''
      });
    } else {
      setErrors(validationErrors);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
      <h2>Simulation Configuration</h2>
      
      <div style={styles.formGroup}>
        <label htmlFor="totalTickets">Total Tickets:</label>
        <input
          type="number"
          id="totalTickets"
          name="totalTickets"
          value={config.totalTickets}
          onChange={handleChange}
          placeholder="Enter total number of tickets"
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
          value={config.ticketReleaseRate}
          onChange={handleChange}
          placeholder="Enter ticket release rate in milliseconds"
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
          value={config.customerRetrievalRate}
          onChange={handleChange}
          placeholder="Enter customer retrieval rate in milliseconds"
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
          value={config.maxTicketCapacity}
          onChange={handleChange}
          placeholder="Enter maximum ticket capacity"
          style={styles.input}
        />
        {errors.maxTicketCapacity && <span style={styles.error}>{errors.maxTicketCapacity}</span>}
      </div>

      <button type="submit" style={styles.button}>Save Configuration</button>
    </form>
  );
}

const styles = {
  form: {
    border: '1px solid #ccc',
    padding: '20px',
    borderRadius: '5px',
    maxWidth: '500px',
    margin: '20px auto',
    backgroundColor: '#f9f9f9'
  },
  formGroup: {
    marginBottom: '15px'
  },
  input: {
    width: '100%',
    padding: '8px',
    marginTop: '5px',
    boxSizing: 'border-box'
  },
  button: {
    padding: '10px 15px',
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '3px',
    cursor: 'pointer'
  },
  error: {
    color: 'red',
    fontSize: '12px'
  }
};

export default ConfigurationForm;
