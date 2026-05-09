const API_BASE = 'http://localhost:8080';
const REFRESH_INTERVAL = 2000;

async function fetchServers() {
    try {
        const response = await fetch(`${API_BASE}/servers`);
        const servers = await response.json();
        renderServers(servers);
        updateChart(servers);
    } catch (error) {
        console.error('Error fetching servers:', error);
    }
}

async function fetchStats() {
    try {
        const response = await fetch(`${API_BASE}/stats`);
        const stats = await response.json();
        document.getElementById('totalRequests').textContent = stats.totalRequests;
        document.getElementById('activeConnections').textContent = stats.activeConnections;
    } catch (error) {
        console.error('Error fetching stats:', error);
    }
}

async function fetchLogs() {
    try {
        const response = await fetch(`${API_BASE}/logs`);
        const logs = await response.json();
        renderLogs(logs);
    } catch (error) {
        console.error('Error fetching logs:', error);
    }
}

function renderServers(servers) {
    const grid = document.getElementById('serversGrid');
    grid.innerHTML = servers.map(server => `
        <div class="server-card">
            <div class="server-header">
                <span class="server-name">${server.id}</span>
                <span class="status-indicator ${server.active ? 'status-active' : 'status-inactive'}"></span>
            </div>
            <div class="server-info">
                <div class="info-row">
                    <span class="info-label">Status:</span>
                    <span class="info-value">${server.active ? 'ACTIVE' : 'INACTIVE'}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Port:</span>
                    <span class="info-value">${server.port}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Requests:</span>
                    <span class="info-value">${server.requestCount}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Avg Latency:</span>
                    <span class="info-value">${server.avgLatency}ms</span>
                </div>
            </div>
        </div>
    `).join('');
}

function renderLogs(logs) {
    const container = document.getElementById('logsContainer');
    if (logs.length === 0) {
        container.innerHTML = '<p class="no-logs">Waiting for requests...</p>';
        return;
    }
    
    container.innerHTML = logs.map(log => `
        <div class="log-entry ${log.success ? 'success' : 'failed'}">
            <span class="log-route">${log.clientId} → ${log.serverId}</span>
            <div class="log-meta">
                <span>${log.latencyMs}ms</span>
                <span>${log.timestamp}</span>
            </div>
        </div>
    `).join('');
}

function refreshData() {
    fetchServers();
    fetchStats();
    fetchLogs();
}

refreshData();
setInterval(refreshData, REFRESH_INTERVAL);
