import { Empresa } from './model';
import { getEmpresas, saveEmpresas } from './storageService.js';

window.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('form-empresa') as HTMLFormElement;

  form.addEventListener('submit', (event) => {
    event.preventDefault();

    const nome = (document.getElementById('nome') as HTMLInputElement).value.trim();
    const email = (document.getElementById('email') as HTMLInputElement).value.trim();
    const cnpj = (document.getElementById('cnpj') as HTMLInputElement).value.trim();
    const pais = (document.getElementById('pais') as HTMLInputElement).value.trim();
    const estado = (document.getElementById('estado') as HTMLInputElement).value.trim();
    const cep = (document.getElementById('cep') as HTMLInputElement).value.trim();
    const descricao = (document.getElementById('descricao') as HTMLTextAreaElement).value.trim();
    const compsStr = (document.getElementById('competenciasEsperadas') as HTMLInputElement).value.trim();

    const competenciasEsperadas = compsStr
      ? compsStr.split(',').map(c => c.trim())
      : [];

    if (!nome) {
      alert("Nome da empresa é obrigatório!");
      return;
    }
    if (!email) {
      alert("E-mail é obrigatório!");
      return;
    }

    const empresas = getEmpresas();
    const newId = empresas.length > 0
      ? empresas[empresas.length - 1].id + 1
      : 1;

    const novaEmpresa: Empresa = {
      id: newId,
      nome,
      email,
      cnpj,
      pais,
      estado,
      cep,
      descricao,
      competenciasEsperadas
    };

    empresas.push(novaEmpresa);
    saveEmpresas(empresas);

    alert(`Empresa "${nome}" cadastrada com sucesso!`);
    form.reset();
  });
});
