// src/components/LogDisplay.jsx
import React, { useEffect, useRef } from 'react';

function LogDisplay({ logs }) {
  const logContainerRef = useRef(null);
  const logEndRef = useRef(null);
  const shouldScrollRef = useRef(true);

  useEffect(() => {
    const logContainer = logContainerRef.current;

    const handleScroll = () => {
      const { scrollTop, scrollHeight, clientHeight } = logContainer;
      const isNearBottom = scrollHeight - scrollTop - clientHeight < 100;
      shouldScrollRef.current = isNearBottom;
    };

    if (logContainer) {
      logContainer.addEventListener('scroll', handleScroll);
    }

    return () => {
      if (logContainer) {
        logContainer.removeEventListener('scroll', handleScroll);
      }
    };
  }, []);

  useEffect(() => {
    if (shouldScrollRef.current && logEndRef.current) {
      logEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [logs]);

  return (
      <div style={styles.container}>
        <h2>Logs</h2>
        <div style={styles.logContainer} ref={logContainerRef}>
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
