import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import ApiClient from './ApiClient';
import Navbar from './components/Navbar';
import ConfigPage from './pages/ConfigPage';
import DashboardPage from './pages/DashboardPage';

function App() {
  const [client, setClient] = useState(null);
  const [status, setStatus] = useState({ currentTickets: 0, totalTicketsReleased: 0, totalTicketsPurchased: 0 });
  const [logs, setLogs] = useState([]);
  const [config, setConfig] = useState(null);
  const [isRunning, setIsRunning] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  // Initialize WebSocket connection only once
  useEffect(() => {
    const stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      debug: (str) => {
        console.log(str);
      },
    });

    stompClient.onConnect = () => {
      console.log('Connected to WebSocket');
      setIsLoading(false);

      // Subscribe to ticket status updates
      stompClient.subscribe('/topic/tickets/status', (message) => {
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
      console.error('Broker error: ' + frame.headers['message']);
      console.error('Details: ' + frame.body);
      setError('An error occurred with the WebSocket connection.');
      setIsLoading(false);
    };

    stompClient.onWebSocketClose = () => {
      console.warn('WebSocket connection closed.');
      // Show a message in the UI when WebSocket is closed
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
  }, []); // No config or isRunning in dependencies

  // Use a separate effect to handle stopping the simulation when all tickets are sold
  useEffect(() => {
    if (config && isRunning && status.totalTicketsPurchased >= config.totalTickets) {
      alert("All tickets have been sold. Stopping the simulation.");
      handleStop();
    }
  }, [config, isRunning, status.totalTicketsPurchased]);

  const handleSaveConfiguration = async (configData) => {
    try {
      const savedConfig = await ApiClient.saveConfiguration(configData);
      setConfig(savedConfig);
      setError(null);
    } catch (error) {
      setError(error.message);
    }
  };

  const handleStart = async () => {
    try {
      const response = await ApiClient.startSystem();
      console.log(response.message);
      setIsRunning(true);
      setError(null);
    } catch (error) {
      setError(error.message);
    }
  };

  const handleStop = async () => {
    try {
      const response = await ApiClient.stopSystem();
      console.log(response.message);
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
      <Router>
        <div className="app">
          <Navbar />
          {error && (
              <div style={styles.errorContainer}>
                <p>{error}</p>
              </div>
          )}
          <div className="app-container">
            <Routes>
              <Route
                  path="/config"
                  element={
                    <ConfigPage
                        onSave={handleSaveConfiguration}
                        config={config}
                    />
                  }
              />
              <Route
                  path="/dashboard"
                  element={
                    <DashboardPage
                        onStart={handleStart}
                        onStop={handleStop}
                        isRunning={isRunning}
                        config={config}
                        status={status}
                        logs={logs}
                    />
                  }
              />
              <Route
                  path="*"
                  element={
                    <ConfigPage
                        onSave={handleSaveConfiguration}
                        config={config}
                    />
                  }
              />
            </Routes>
          </div>
        </div>
      </Router>
  );
}

const styles = {
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
