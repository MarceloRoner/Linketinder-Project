package dao

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.PreparedStatement
import java.sql.ResultSet

import domain.Candidato
import domain.Empresa
import domain.Vaga

import java.time.LocalDate
import java.time.Period

class LinketinderDAO {

    static final String URL = "jdbc:postgresql://localhost:5432/linketinder"
    static final String USER = "marcelo"
    static final String PASSWORD = "jack"

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }

    // ----------------------------------------------------------------
    // CANDIDATO
    // ----------------------------------------------------------------
    // No arquivo LinketinderDAO.groovy
    static List<Candidato> listarCandidatos() {
        List<Candidato> lista = []
        Connection conn = null
        try {
            conn = getConnection()
            String sql = """
            SELECT c.id, c.nome, c.sobrenome, c.pais, c.email, c.cpf, c.estado, c.cep, c.descricao, c.data_nascimento,c.senha,
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
                LocalDate data_nascimento = rs.getDate("data_nascimento").toLocalDate()
                int idade = Period.between(data_nascimento, LocalDate.now()).getYears()
                String comps = rs.getString("competencias")
                List<String> competencias = comps ? comps.split(", ").toList() : []


                Candidato c = new Candidato(
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        data_nascimento,
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
                lista << c
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
            conn = getConnection()
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

            // Inserir/relacionar cada competência
            c.competencias.each { comp ->
                int compId = inserirOuBuscarCompetencia(conn, comp)

                PreparedStatement stmt2 = conn.prepareStatement(
                        "INSERT INTO candidato_competencia (id_candidato, id_competencia) VALUES (?, ?)"
                )
                stmt2.setInt(1, candidatoId)
                stmt2.setInt(2, compId)
                stmt2.executeUpdate()
                stmt2.close()
            }

        } finally {
            if (conn != null) conn.close()
        }
    }

    // ----------------------------------------------------------------
    // EMPRESA
    // ----------------------------------------------------------------
    static List<Empresa> listarEmpresas() {
        List<Empresa> lista = []
        Connection conn = null
        try {
            conn = getConnection()
            String sql = """
            SELECT e.id, e.nome, e.cnpj, e.email, e.pais, e.estado, e.cep, e.descricao,e.senha,
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
                lista << e
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
            conn = getConnection()
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
            stmt.setString(8, e.senha) // senha padrão

            ResultSet rs = stmt.executeQuery()
            rs.next()
            int empresaId = rs.getInt("id")
            rs.close()
            stmt.close()

            e.competenciasEsperadas.each { comp ->
                int compId = inserirOuBuscarCompetencia(conn, comp)

                PreparedStatement stmt2 = conn.prepareStatement(
                        "INSERT INTO empresa_competencia (id_empresa, id_competencia) VALUES (?, ?)"
                )
                stmt2.setInt(1, empresaId)
                stmt2.setInt(2, compId)
                stmt2.executeUpdate()
                stmt2.close()
            }

        } finally {
            if (conn != null) conn.close()
        }
    }

    static List<Vaga> listarVagas() {
        List<Vaga> lista = []
        Connection conn = null
        try {
            conn = getConnection()
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
                String comps = rs.getString("competencias")
                List<String> competencias = comps ? comps.split(", ").toList() : []
                Empresa emp = new Empresa(
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
                emp.id = rs.getInt("empresa_id")

                Vaga v = new Vaga(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        emp,
                        competencias,
                        rs.getString("local")

                )
                lista << v
            }

            rs.close()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
        return lista
    }

    static void inserirVaga(Vaga v) {
        Connection conn = null
        try {
            conn = getConnection()
            String sql = """
                INSERT INTO vaga (nome, descricao, local, id_empresa)
                VALUES (?, ?, ?, ?)
                RETURNING id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, v.titulo)
            stmt.setString(2, "")
            stmt.setString(3, v.local)
            stmt.setInt(4, v.empresa.id)

            ResultSet rs = stmt.executeQuery()
            rs.next()
            int vagaId = rs.getInt("id")
            rs.close()
            stmt.close()

            // Relacionar competências
            v.competenciasRequeridas.each { comp ->
                int compId = inserirOuBuscarCompetencia(conn, comp)

                PreparedStatement stmt2 = conn.prepareStatement(
                        "INSERT INTO vaga_competencia (id_vaga, id_competencia) VALUES (?, ?)"
                )
                stmt2.setInt(1, vagaId)
                stmt2.setInt(2, compId)
                stmt2.executeUpdate()
                stmt2.close()
            }

        } finally {
            if (conn != null) conn.close()
        }
    }

    // ----------------------------------------------------------------
    // COMPETENCIA (uso interno)
    // ----------------------------------------------------------------
    private static int inserirOuBuscarCompetencia(Connection conn, String nomeComp) {
        String selectSql = "SELECT id FROM competencia WHERE nome = ?"
        PreparedStatement sel = conn.prepareStatement(selectSql)
        sel.setString(1, nomeComp)
        ResultSet rsSel = sel.executeQuery()
        if (rsSel.next()) {
            int existente = rsSel.getInt("id")
            rsSel.close()
            sel.close()
            return existente
        }
        rsSel.close()
        sel.close()

        String insertSql = "INSERT INTO competencia (nome) VALUES (?) RETURNING id"
        PreparedStatement ins = conn.prepareStatement(insertSql)
        ins.setString(1, nomeComp)
        ResultSet rsIns = ins.executeQuery()
        rsIns.next()
        int novoId = rsIns.getInt("id")
        rsIns.close()
        ins.close()

        return novoId
    }

    static List<String> listarTodasCompetencias() {
        List<String> competencias = []
        Connection conn = null
        try {
            conn = getConnection()
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
            conn = getConnection()
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM competencia WHERE nome = ?"
            )
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
            conn = getConnection()
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

    static void excluirCandidato(int id) {
        Connection conn = null
        try {
            conn = getConnection()

            PreparedStatement stmt1 = conn.prepareStatement(
                    "DELETE FROM candidato_competencia WHERE id_candidato = ?"
            )
            stmt1.setInt(1, id)
            stmt1.executeUpdate()
            stmt1.close()

            PreparedStatement stmt2 = conn.prepareStatement(
                    "DELETE FROM curtida WHERE id_candidato = ?"
            )
            stmt2.setInt(1, id)
            stmt2.executeUpdate()
            stmt2.close()

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

    static void excluirEmpresa(int id) {
        Connection conn = null
        try {
            conn = getConnection()

            PreparedStatement stmt1 = conn.prepareStatement(
                    "DELETE FROM vaga_competencia WHERE id_vaga IN (SELECT id FROM vaga WHERE id_empresa = ?)"
            )
            stmt1.setInt(1, id)
            stmt1.executeUpdate()
            stmt1.close()

            PreparedStatement stmt2 = conn.prepareStatement(
                    "DELETE FROM curtida WHERE id_vaga IN (SELECT id FROM vaga WHERE id_empresa = ?)"
            )
            stmt2.setInt(1, id)
            stmt2.executeUpdate()
            stmt2.close()

            PreparedStatement stmt3 = conn.prepareStatement(
                    "DELETE FROM vaga WHERE id_empresa = ?"
            )
            stmt3.setInt(1, id)
            stmt3.executeUpdate()
            stmt3.close()

            PreparedStatement stmt4 = conn.prepareStatement(
                    "DELETE FROM empresa_competencia WHERE id_empresa = ?"
            )
            stmt4.setInt(1, id)
            stmt4.executeUpdate()
            stmt4.close()

            PreparedStatement stmt5 = conn.prepareStatement(
                    "DELETE FROM empresa WHERE id = ?"
            )
            stmt5.setInt(1, id)
            stmt5.executeUpdate()
            stmt5.close()

        } finally {
            if (conn != null) conn.close()
        }
    }

    static void atualizarCandidato(Candidato c) {
        Connection conn = null
        try {
            conn = getConnection()
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
    static void atualizarEmpresa(Empresa e) {
        Connection conn = null
        try {
            conn = getConnection()
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
    static void atualizarVaga(Vaga v) {
        Connection conn = null
        try {
            conn = getConnection()
            String sql = """
            UPDATE vaga 
            SET nome = ?, local = ?, id_empresa = ?
            WHERE id = ?
        """
            PreparedStatement stmt = conn.prepareStatement(sql)
            stmt.setString(1, v.titulo)
            stmt.setString(2, v.local)
            stmt.setInt(3, v.empresa.id)
            stmt.setInt(4, v.id)
            stmt.executeUpdate()
            stmt.close()
        } finally {
            if (conn != null) conn.close()
        }
    }
    static void excluirVaga(int id) {
        Connection conn = null
        try {
            conn = getConnection()

            PreparedStatement stmt1 = conn.prepareStatement(
                    "DELETE FROM vaga_competencia WHERE id_vaga = ?"
            )
            stmt1.setInt(1, id)
            stmt1.executeUpdate()
            stmt1.close()

            PreparedStatement stmt2 = conn.prepareStatement(
                    "DELETE FROM curtida WHERE id_vaga = ?"
            )
            stmt2.setInt(1, id)
            stmt2.executeUpdate()
            stmt2.close()

            PreparedStatement stmt3 = conn.prepareStatement(
                    "DELETE FROM vaga WHERE id = ?"
            )
            stmt3.setInt(1, id)
            stmt3.executeUpdate()
            stmt3.close()

        } finally {
            if (conn != null) conn.close()
        }
    }


}
