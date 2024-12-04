
import React from 'react';

function ConfigurationDisplay({ config }) {
  if (!config) {
    return (
      <div style={styles.container}>
        <h2>Saved Configuration</h2>
        <p>No configuration saved yet.</p>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <h2>Saved Configuration</h2>
      <ul style={styles.list}>
        <li><strong>Total Tickets:</strong> {config.totalTickets}</li>
        <li><strong>Ticket Release Rate (ms):</strong> {config.ticketReleaseRate}</li>
        <li><strong>Customer Retrieval Rate (ms):</strong> {config.customerRetrievalRate}</li>
        <li><strong>Max Ticket Capacity:</strong> {config.maxTicketCapacity}</li>
      </ul>
    </div>
  );
}

const styles = {
  container: {
    border: '1px solid #007bff',
    padding: '20px',
    borderRadius: '5px',
    backgroundColor: '#e7f1ff',
    marginTop: '20px'
  },
  list: {
    listStyleType: 'none',
    padding: 0
  }
};

export default ConfigurationDisplay;
