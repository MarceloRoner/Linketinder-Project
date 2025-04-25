const REG_EMAIL = /^[\w.+-]+@[\w-]+\.[\w.-]{2,}$/i;
const REG_CPF   = /^\d{11}$/;                     // somente dígitos
const REG_CNPJ  = /^\d{14}$/;                     // somente dígitos
const REG_CEP   = /^\d{5}-?\d{3}$/;

export function emailValido(e: string): boolean   { return REG_EMAIL.test(e); }
export function cpfValido(cpf: string): boolean   { return REG_CPF.test(cpf); }
export function cnpjValido(cnpj: string): boolean { return REG_CNPJ.test(cnpj); }
export function cepValido(cep: string): boolean   { return REG_CEP.test(cep); }

export function campoPreenchido(v?: string | null): boolean {
    return (v ?? "").trim().length > 0;
}

export function listaNaoVazia(arr: unknown[] | undefined | null): boolean {
    return Array.isArray(arr) && arr.length > 0;
}

export function formCandidatoOk(c: {
    email: string; cpf: string; nome: string; sobrenome: string;
}): boolean {
    return (
        emailValido(c.email) &&
        cpfValido(c.cpf)     &&
        campoPreenchido(c.nome) &&
        campoPreenchido(c.sobrenome)
    );
}
