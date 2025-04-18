package dao


import domain.Empresa
import domain.Vaga
import utils.DatabaseUtils

import java.sql.*

class VagaDAO {

    static List<Vaga> listarVagas() {
        List<Vaga> lista = []
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                SELECT v.id, v.nome AS titulo, v.descricao, v.local,
                    e.id AS empresa_id, e.nome AS empresa_nome,
                    e.email, e.cnpj, e.pais, e.estado, e.cep, e.descricao AS empresa_descricao,
                    STRING_AGG(comp.nome, ', ') AS competencias
                FROM vaga v
                JOIN empresa e ON v.id_empresa = e.id
                LEFT JOIN vaga_competencia vc ON v.id = vc.id_vaga
                LEFT JOIN competencia comp ON vc.id_competencia = comp.id
                GROUP BY v.id, e.id
                ORDER BY v.id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                lista << construirVaga(rs)
            }

            rs.close()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
        return lista
    }

    static void inserirVaga(Vaga vaga) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                INSERT INTO vaga (nome, descricao, local, id_empresa)
                VALUES (?, ?, ?, ?)
                RETURNING id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, vaga.titulo)
            stmt.setString(2, "")
            stmt.setString(3, vaga.local)
            stmt.setInt(4, vaga.empresa.id)

            ResultSet rs = stmt.executeQuery()
            rs.next()
            int vagaId = rs.getInt("id")
            rs.close()
            stmt.close()

            relacionarCompetencias(conn, vagaId, vaga.competenciasRequeridas)

        } finally {
            if (conn != null) conn.close()
        }
    }

    static void atualizarVaga(Vaga vaga) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                UPDATE vaga 
                SET nome = ?, local = ?, id_empresa = ?
                WHERE id = ?
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, vaga.titulo)
            stmt.setString(2, vaga.local)
            stmt.setInt(3, vaga.empresa.id)
            stmt.setInt(4, vaga.id)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }

    static void excluirVaga(int id) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()

            String[] sqls = [
                    "DELETE FROM vaga_competencia WHERE id_vaga = ?",
                    "DELETE FROM curtida WHERE id_vaga = ?",
                    "DELETE FROM vaga WHERE id = ?"
            ]
            sqls.each { query ->
                PreparedStatement stmt = conn.prepareStatement(query)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                stmt.close()
            }

        } finally {
            if (conn != null) conn.close()
        }
    }

    private static Vaga construirVaga(ResultSet rs) {
        String comps = rs.getString("competencias")
        List<String> competencias = comps ? comps.split(", ").toList() : []

        Empresa empresa = new Empresa(
                rs.getString("empresa_nome"),
                rs.getString("email"),
                rs.getString("cnpj"),
                rs.getString("pais"),
                rs.getString("estado"),
                rs.getString("cep"),
                rs.getString("empresa_descricao"),
                "*********",
                competencias
        )
        empresa.id = rs.getInt("empresa_id")

        return new Vaga(
                rs.getInt("id"),
                rs.getString("titulo"),
                empresa,
                competencias,
                rs.getString("local")
        )
    }

    private static void relacionarCompetencias(Connection conn, int vagaId, List<String> competencias) {
        competencias.each { comp ->
            int compId = CompetenciaDAO.inserirOuBuscarCompetencia(conn, comp)

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO vaga_competencia (id_vaga, id_competencia) VALUES (?, ?)"
            )
            stmt.setInt(1, vagaId)
            stmt.setInt(2, compId)
            stmt.executeUpdate()
            stmt.close()
        }
    }
}
