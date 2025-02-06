let cryptoData = {}; 
let chart;           
let chartInterval = null; 
const pointVisibility = 7 ; 
let secondLoad = 2000 ; 


function loadCryptos() {
    $.ajax({
        url: '/ListCryptoJSON', 
        method: 'GET',
        success: function(data) {
            var tbody = $('#cryptoTable tbody');
            tbody.empty();
            data.forEach(function(crypto) {
                tbody.append(`
                    <tr onclick="loadChart(${crypto.idCrypto}, '${crypto.libelle}')">
                        <td>${crypto.idCrypto}</td>
                        <td>${crypto.libelle}</td>
                        <td>${crypto.prixActuelle}</td>
                        <td>${crypto.dateUpdate}</td>
                        <td><button onclick="loadChart(${crypto.idCrypto}, '${crypto.libelle}')">Voir</button></td>
                    </tr>
                `);
                if (!cryptoData[crypto.idCrypto]) {
                    cryptoData[crypto.idCrypto] = {
                        labels: [],
                        prices: []
                    };
                }
                const lastLabel = cryptoData[crypto.idCrypto].labels.slice(-1)[0];
                if (lastLabel !== crypto.dateUpdate) {
                    cryptoData[crypto.idCrypto].labels.push(crypto.dateUpdate);
                    cryptoData[crypto.idCrypto].prices.push(crypto.prixActuelle);
                }
            });
        },
        error: function() {
            alert('Erreur de récupération des données');
        }
    });
}

function loadChart(idCrypto, libelle) {
    currentCryptoId = idCrypto;
    currentCryptoName = libelle;

    const data = cryptoData[idCrypto];
    if (!data) {
        alert('Pas de données pour ce crypto.');
        return;
    }

    const ctx = document.getElementById('cryptoChart').getContext('2d');

    if (chart) {
        chart.destroy();
    }

    const visibleLabels = data.labels.slice(-pointVisibility);
    const visiblePrices = data.prices.slice(-pointVisibility);

    chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: visibleLabels, 
            datasets: [{
                label: libelle,
                data: visiblePrices, 
                borderColor: 'green',
                backgroundColor: 'rgba(0, 0, 0, 0.1)',
                fill: true,
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true
                }
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Temps'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Prix'
                    }
                }
            }
        }
    });
    startChartUpdate();
}
function startChartUpdate() {
    console.log('Starting chart update');

    
    if (chartInterval) {
        clearInterval(chartInterval);
    }


    chartInterval = setInterval(function() {
        $.ajax({
            url: '/ListCryptoJSON',
            method: 'GET',
            success: function(data) {
                data.forEach(function(crypto) {
                    if (crypto.idCrypto === currentCryptoId) {
                        const dataSet = cryptoData[crypto.idCrypto];

                        // Ajouter la nouvelle donnée
                        dataSet.labels.push(crypto.dateUpdate);
                        dataSet.prices.push(crypto.prixActuelle);

                        // Garder seulement les derniers points visibles
                        const visibleLabels = dataSet.labels.slice(-pointVisibility);
                        const visiblePrices = dataSet.prices.slice(-pointVisibility);

                        // Mettre à jour le graphique
                        chart.data.labels = visibleLabels;
                        chart.data.datasets[0].data = visiblePrices;
                        chart.update();
                    }
                });
            },
            error: function() {
                console.error('Erreur lors de la mise à jour des données.');
            }
        });
    }, secondLoad); 
}

setInterval(loadCryptos, secondLoad);

$(document).ready(function() {
    loadCryptos();
});
