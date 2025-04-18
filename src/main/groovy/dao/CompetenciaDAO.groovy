package dao

import utils.DatabaseUtils

import java.sql.*

class CompetenciaDAO {

    static int inserirOuBuscarCompetencia(Connection conn, String nomeComp) {
        int idExistente = buscarIdCompetencia(conn, nomeComp)
        if (idExistente != -1) return idExistente

        return inserirNovaCompetencia(conn, nomeComp)
    }

    private static int buscarIdCompetencia(Connection conn, String nomeComp) {
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

    private static int inserirNovaCompetencia(Connection conn, String nomeComp) {
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

    static List<String> listarTodasCompetencias() {
        List<String> competencias = []
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = "SELECT nome FROM competencia ORDER BY nome"
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()
            while (rs.next()) {
                competencias << rs.getString("nome")
            }
            rs.close()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
        return competencias
    }

    static void excluirCompetencia(String nome) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = "DELETE FROM competencia WHERE nome = ?"
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nome)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }

    static void atualizarCompetencia(String nomeAntigo, String nomeNovo) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = "UPDATE competencia SET nome = ? WHERE nome = ?"
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, nomeNovo)
            stmt.setString(2, nomeAntigo)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }
}
