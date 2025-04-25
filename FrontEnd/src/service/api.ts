import { storageService } from "./storageService";

const keys = {
    candidatos: "candidatos",
    empresas: "empresas",
    vagas: "vagas",
};

type Entidade = "candidatos" | "empresas" | "vagas";

function lerLista<T>(ent: Entidade): T[] {
    return storageService.load<T[]>(keys[ent]) ?? [];
}

function salvarLista<T>(ent: Entidade, lista: T[]): void {
    storageService.save(keys[ent], lista);
}

export const api = {
    listar<T>(ent: Entidade): T[] {
        return lerLista<T>(ent);
    },

    criar<T extends { id?: number }>(ent: Entidade, item: T): void {
        const lista = lerLista<T>(ent);
        item.id = Date.now(); // id simples
        lista.push(item);
        salvarLista(ent, lista);
    },

    atualizar<T extends { id: number }>(ent: Entidade, item: T): void {
        const lista = lerLista<T>(ent);
        const idx = lista.findIndex((e) => e.id === item.id);
        if (idx >= 0) {
            lista[idx] = item;
            salvarLista(ent, lista);
        }
    },

    excluir(ent: Entidade, id: number): void {
        const lista = lerLista<any>(ent).filter((e) => e.id !== id);
        salvarLista(ent, lista);
    },
};
