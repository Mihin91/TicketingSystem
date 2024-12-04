// src/App.jsx
import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import ConfigurationForm from './components/ConfigurationForm';
import ControlPanel from './components/ControlPanel';
import TicketStatus from './components/TicketStatus';
import LogDisplay from './components/LogDisplay';
import ConfigurationDisplay from './components/ConfigurationDisplay';

function App() {
  const [client, setClient] = useState(null);
  const [status, setStatus] = useState({ currentTickets: 0 });
  const [logs, setLogs] = useState([]);
  const [config, setConfig] = useState(null);
  const [isRunning, setIsRunning] = useState(false);
  const [isLoading, setIsLoading] = useState(true); // State for loading
  const [error, setError] = useState(null); // State for errors

  useEffect(() => {
    const stompClient = new Client({
      // Use absolute URL for SockJS
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      debug: (str) => {
        console.log(str);
      },
    });

    stompClient.onConnect = () => {
      console.log('Connected to WebSocket');
      setIsLoading(false); // Connection established

      // Subscribe to status updates
      stompClient.subscribe('/topic/status', (message) => {
        const statusUpdate = JSON.parse(message.body);
        setStatus(statusUpdate);
      });

      // Subscribe to log messages
      stompClient.subscribe('/topic/logs', (message) => {
        const logMessage = message.body;
        console.log('Received log:', logMessage);
        setLogs((prevLogs) => [...prevLogs, logMessage]);
      });
    };

    stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
      setError('An error occurred with the WebSocket connection.');
      setIsLoading(false);
    };

    stompClient.onWebSocketClose = () => {
      console.warn('WebSocket connection closed.');
      setError('WebSocket connection closed.');
      setIsLoading(false);
    };

    stompClient.activate();
    setClient(stompClient);

    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, []);

  const handleStart = (configData) => {
    if (client && client.active) {
      client.publish({
        destination: '/app/start',
        body: JSON.stringify(configData),
      });
      console.log('Simulation started with config:', configData);
      setConfig(configData);
      setIsRunning(true);
      setError(null); // Clear any existing errors
    } else {
      setError('WebSocket is not connected.');
    }
  };

  const handleStop = () => {
    if (client && client.active) {
      client.publish({ destination: '/app/stop' });
      console.log('Simulation stopped.');
      setIsRunning(false);
      setError(null); // Clear any existing errors
    } else {
      setError('WebSocket is not connected.');
    }
  };

  if (isLoading) {
    return (
      <div style={styles.loadingContainer}>
        <p>Connecting to the simulation server...</p>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <h1>Ticket Simulation Dashboard</h1>
      {error && (
        <div style={styles.errorContainer}>
          <p>{error}</p>
        </div>
      )}
      <div style={styles.grid}>
        <div style={styles.column}>
          <ConfigurationForm onStart={handleStart} isRunning={isRunning} />
          <ConfigurationDisplay config={config} />
          <ControlPanel onStart={handleStart} onStop={handleStop} isRunning={isRunning} config={config} />
        </div>
        <div style={styles.column}>
          <TicketStatus status={status} />
          <LogDisplay logs={logs} />
        </div>
      </div>
    </div>
  );
}

const styles = {
  container: {
    fontFamily: 'Arial, sans-serif',
    padding: '20px',
    backgroundColor: '#f4f6f8',
    minHeight: '100vh'
  },
  grid: {
    display: 'flex',
    flexWrap: 'wrap',
    gap: '20px',
    marginTop: '20px'
  },
  column: {
    flex: '1',
    minWidth: '300px'
  },
  loadingContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    fontSize: '18px'
  },
  errorContainer: {
    backgroundColor: '#f8d7da',
    color: '#721c24',
    border: '1px solid #f5c6cb',
    padding: '10px 20px',
    borderRadius: '5px',
    maxWidth: '800px',
    margin: '10px auto'
  }
};

export default App;
