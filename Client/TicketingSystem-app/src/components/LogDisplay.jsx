// src/components/LogDisplay.jsx
import React, { useEffect, useRef } from 'react';

function LogDisplay({ logs }) {
  const logEndRef = useRef(null);

  useEffect(() => {
    // Scroll to the bottom when a new log is added
    if (logEndRef.current) {
      logEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [logs]);

  return (
    <div style={styles.container}>
      <h2>Logs</h2>
      <div style={styles.logContainer}>
        {logs.length === 0 ? (
          <p>No logs available.</p>
        ) : (
          <ul style={styles.logList}>
            {logs.map((log, index) => (
              <li key={index} style={styles.logItem}>{log}</li>
            ))}
            <div ref={logEndRef} />
          </ul>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: {
    border: '1px solid #ffc107',
    padding: '15px',
    borderRadius: '5px',
    backgroundColor: '#fff8e1',
    marginBottom: '20px'
  },
  logContainer: {
    maxHeight: '300px',
    overflowY: 'auto',
    marginTop: '10px',
    paddingRight: '10px'
  },
  logList: {
    listStyleType: 'none',
    padding: 0,
    margin: 0
  },
  logItem: {
    padding: '5px 0',
    borderBottom: '1px solid #f1c40f'
  }
};

export default LogDisplay;
