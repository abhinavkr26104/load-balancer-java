let trafficChart = null;

function updateChart(servers) {
    const ctx = document.getElementById('trafficChart').getContext('2d');
    
    const labels = servers.map(s => s.id);
    const data = servers.map(s => s.requestCount);
    
    if (trafficChart) {
        trafficChart.data.labels = labels;
        trafficChart.data.datasets[0].data = data;
        trafficChart.update();
    } else {
        trafficChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Requests Handled',
                    data: data,
                    backgroundColor: [
                        'rgba(0, 0, 0, 0.8)',
                        'rgba(100, 100, 100, 0.8)',
                        'rgba(150, 150, 150, 0.8)'
                    ],
                    borderColor: [
                        'rgba(0, 0, 0, 1)',
                        'rgba(100, 100, 100, 1)',
                        'rgba(150, 150, 150, 1)'
                    ],
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        labels: {
                            color: '#000000',
                            font: { size: 14 }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            color: '#000000',
                            stepSize: 1
                        },
                        grid: {
                            color: 'rgba(0, 0, 0, 0.1)'
                        }
                    },
                    x: {
                        ticks: {
                            color: '#000000'
                        },
                        grid: {
                            color: 'rgba(0, 0, 0, 0.1)'
                        }
                    }
                }
            }
        });
    }
}
