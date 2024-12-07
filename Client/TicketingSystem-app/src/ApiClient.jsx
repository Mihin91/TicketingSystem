// src/ApiClient.jsx
class ApiClient {
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

    static async getAllConfigurations() {
        const response = await fetch("http://localhost:8080/api/configurations/all");
        if (!response.ok) {
            throw new Error("Failed to fetch configurations");
        }
        return response.json();
    }

    static async deleteConfiguration(id) {
        const response = await fetch(`http://localhost:8080/api/configurations/delete/${id}`, {
            method: "DELETE"
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Failed to delete configuration");
        }
    }

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
}

export default ApiClient;
