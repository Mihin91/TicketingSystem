// src/ApiClient.jsx
class ApiClient {
    /**
     * Saves the configuration to the backend.
     *
     * @param {Object} config - Configuration data.
     * @returns {Promise<Object>} - The saved configuration.
     */
    static async saveConfiguration(config) {
        try {
            const response = await fetch("http://localhost:8080/api/configurations/save", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(config),
            });
            if (!response.ok) {
                const errorText = await response.json();
                console.error("Failed to save configuration:", errorText.message);
                throw new Error(errorText.message || "Failed to save configuration");
            }
            return response.json();
        } catch (error) {
            console.error("Error in saveConfiguration:", error);
            throw error;
        }
    }

    /**
     * Starts the simulation by calling the backend endpoint.
     *
     * @returns {Promise<Object>} - The response from the backend.
     */
    static async startSystem() {
        try {
            const response = await fetch("http://localhost:8080/api/system/start", { method: "POST" });
            if (!response.ok) {
                const errorText = await response.json();
                console.error("Failed to start system:", errorText.message);
                throw new Error(errorText.message || "Failed to start system");
            }
            return response.json();
        } catch (error) {
            console.error("Error in startSystem:", error);
            throw error;
        }
    }

    /**
     * Stops the simulation by calling the backend endpoint.
     *
     * @returns {Promise<Object>} - The response from the backend.
     */
    static async stopSystem() {
        try {
            const response = await fetch("http://localhost:8080/api/system/stop", { method: "POST" });
            if (!response.ok) {
                const errorText = await response.json();
                console.error("Failed to stop system:", errorText.message);
                throw new Error(errorText.message || "Failed to stop system");
            }
            return response.json();
        } catch (error) {
            console.error("Error in stopSystem:", error);
            throw error;
        }
    }

    // ... other methods if any
}

export default ApiClient;
