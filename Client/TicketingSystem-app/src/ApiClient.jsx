class ApiClient {
    static async startSystem() {
      try {
        const response = await fetch("http://localhost:8080/api/system/start", { method: "POST" });
        if (!response.ok) {
          throw new Error("Failed to start system");
        }
        return response.json();
      } catch (error) {
        throw error;
      }
    }
  
    static async stopSystem() {
      try {
        const response = await fetch("http://localhost:8080/api/system/stop", { method: "POST" });
        if (!response.ok) {
          throw new Error("Failed to stop system");
        }
        return response.json();
      } catch (error) {
        throw error;
      }
    }
  
    static async updateTicket(ticket) {
      try {
        const response = await fetch("http://localhost:8080/api/system/updateTicket", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(ticket),
        });
        if (!response.ok) {
          throw new Error("Failed to update ticket");
        }
        return response.json();
      } catch (error) {
        throw error;
      }
    }
  }
  
  export default ApiClient;
  