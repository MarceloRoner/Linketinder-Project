export interface Candidato {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  idade: number;
  estado: string;
  cep: string;
  descricao: string;
  competencias: string[];
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
}

export interface Vaga {
  id: number;
  titulo: string;
  empresaId: number;
  competenciasRequeridas: string[];
}
