// src/App.jsx
import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import ConfigurationForm from './components/ConfigurationForm';
import ControlPanel from './components/ControlPanel';
import TicketStatus from './components/TicketStatus';
import LogDisplay from './components/LogDisplay';
import ConfigurationDisplay from './components/ConfigurationDisplay';
import ApiClient from './ApiClient';

function App() {
  const [client, setClient] = useState(null);
  const [status, setStatus] = useState({ currentTickets: 0, totalTicketsReleased: 0, totalTicketsPurchased: 0 });
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

      // Subscribe to ticket status updates
      stompClient.subscribe('/topic/tickets/status', (message) => {
        const statusUpdate = JSON.parse(message.body);
        setStatus(statusUpdate);

        // Stop the simulation if all tickets are sold
        if (statusUpdate.totalTicketsPurchased >= config.totalTickets) {
          alert("All tickets have been sold. Stopping the simulation.");
          handleStop();
        }
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

  /**
   * Handles saving the configuration by calling the backend API.
   *
   * @param {Object} configData - Configuration data from the form.
   */
  const handleSaveConfiguration = async (configData) => {
    try {
      const savedConfig = await ApiClient.saveConfiguration(configData);
      setConfig(savedConfig);
      setError(null);
      // Optionally, show success message
    } catch (error) {
      setError(error.message);
    }
  };

  /**
   * Handles starting the simulation by calling the backend API.
   */
  const handleStart = async () => {
    try {
      const response = await ApiClient.startSystem();
      console.log(response.message); // Optional: Display success message
      setIsRunning(true);
      setError(null);
    } catch (error) {
      setError(error.message);
    }
  };

  /**
   * Handles stopping the simulation by calling the backend API.
   */
  const handleStop = async () => {
    try {
      const response = await ApiClient.stopSystem();
      console.log(response.message); // Optional: Display success message
      setIsRunning(false);
      setError(null);
    } catch (error) {
      setError(error.message);
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
            <ConfigurationForm onSave={handleSaveConfiguration} />
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
