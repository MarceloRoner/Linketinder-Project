import { api } from "../service/api.js";
import type {Empresa, Vaga} from "../model/model.js";

window.addEventListener("DOMContentLoaded", () => {
  const empresas = api.listar<Empresa>("empresas");
  const vagas    = api.listar<Vaga>("vagas");

  // exemplo: listar empresas com qtde de vagas
  const ul = document.querySelector("#lista-empresas") as HTMLUListElement;

  empresas.forEach((emp) => {
    const totalVagas = vagas.filter((v) => v.empresa?.id === emp.id).length;
    const li = document.createElement("li");
    li.textContent = `${emp.nome} — ${totalVagas} vagas`;
    ul.appendChild(li);
  });
});
