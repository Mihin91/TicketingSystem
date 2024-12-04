// src/components/ControlPanel.jsx
import React from 'react';

function ControlPanel({ onStart, onStop, isRunning, config }) {
  const handleStart = () => {
    if (config) {
      onStart(config);
    } else {
      alert('Please save a configuration before starting the simulation.');
    }
  };

  return (
    <div style={styles.container}>
      <h2>Control Panel</h2>
      <div style={styles.buttonGroup}>
        <button
          onClick={handleStart}
          style={{ ...styles.startButton }}
          disabled={isRunning || !config}
        >
          Start 
        </button>
        <button
          onClick={onStop}
          style={{ ...styles.stopButton }}
          disabled={!isRunning}
        >
          Stop 
        </button>
      </div>
    </div>
  );
}

const styles = {
  container: {
    textAlign: 'center',
    margin: '20px 0'
  },
  buttonGroup: {
    display: 'flex',
    justifyContent: 'space-between'
  },
  startButton: {
    padding: '10px 15px',
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    borderRadius: '3px',
    cursor: 'pointer',
    flex: '1',
    marginRight: '10px',
    fontSize: '16px',
    transition: 'background-color 0.3s ease'
  },
  stopButton: {
    padding: '10px 15px',
    backgroundColor: '#dc3545',
    color: '#fff',
    border: 'none',
    borderRadius: '3px',
    cursor: 'pointer',
    flex: '1',
    marginLeft: '10px',
    fontSize: '16px',
    transition: 'background-color 0.3s ease'
  }
};

export default ControlPanel;
