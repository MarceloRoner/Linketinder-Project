import { api } from "../service/api";
import { formCandidatoOk, cpfValido, emailValido } from "../service/validations";
import { Candidato } from "../model/model";

export function conectarFormCandidato(form: HTMLFormElement) {
  form.addEventListener("submit", (ev) => {
    ev.preventDefault();

    const d = Object.fromEntries(
        Array.from(new FormData(form).entries())
    ) as Record<string, string>;

    const cand: Candidato = {
      id: 0,
      nome: d.nome,
      sobrenome: d.sobrenome,
      email: d.email,
      cpf: d.cpf,
      idade: Number(d.idade),
      pais: "Brasil",
      estado: d.estado,
      cep: d.cep,
      descricao: d.descricao,
      competencias: (d.competencias ?? "")
          .split(",")
          .map((s) => s.trim())
          .filter(Boolean),
      senha: d.senha,
      dataNascimento: d.dataNascimento,
    };

    if (!formCandidatoOk(cand) || !cpfValido(cand.cpf) || !emailValido(cand.email)) {
      alert("Dados inválidos – verifique o formulário.");
      return;
    }

    api.criar<Candidato>("candidatos", cand);
    alert("Candidato cadastrado!");
    form.reset();
  });
}
