export function validarNome(nome:string) : boolean {

  const regex = /^(?!.*[\s'-]{2})[A-Za-zÀ-ÖØ-öø-ÿ]([A-Za-zÀ-ÖØ-öø-ÿ\s'-]*[A-Za-zÀ-ÖØ-öø-ÿ])?$/;
  return regex.test(nome.trim());

}

export function validarEmail(email:string) : boolean {
     
  const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return regex.test(email.trim());

}

export function validarCPF(cpf: string): boolean {
    if (!cpf) return true;  
    const regex = /^(\d{3}\.){2}\d{3}-\d{2}|\d{11}$/;
    return regex.test(cpf.trim());
  }
    
  export function validarCNPJ(cnpj: string): boolean {
    if (!cnpj) return true;
    const regex = /^\d{2}(\.\d{3}){2}\/\d{4}-\d{2}|\d{14}$/;
    return regex.test(cnpj.trim());
  }

  export function validarCEP(cep: string): boolean {
    if (!cep) return true;
    const regex = /^\d{5}-?\d{3}$/;
    return regex.test(cep.trim().replace(/\s/g, ''));
  }

  export function validarCompetencias(comps: string[]): boolean {
    const regex = /^[A-Za-z0-9À-ÖØ-öø-ÿ\s]+$/;
  
    if (!comps || comps.length === 0) return false;
  
    return comps.every((item) => {
        const trimmedItem = item.trim();
        return trimmedItem.length > 0 && regex.test(trimmedItem);
    });
}


