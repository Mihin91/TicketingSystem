// src/components/ControlPanel.jsx
import React from 'react';

function ControlPanel({ onStart, onStop, isRunning, onReset, config }) {
  const handleStart = () => {
    if (config) {
      onStart();
    } else {
      alert('Please save a configuration before starting the simulation.');
    }
  };

  return (
      <div style={styles.container}>
        <h2 style={styles.title}>Control Panel</h2>
        <div style={styles.buttonGroup}>
          <button
              onClick={handleStart}
              style={styles.startButton}
              disabled={isRunning || !config}
          >
            Start
          </button>
          <button
              onClick={onStop}
              style={styles.stopButton}
              disabled={!isRunning}
          >
            Stop
          </button>
          <button
              onClick={onReset}
              style={styles.resetButton}
              disabled={isRunning}
          >
            Reset
          </button>
        </div>
      </div>
  );
}

const styles = {
  container: {
    backgroundColor: 'rgba(161,140,209,0.8)',
    padding: '15px',
    border: '1px solid #8a6dbe',
    borderRadius: '15px',
    textAlign: 'center',
    margin: '3px 0px 20px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
  },
  title: {
    margin: '0 0 10px 0',
    color: '#fff',
    fontSize: '1.5rem',
    fontWeight: 'bold',
  },
  buttonGroup: {
    display: 'flex',
    justifyContent: 'space-between',
  },
  startButton: {
    padding: '10px 15px',
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    borderRadius: '15px',
    cursor: 'pointer',
    flex: '1',
    marginRight: '10px',
    fontSize: '16px',
    transition: 'background-color 0.3s ease',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)',
  },
  stopButton: {
    padding: '10px 15px',
    backgroundColor: '#dc3545',
    color: '#fff',
    border: 'none',
    borderRadius: '15px',
    cursor: 'pointer',
    flex: '1',
    marginLeft: '10px',
    fontSize: '16px',
    transition: 'background-color 0.3s ease',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)',
  },
  resetButton: {
    padding: '10px 15px',
    backgroundColor: '#ffc107',
    color: '#fff',
    border: 'none',
    borderRadius: '15px',
    cursor: 'pointer',
    flex: '1',
    marginLeft: '10px',
    fontSize: '16px',
    transition: 'background-color 0.3s ease',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)',
  },
};

export default ControlPanel;
