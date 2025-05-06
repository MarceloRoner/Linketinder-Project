import { api } from "../service/api.js";
import {
  emailValido,
  cnpjValido,
  campoPreenchido,
  cepValido,
  listaNaoVazia,
} from "../service/validations";
import type {Empresa} from "../model/model";

export function conectarFormEmpresa(form: HTMLFormElement) {
  form.addEventListener("submit", (event) => {
    event.preventDefault();

    const d = Object.fromEntries(
        Array.from(new FormData(form).entries())
    ) as Record<string, string>;

    /* ---------- validação ---------- */
    const ok =
        campoPreenchido(d.nome) &&
        emailValido(d.email) &&
        cnpjValido(d.cnpj) &&
        cepValido(d.cep) &&
        listaNaoVazia((d.competencias ?? "").split(","));

    if (!ok) {
      alert("Dados inválidos – verifique o formulário.");
      return;
    }

    /* ---------- montagem DTO ---------- */
    const emp: Empresa = {
      id: 0,
      nome: d.nome,
      email: d.email,
      cnpj: d.cnpj,
      pais: d.pais || "Brasil",
      estado: d.estado,
      cep: d.cep,
      descricao: d.descricao,
      senha: d.senha,
      competenciasEsperadas: (d.competencias ?? "")
          .split(",")
          .map((s) => s.trim())
          .filter(Boolean),
    } as Empresa;

    /* ---------- persistência ---------- */
    api.criar<Empresa>("empresas", emp);
    alert("Empresa cadastrada!");
    form.reset();
  });
}
