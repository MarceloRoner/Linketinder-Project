import { getVagas, getEmpresas } from './storageService.js';

window.addEventListener('DOMContentLoaded', () => {
  const vagas = getVagas();
  const empresas = getEmpresas();

  console.log("Vagas:", vagas);
  console.log("Empresas:", empresas);

  const tbody = document.querySelector('#tabela-vagas tbody') as HTMLTableSectionElement;

  vagas.forEach((vaga) => {
    const emp = empresas.find(e => e.id === vaga.empresaId);

    let nomeAnonimo = 'Empresa Desconhecida';
    if (emp) {
      nomeAnonimo = `Empresa #${emp.id}`;
    }

    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${vaga.titulo}</td>
      <td>${nomeAnonimo}</td>
      <td>${vaga.competenciasRequeridas.join(', ')}</td>
    `;
    tbody.appendChild(tr);
  });
});
