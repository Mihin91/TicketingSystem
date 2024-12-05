// src/components/TicketStatus.jsx
import React from 'react';

function TicketStatus({ status }) {
    return (
        <div style={styles.container}>
            <h2>Ticket Status</h2>
            <p><strong>Current Tickets:</strong> {status.currentTickets}</p>
            <p><strong>Total Tickets Released:</strong> {status.totalTicketsReleased}</p>
            <p><strong>Total Tickets Purchased:</strong> {status.totalTicketsPurchased}</p>
        </div>
    );
}

const styles = {
    container: {
        border: '1px solid #17a2b8',
        padding: '15px',
        borderRadius: '5px',
        backgroundColor: '#e9f7fd',
        marginBottom: '20px'
    }
};

export default TicketStatus;
