package dao

import model.Vaga

interface IVagaDAO {
    List<Vaga> listarVagas()
    void inserirVaga(Vaga vaga)
    void atualizarVaga(Vaga vaga)
    void excluirVaga(int id)
}
