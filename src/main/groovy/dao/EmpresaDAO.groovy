package dao

import domain.Empresa
import utils.DatabaseUtils

import java.sql.*

class EmpresaDAO {

    static List<Empresa> listarEmpresas() {
        List<Empresa> lista = []
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                SELECT e.id, e.nome, e.cnpj, e.email, e.pais, e.estado, e.cep, e.descricao, e.senha,
                       STRING_AGG(comp.nome, ', ') AS competencias
                FROM empresa e
                LEFT JOIN empresa_competencia ec ON e.id = ec.id_empresa
                LEFT JOIN competencia comp ON ec.id_competencia = comp.id
                GROUP BY e.id
                ORDER BY e.id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                lista << construirEmpresa(rs)
            }

            rs.close()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
        return lista
    }

    static void inserirEmpresa(Empresa e) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                INSERT INTO empresa
                (nome, cnpj, email, pais, estado, cep, descricao, senha)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, e.nome)
            stmt.setString(2, e.cnpj)
            stmt.setString(3, e.email)
            stmt.setString(4, e.pais)
            stmt.setString(5, e.estado)
            stmt.setString(6, e.cep)
            stmt.setString(7, e.descricao)
            stmt.setString(8, e.senha)

            ResultSet rs = stmt.executeQuery()
            rs.next()
            int empresaId = rs.getInt("id")
            rs.close()
            stmt.close()

            relacionarCompetencias(conn, empresaId, e.competenciasEsperadas)

        } finally {
            if (conn != null) conn.close()
        }
    }

    static void atualizarEmpresa(Empresa e) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                UPDATE empresa 
                SET nome = ?, email = ?, cnpj = ?, pais = ?, estado = ?, 
                    cep = ?, descricao = ?, senha = ?
                WHERE id = ?
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, e.nome)
            stmt.setString(2, e.email)
            stmt.setString(3, e.cnpj)
            stmt.setString(4, e.pais)
            stmt.setString(5, e.estado)
            stmt.setString(6, e.cep)
            stmt.setString(7, e.descricao)
            stmt.setString(8, e.senha)
            stmt.setInt(9, e.id)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }

    static void excluirEmpresa(int id) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            removerVagasRelacionadas(conn, id)
            removerCompetenciasRelacionadas(conn, id)

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM empresa WHERE id = ?"
            )
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }

    private static Empresa construirEmpresa(ResultSet rs) {
        String comps = rs.getString("competencias")
        List<String> competencias = comps ? comps.split(", ").toList() : []

        Empresa e = new Empresa(
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cnpj"),
                rs.getString("pais"),
                rs.getString("estado"),
                rs.getString("cep"),
                rs.getString("descricao"),
                rs.getString("senha"),
                competencias
        )
        e.id = rs.getInt("id")
        return e
    }

    private static void relacionarCompetencias(Connection conn, int empresaId, List<String> competencias) {
        competencias.each { comp ->
            int compId = CompetenciaDAO.inserirOuBuscarCompetencia(conn, comp)
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO empresa_competencia (id_empresa, id_competencia) VALUES (?, ?)"
            )
            stmt.setInt(1, empresaId)
            stmt.setInt(2, compId)
            stmt.executeUpdate()
            stmt.close()
        }
    }

    private static void removerVagasRelacionadas(Connection conn, int empresaId) {
        String[] sqls = [
                "DELETE FROM vaga_competencia WHERE id_vaga IN (SELECT id FROM vaga WHERE id_empresa = ?)",
                "DELETE FROM curtida WHERE id_vaga IN (SELECT id FROM vaga WHERE id_empresa = ?)",
                "DELETE FROM vaga WHERE id_empresa = ?"
        ]
        sqls.each { sql ->
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setInt(1, empresaId)
            stmt.executeUpdate()
            stmt.close()
        }
    }

    private static void removerCompetenciasRelacionadas(Connection conn, int empresaId) {
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM empresa_competencia WHERE id_empresa = ?"
        )
        stmt.setInt(1, empresaId)
        stmt.executeUpdate()
        stmt.close()
    }
}
