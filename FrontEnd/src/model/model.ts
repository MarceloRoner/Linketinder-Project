export interface Candidato {
  id: number;
  nome: string;
  sobrenome: string;
  email: string;
  cpf: string;
  idade: number;
  pais: string;
  estado: string;
  cep: string;
  descricao: string;
  dataNascimento: string;
  competencias: string[];
  senha: string;
}

export interface Empresa {
  id: number;
  nome: string;
  email: string;
  cnpj: string;
  pais: string;
  estado: string;
  cep: string;
  descricao: string;
  competenciasEsperadas: string[];
  senha: string;
}

export interface Vaga {
  id: number;
  titulo: string;
  empresa: Empresa;
  local: string;
  competenciasRequeridas: string[];
}
