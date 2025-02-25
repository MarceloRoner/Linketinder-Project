import { getCandidatos } from './storageService.js';
import { Candidato } from './model';


declare var Chart: any;

function contarCompetencias(candidatos: Candidato[]): Record<string, number> {
  const mapa: Record<string, number> = {};
  for (const cand of candidatos) {
    for (const skill of cand.competencias) {
      if (!mapa[skill]) {
        mapa[skill] = 0;
      }
      mapa[skill]++;
    }
  }
  return mapa;
}

window.addEventListener('DOMContentLoaded', () => {
  const candidatos = getCandidatos();
  console.log("Candidatos:", candidatos);

  const tbody = document.querySelector('#tabela-candidatos tbody') as HTMLTableSectionElement;

  candidatos.forEach(c => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>Candidato #${c.id}</td>
      <td>${c.competencias.join(', ')}</td>
    `;
    tbody.appendChild(tr);
  });

  const mapa = contarCompetencias(candidatos);
  const labels = Object.keys(mapa);
  const valores = Object.values(mapa);

  const ctx = document.getElementById('grafico') as HTMLCanvasElement;

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Candidatos por Competência',
        data: valores,
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: { beginAtZero: true }
      },
      plugins: {
        tooltip: {
          callbacks: {
            label: (context: any) => {
              const skill = context.label;
              const count = context.parsed.y;
              return `${skill}: ${count} candidatos`;
            }
          }
        }
      }
    }
  });
});
