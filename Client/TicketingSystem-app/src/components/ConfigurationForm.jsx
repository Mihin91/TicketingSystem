// src/components/ConfigurationForm.jsx
import React, { useState } from 'react';

function ConfigurationForm({ onSave }) {
  const [config, setConfig] = useState({
    totalTickets: '',
    ticketReleaseRate: '',
    customerRetrievalRate: '',
    maxTicketCapacity: '',
    numberOfVendors: '',
    numberOfCustomers: '',
    releaseInterval: '' // Added this field
  });

  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);
  const [saveSuccess, setSaveSuccess] = useState(false);
  const [saveError, setSaveError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setConfig({
      ...config,
      [name]: value === '' ? '' : Number(value), // Convert to number if not empty
    });
    setErrors({ ...errors, [name]: '' }); // Clear error on change
    setSaveSuccess(false);
    setSaveError(null);
  };

  const validate = () => {
    const newErrors = {};
    Object.entries(config).forEach(([key, value]) => {
      if (value === '') {
        newErrors[key] = 'This field is required.';
      } else if (isNaN(value) || value <= 0) {
        newErrors[key] = 'Please enter a positive number.';
      }
    });
    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length === 0) {
      try {
        setSaving(true);
        await onSave(config);
        setConfig({
          totalTickets: '',
          ticketReleaseRate: '',
          customerRetrievalRate: '',
          maxTicketCapacity: '',
          numberOfVendors: '',
          numberOfCustomers: '',
          releaseInterval: '' // Reset this field as well
        });
        setSaveSuccess(true);
      } catch (error) {
        setSaveError(error.message);
      } finally {
        setSaving(false);
      }
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
              min="1"
              required
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
              min="1"
              required
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
              min="1"
              required
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
              min="1"
              required
          />
          {errors.maxTicketCapacity && <span style={styles.error}>{errors.maxTicketCapacity}</span>}
        </div>

        <div style={styles.formGroup}>
          <label htmlFor="numberOfVendors">Number of Vendors:</label>
          <input
              type="number"
              id="numberOfVendors"
              name="numberOfVendors"
              value={config.numberOfVendors}
              onChange={handleChange}
              placeholder="Enter number of vendors"
              style={styles.input}
              min="1"
              required
          />
          {errors.numberOfVendors && <span style={styles.error}>{errors.numberOfVendors}</span>}
        </div>

        <div style={styles.formGroup}>
          <label htmlFor="numberOfCustomers">Number of Customers:</label>
          <input
              type="number"
              id="numberOfCustomers"
              name="numberOfCustomers"
              value={config.numberOfCustomers}
              onChange={handleChange}
              placeholder="Enter number of customers"
              style={styles.input}
              min="1"
              required
          />
          {errors.numberOfCustomers && <span style={styles.error}>{errors.numberOfCustomers}</span>}
        </div>

        <div style={styles.formGroup}>
          <label htmlFor="releaseInterval">Release Interval (ms):</label>
          <input
              type="number"
              id="releaseInterval"
              name="releaseInterval"
              value={config.releaseInterval}
              onChange={handleChange}
              placeholder="Enter release interval in milliseconds"
              style={styles.input}
              min="1"
              required
          />
          {errors.releaseInterval && <span style={styles.error}>{errors.releaseInterval}</span>}
        </div>

        <button type="submit" style={styles.button} disabled={saving}>
          {saving ? 'Saving...' : 'Save Configuration'}
        </button>
        {saveSuccess && <p style={styles.success}>Configuration saved successfully!</p>}
        {saveError && <p style={styles.error}>{saveError}</p>}
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
  },
  success: {
    color: 'green',
    fontSize: '12px'
  }
};

export default ConfigurationForm;
