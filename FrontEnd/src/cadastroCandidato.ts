import { Candidato } from './model';
import { getCandidatos, saveCandidatos } from './storageService.js';

window.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('form-candidato') as HTMLFormElement;

  form.addEventListener('submit', (event) => {
    event.preventDefault();

    const nome = (document.getElementById('nome') as HTMLInputElement).value.trim();
    const email = (document.getElementById('email') as HTMLInputElement).value.trim();
    const cpf = (document.getElementById('cpf') as HTMLInputElement).value.trim();
    const idadeStr = (document.getElementById('idade') as HTMLInputElement).value.trim();
    const estado = (document.getElementById('estado') as HTMLInputElement).value.trim();
    const cep = (document.getElementById('cep') as HTMLInputElement).value.trim();
    const descricao = (document.getElementById('descricao') as HTMLTextAreaElement).value.trim();
    const competenciasStr = (document.getElementById('competencias') as HTMLInputElement).value.trim();

    const idade = idadeStr ? parseInt(idadeStr, 10) : 0;
    const competencias = competenciasStr ? competenciasStr.split(',').map(c => c.trim()) : [];

    // Validar básicos
    if (!nome) {
      alert("Nome é obrigatório!");
      return;
    }
    if (!email) {
      alert("Email é obrigatório!");
      return;
    }

    const candidatos = getCandidatos();
    const newId = candidatos.length > 0
      ? candidatos[candidatos.length - 1].id + 1
      : 1;

    const novo: Candidato = {
      id: newId,
      nome,
      email,
      cpf,
      idade,
      estado,
      cep,
      descricao,
      competencias
    };

    candidatos.push(novo);
    saveCandidatos(candidatos);

    alert(`Candidato "${nome}" cadastrado com sucesso!`);
    form.reset();
  });
});
