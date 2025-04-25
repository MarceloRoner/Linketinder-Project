import { api } from "../service/api";
import { Vaga, Empresa } from "../model/model";

window.addEventListener("DOMContentLoaded", () => {
  const vagas     = api.listar<Vaga>("vagas");
  const empresas  = api.listar<Empresa>("empresas");

  const tbody = document.querySelector("#tabela-vagas tbody") as HTMLTableSectionElement;

  vagas.forEach((vaga) => {
    const emp = empresas.find((e) => e.id === vaga.empresa?.id);
    const nomeEmpresa = emp ? `Empresa ${emp.nome}` : "Empresa Desconhecida";

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${vaga.titulo}</td>
      <td>${nomeEmpresa}</td>
      <td>${vaga.competenciasRequeridas.join(", ")}</td>
    `;
    tbody.appendChild(tr);
  });
});
