import { Candidato } from './model';
import { getCandidatos, saveCandidatos } from './storageService.js';
import { validarNome, validarEmail, validarCPF, validarCEP, validarCompetencias } from './validations.js';
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

    if (!validarNome(nome)) {
      alert("Nome inválido! (mínimo 3 letras, sem caracteres estranhos)");
      return;
    }
    if (!validarEmail(email)) {
      alert("E-mail inválido!");
      return;
    }
    if (cpf && !validarCPF(cpf)) {
      alert("CPF inválido! Use 11 dígitos (ex.: 12345678901)");
      return;
    }
    if (cep && !validarCEP(cep)) {
      alert("CEP inválido! Use 99999-999 ou 99999999");
      return;
    }
    if (!validarCompetencias(competencias)) {
      alert("Competências inválidas! Use somente letras, números e espaços.");
      return;
    }
    if (idade < 18 || idade > 65) {
      alert("Idade deve estar entre 18 e 65.");
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
