import { Candidato, Empresa, Vaga } from './model';

export function getCandidatos(): Candidato[] {
  const stored = localStorage.getItem('candidatos');
  return stored ? JSON.parse(stored) : [];
}

export function saveCandidatos(candidatos: Candidato[]): void {
  localStorage.setItem('candidatos', JSON.stringify(candidatos));
}

export function deleteCandidato(id: number): void {
  let arr = getCandidatos();
  arr = arr.filter(c => c.id !== id);
  saveCandidatos(arr);
}

// EMPRESA
export function getEmpresas(): Empresa[] {
  const stored = localStorage.getItem('empresas');
  return stored ? JSON.parse(stored) : [];
}

export function saveEmpresas(empresas: Empresa[]): void {
  localStorage.setItem('empresas', JSON.stringify(empresas));
}

export function deleteEmpresa(id: number): void {
  let arr = getEmpresas();
  arr = arr.filter(e => e.id !== id);
  saveEmpresas(arr);
}

// VAGA
export function getVagas(): Vaga[] {
  const stored = localStorage.getItem('vagas');
  return stored ? JSON.parse(stored) : [];
}

export function saveVagas(vagas: Vaga[]): void {
  localStorage.setItem('vagas', JSON.stringify(vagas));
}

export function deleteVaga(id: number): void {
  let arr = getVagas();
  arr = arr.filter(v => v.id !== id);
  saveVagas(arr);
}
