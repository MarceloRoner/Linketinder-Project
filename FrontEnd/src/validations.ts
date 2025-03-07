export function validarNome(nome:string) : boolean {

        const regex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{3,}$/;
        return regex.test(nome.trim());

}

export function validarEmail(email:string) : boolean {
     
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email.trim());

}

export function validarCPF(cpf: string): boolean {
    if (!cpf) return true;  
    const regex = /^\d{11}$/;
    return regex.test(cpf.trim());
  }
    
  export function validarCNPJ(cnpj: string): boolean {
    if (!cnpj) return true; // Idem
    const regex = /^\d{14}$/;
    return regex.test(cnpj.trim());
  }

  export function validarCEP(cep: string): boolean {
    if (!cep) return true;
    const regex = /^\d{5}-?\d{3}$/;
    return regex.test(cep.trim());
  }

  export function validarCompetencias(comps: string[]): boolean {
    const regex = /^[A-Za-z0-9À-ÖØ-öø-ÿ\s]+$/;
  
    if (!comps || comps.length === 0) {
      return true; 
    }
  
    return comps.every((item) => regex.test(item.trim()));
  }

