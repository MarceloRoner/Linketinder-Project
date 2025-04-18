package dao

import domain.Candidato
import utils.DatabaseUtils

import java.sql.*
import java.time.LocalDate
import java.time.Period

class CandidatoDAO {

    static List<Candidato> listarCandidatos() {
        List<Candidato> lista = []
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                SELECT c.id, c.nome, c.sobrenome, c.pais, c.email, c.cpf, c.estado, c.cep, c.descricao, c.data_nascimento, c.senha,
                       STRING_AGG(comp.nome, ', ') AS competencias
                FROM candidato c
                LEFT JOIN candidato_competencia cc ON c.id = cc.id_candidato
                LEFT JOIN competencia comp ON cc.id_competencia = comp.id
                GROUP BY c.id
                ORDER BY c.id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                lista << construirCandidato(rs)
            }

            rs.close()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
        return lista
    }

    static void inserirCandidato(Candidato c) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
                INSERT INTO candidato
                (nome, sobrenome, data_nascimento, email, cpf, pais, estado, cep, descricao, senha)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, c.nome)
            stmt.setString(2, c.sobrenome)
            stmt.setDate(3, java.sql.Date.valueOf(c.dataNascimento))
            stmt.setString(4, c.email)
            stmt.setString(5, c.cpf)
            stmt.setString(6, c.pais)
            stmt.setString(7, c.estado)
            stmt.setString(8, c.cep)
            stmt.setString(9, c.descricao)
            stmt.setString(10, c.senha)

            ResultSet rs = stmt.executeQuery()
            rs.next()
            int candidatoId = rs.getInt("id")
            rs.close()
            stmt.close()

            relacionarCompetencias(conn, candidatoId, c.competencias)

        } finally {
            if (conn != null) conn.close()
        }
    }

    static void excluirCandidato(int id) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()

            removerRelacionamentos(conn, id)

            PreparedStatement stmt3 = conn.prepareStatement(
                    "DELETE FROM candidato WHERE id = ?"
            )
            stmt3.setInt(1, id)
            stmt3.executeUpdate()
            stmt3.close()

        } finally {
            if (conn != null) conn.close()
        }
    }

    private static Candidato construirCandidato(ResultSet rs) {
        LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate()
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears()
        String comps = rs.getString("competencias")
        List<String> competencias = comps ? comps.split(", ").toList() : []

        Candidato c = new Candidato(
                rs.getString("nome"),
                rs.getString("sobrenome"),
                dataNascimento,
                rs.getString("email"),
                rs.getString("cpf"),
                idade,
                rs.getString("pais"),
                rs.getString("estado"),
                rs.getString("cep"),
                rs.getString("descricao"),
                competencias,
                rs.getString("senha")
        )
        c.id = rs.getInt("id")
        return c
    }

    private static void relacionarCompetencias(Connection conn, int candidatoId, List<String> competencias) {
        competencias.each { comp ->
            int compId = CompetenciaDAO.inserirOuBuscarCompetencia(conn, comp)

            PreparedStatement stmt2 = conn.prepareStatement(
                    "INSERT INTO candidato_competencia (id_candidato, id_competencia) VALUES (?, ?)"
            )
            stmt2.setInt(1, candidatoId)
            stmt2.setInt(2, compId)
            stmt2.executeUpdate()
            stmt2.close()
        }
    }

    private static void removerRelacionamentos(Connection conn, int candidatoId) {
        PreparedStatement stmt1 = conn.prepareStatement(
                "DELETE FROM candidato_competencia WHERE id_candidato = ?"
        )
        stmt1.setInt(1, candidatoId)
        stmt1.executeUpdate()
        stmt1.close()

        PreparedStatement stmt2 = conn.prepareStatement(
                "DELETE FROM curtida WHERE id_candidato = ?"
        )
        stmt2.setInt(1, candidatoId)
        stmt2.executeUpdate()
        stmt2.close()
    }
    static void atualizarCandidato(Candidato c) {
        Connection conn = null
        try {
            conn = DatabaseUtils.getConnection()
            String sql = """
            UPDATE candidato 
            SET nome = ?, sobrenome = ?, data_nascimento = ?, email = ?, 
                cpf = ?, pais = ?, estado = ?, cep = ?, descricao = ?, senha = ?
            WHERE id = ?
        """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, c.nome)
            stmt.setString(2, c.sobrenome)
            stmt.setDate(3, java.sql.Date.valueOf(c.dataNascimento))
            stmt.setString(4, c.email)
            stmt.setString(5, c.cpf)
            stmt.setString(6, c.pais)
            stmt.setString(7, c.estado)
            stmt.setString(8, c.cep)
            stmt.setString(9, c.descricao)
            stmt.setString(10, c.senha)
            stmt.setInt(11, c.id)

            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }

}
