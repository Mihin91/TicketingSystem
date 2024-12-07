// src/pages/ConfigPage.jsx
import React, { useEffect, useState } from 'react';
import ConfigurationForm from '../components/ConfigurationForm';
import ConfigurationDisplay from '../components/ConfigurationDisplay';
import ApiClient from '../ApiClient';

function ConfigPage({ onSave, config }) {
    const [configs, setConfigs] = useState([]);

    const fetchAllConfigurations = async () => {
        try {
            const data = await ApiClient.getAllConfigurations();
            setConfigs(data);
        } catch (error) {
            console.error("Error fetching configurations:", error);
        }
    };

    useEffect(() => {
        // Load all configurations when page mounts
        fetchAllConfigurations();
    }, []);

    const handleSave = async (configData) => {
        await onSave(configData);
        // After saving, refresh the list
        fetchAllConfigurations();
    };

    const handleDeleteConfig = async (id) => {
        try {
            await ApiClient.deleteConfiguration(id);
            // Remove from state or re-fetch
            setConfigs((prev) => prev.filter((cfg) => cfg.id !== id));
        } catch (error) {
            console.error("Error deleting configuration:", error);
        }
    };

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Configuration</h1>
            <ConfigurationForm onSave={handleSave} />
            <ConfigurationDisplay configs={configs} onDeleteConfig={handleDeleteConfig} />
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
    }
};

export default ConfigPage;
