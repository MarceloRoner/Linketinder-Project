package dao

import java.sql.*

class CompetenciaDAO implements ICompetenciaDAO {

    private final Connection conn

    CompetenciaDAO(Connection conn) {
        this.conn = conn
    }

    @Override
    int inserirOuBuscarCompetencia(String nomeComp) {
        int idExistente = buscarIdCompetencia(nomeComp)
        if (idExistente != -1) return idExistente

        return inserirNovaCompetencia(nomeComp)
    }

    private int buscarIdCompetencia(String nomeComp) {
        String sql = "SELECT id FROM competencia WHERE nome = ?"
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setString(1, nomeComp)
        ResultSet rs = stmt.executeQuery()

        if (rs.next()) {
            int id = rs.getInt("id")
            rs.close()
            stmt.close()
            return id
        }

        rs.close()
        stmt.close()
        return -1
    }

    private int inserirNovaCompetencia(String nomeComp) {
        String sql = "INSERT INTO competencia (nome) VALUES (?) RETURNING id"
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setString(1, nomeComp)
        ResultSet rs = stmt.executeQuery()
        rs.next()
        int novoId = rs.getInt("id")
        rs.close()
        stmt.close()
        return novoId
    }

    @Override
    List<String> listarTodasCompetencias() {
        List<String> competencias = []
        String sql = "SELECT nome FROM competencia ORDER BY nome"
        PreparedStatement stmt = conn.prepareStatement(sql)
        ResultSet rs = stmt.executeQuery()
        while (rs.next()) {
            competencias << rs.getString("nome")
        }
        rs.close()
        stmt.close()
        return competencias
    }

    @Override
    void excluirCompetencia(String nome) {
        String sql = "DELETE FROM competencia WHERE nome = ?"
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setString(1, nome)
        stmt.executeUpdate()
        stmt.close()
    }

    @Override
    void atualizarCompetencia(String nomeAntigo, String nomeNovo) {
        String sql = "UPDATE competencia SET nome = ? WHERE nome = ?"
        PreparedStatement stmt = conn.prepareStatement(sql)
        stmt.setString(1, nomeNovo)
        stmt.setString(2, nomeAntigo)
        stmt.executeUpdate()
        stmt.close()
    }
}
