package dao

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.PreparedStatement
import java.sql.ResultSet

import domain.Candidato
import domain.Empresa
import domain.Vaga

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
    static List<Candidato> listarCandidatos() {
        List<Candidato> lista = []
        Connection conn = null
        try {
            conn = getConnection()
            String sql = """
                SELECT id, nome, email, cpf, estado, cep, descricao
                FROM candidato
                ORDER BY id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                Candidato c = new Candidato(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        0, // idade ainda não vem do bd
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("descricao"),
                        []
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
            stmt.setString(2, "") // sobrenome fixo
            stmt.setDate(3, java.sql.Date.valueOf("1990-01-01")) // data_nascimento fixa
            stmt.setString(4, c.email)
            stmt.setString(5, c.cpf)
            stmt.setString(6, "Brasil")
            stmt.setString(7, c.estado)
            stmt.setString(8, c.cep)
            stmt.setString(9, c.descricao)
            stmt.setString(10, "1234") // senha padrão

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
                SELECT id, nome, cnpj, email, pais, estado, cep, descricao
                FROM empresa
                ORDER BY id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                Empresa e = new Empresa(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cnpj"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("descricao"),
                        []
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
            stmt.setString(8, "1234") // senha padrão

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
                SELECT v.id, v.nome, v.descricao, v.local,
                       e.id AS empresa_id, e.nome AS empresa_nome,
                       e.email, e.cnpj, e.pais, e.estado, e.cep, e.descricao AS empresa_descricao
                FROM vaga v
                JOIN empresa e ON v.id_empresa = e.id
                ORDER BY v.id
            """
            PreparedStatement stmt = conn.prepareStatement(sql)
            ResultSet rs = stmt.executeQuery()

            while (rs.next()) {
                Empresa emp = new Empresa(
                        rs.getString("empresa_nome"),
                        rs.getString("email"),
                        rs.getString("cnpj"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("empresa_descricao"),
                        []
                )
                emp.id = rs.getInt("empresa_id")

                Vaga v = new Vaga(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        emp,
                        []
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
            stmt.setString(2, "") // descrição fixa
            stmt.setString(3, "Remoto") // local fixo
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

}
