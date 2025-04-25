package dao

import model.Candidato

interface ICandidatoDAO {
    List<Candidato> listarCandidatos()
    void inserirCandidato(Candidato c)
    void atualizarCandidato(Candidato c)
    void excluirCandidato(int id)
}
