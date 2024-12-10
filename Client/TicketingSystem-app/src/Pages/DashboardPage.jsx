// src/pages/DashboardPage.jsx
import React from 'react';
import ControlPanel from '../components/ControlPanel';
import TicketStatus from '../components/TicketStatus';
import LogDisplay from '../components/LogDisplay';

function DashboardPage({ onStart, onStop, isRunning, config, status, logs, onReset }) {
    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Dashboard</h1>
            <div style={styles.grid}>
                <div style={styles.column}>
                    <ControlPanel onStart={onStart} onStop={onStop} isRunning={isRunning} onReset={onReset} config={config} />
                    <TicketStatus status={status} />
                </div>
                <div style={styles.column}>
                    <LogDisplay logs={logs} />
                </div>
            </div>
        </div>
    );
}

const styles = {
    container: {
        padding: '20px'
    },
    title: {
        color: '#333',
        textAlign: 'center'
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
    }
};

export default DashboardPage;
