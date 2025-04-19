package dao

interface ICompetenciaDAO {
    int inserirOuBuscarCompetencia(String nomeComp)
    List<String> listarTodasCompetencias()
    void excluirCompetencia(String nome)
    void atualizarCompetencia(String nomeAntigo, String nomeNovo)
}
