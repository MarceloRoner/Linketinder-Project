import { api } from "../service/api.js";
import { listaNaoVazia, campoPreenchido } from "../service/validations.js";
import type {Empresa, Vaga} from "../model/model.js";

export function popularSelectEmpresas(select: HTMLSelectElement) {
  const empresas = api.listar<Empresa>("empresas");
  select.innerHTML = empresas
      .map((e, i) => `<option value="${i}">${e.nome}</option>`)
      .join("");
  return empresas;
}

export function conectarFormVaga(form: HTMLFormElement) {
  const selEmp = form.querySelector("select[name=empresa]") as HTMLSelectElement;
  let empresas = popularSelectEmpresas(selEmp);

  form.addEventListener("submit", (ev) => {
    ev.preventDefault();

    const d = Object.fromEntries(
        Array.from(new FormData(form).entries())
    ) as Record<string, string>;

    const competencias = (d.competencias ?? "")
        .split(",")
        .map((s) => s.trim())
        .filter(Boolean);

    const ok =
        campoPreenchido(d.titulo) &&
        listaNaoVazia(empresas) &&
        listaNaoVazia(competencias);

    if (!ok) {
      alert("Preencha título, empresa e competências.");
      return;
    }

    const emp = empresas[Number(d.empresa)];
    const vaga: Vaga = {
      id: 0,
      titulo: d.titulo,
      empresa: emp,                 // ← agora objeto
      local: d.local,
      competenciasRequeridas: competencias,
    };

    api.criar<Vaga>("vagas", vaga);
    alert("Vaga cadastrada!");
    form.reset();
  });
}
