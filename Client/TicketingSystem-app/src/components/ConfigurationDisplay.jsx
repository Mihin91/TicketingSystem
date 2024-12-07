// src/components/ConfigurationDisplay.jsx
import React from 'react';

function ConfigurationDisplay({ configs, onDeleteConfig }) {
    if (!configs || configs.length === 0) {
        return (
            <div style={styles.container}>
                <h2 style={styles.title}>Saved Configurations</h2>
                <p style={styles.noConfigText}>No configurations saved yet.</p>
            </div>
        );
    }

    return (
        <div style={styles.container}>
            <h2 style={styles.title}>Saved Configurations</h2>
            <div style={styles.tableWrapper}>
                <table style={styles.table}>
                    <thead>
                    <tr>
                        <th style={{ ...styles.headerCell, textAlign: 'left' }}>ID</th>
                        <th style={styles.headerCell}>Customer retrieval rate(ms)</th>
                        <th style={styles.headerCell}>Max ticket capacity</th>
                        <th style={styles.headerCell}>No. of Customers</th>
                        <th style={styles.headerCell}>No. of Vendors</th>
                        <th style={styles.headerCell}>Ticket Release Rate(ms)</th>
                        <th style={styles.headerCell}>Total tickets</th>
                        <th style={styles.headerCell}>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {configs.map((config) => (
                        <tr key={config.id} style={styles.row}>
                            <td style={{ ...styles.cell, textAlign: 'left' }}>{config.id}</td>
                            <td style={styles.numericCell}>{config.customerRetrievalRate}</td>
                            <td style={styles.numericCell}>{config.maxTicketCapacity}</td>
                            <td style={styles.numericCell}>{config.numberOfCustomers}</td>
                            <td style={styles.numericCell}>{config.numberOfVendors}</td>
                            <td style={styles.numericCell}>{config.ticketReleaseRate}</td>
                            <td style={styles.numericCell}>{config.totalTickets}</td>
                            <td style={styles.cell}>
                                <button style={styles.deleteButton} onClick={() => onDeleteConfig(config.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

const styles = {
    container: {
        border: '1px solid #007bff',
        padding: '10px',
        borderRadius: '13px',
        backgroundColor: '#e7f1ff',
        marginTop: '20px',
        fontSize: '14px',
        overflowX: 'auto'
    },
    title: {
        margin: '0 0 10px 0',
        fontSize: '16px',
        fontWeight: 'bold',
        textAlign: 'left',
    },
    noConfigText: {
        margin: '5px 0',
        fontSize: '14px'
    },
    tableWrapper: {
        width: '100%',
        overflowX: 'auto'
    },
    table: {
        width: '100%',
        borderCollapse: 'collapse'
    },
    headerCell: {
        borderBottom: '1px solid #ccc',
        fontWeight: 'bold',
        padding: '5px',
        backgroundColor: '#f0f8ff',
        fontSize: '13px',
        textAlign: 'right' // Default right align for numeric headers
    },
    row: {
        borderBottom: '1px solid #ccc'
    },
    cell: {
        padding: '5px',
        fontSize: '13px',
        textAlign: 'right' // Numeric columns will be right aligned by default
    },
    numericCell: {
        padding: '5px',
        fontSize: '13px',
        textAlign: 'right'
    },
    deleteButton: {
        backgroundColor: '#dc3545',
        color: '#fff',
        border: 'none',
        borderRadius: '3px',
        padding: '3px 8px',
        cursor: 'pointer',
        fontSize: '12px',
        textAlign: 'center' // Center align the text in the button
    }
};

export default ConfigurationDisplay;
