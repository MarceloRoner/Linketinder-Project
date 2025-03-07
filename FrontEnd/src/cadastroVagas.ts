import { Vaga } from './model';
import { getVagas, saveVagas } from './storageService.js';
import {
  validarCompetencias
} from './validations';

window.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('form-vaga') as HTMLFormElement;

  form.addEventListener('submit', (event) => {
    event.preventDefault();

    const titulo = (document.getElementById('titulo') as HTMLInputElement).value.trim();
    const empresaIdStr = (document.getElementById('empresaId') as HTMLInputElement).value.trim();
    const compsStr = (document.getElementById('competencias') as HTMLInputElement).value.trim();

    if (!titulo) {
      alert("Título da vaga é obrigatório!");
      return;
    }
    if (!empresaIdStr) {
      alert("Informe o ID da empresa!");
      return;
    }

    const empresaId = parseInt(empresaIdStr, 10);
    const competencias = compsStr 
      ? compsStr.split(',').map(c => c.trim()) 
      : [];

      if (!validarCompetencias(competencias)) {
        alert("As competências têm caracteres inválidos!");
        return;
      }

    // Lê as vagas existentes
    const vagas = getVagas();
    // Gera ID
    const newId = vagas.length > 0 
      ? vagas[vagas.length - 1].id + 1 
      : 1;

    const novaVaga: Vaga = {
      id: newId,
      titulo,
      empresaId,
      competenciasRequeridas: competencias,
    };

    vagas.push(novaVaga);
    saveVagas(vagas);

    alert(`Vaga "${titulo}" cadastrada com sucesso!`);
    form.reset();
  });
});
