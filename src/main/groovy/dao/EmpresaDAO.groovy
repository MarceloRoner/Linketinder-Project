package dao

import model.Empresa
import java.sql.*

class EmpresaDAO implements IEmpresaDAO {

    private final Connection conn

    EmpresaDAO(Connection conn) {
        this.conn = conn
    }

    @Override
    List<Empresa> listarEmpresas() {
        List<Empresa> lista = []
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
        return lista
    }

    @Override
    void inserirEmpresa(Empresa e) {
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

        relacionarCompetencias(empresaId, e.competenciasEsperadas)
    }

    @Override
    void atualizarEmpresa(Empresa e) {
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
    }

    @Override
    void excluirEmpresa(int id) {
        removerVagasRelacionadas(id)
        removerCompetenciasRelacionadas(id)

        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM empresa WHERE id = ?"
        )
        stmt.setInt(1, id)
        stmt.executeUpdate()
        stmt.close()
    }

    private Empresa construirEmpresa(ResultSet rs) {
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

    private void relacionarCompetencias(int empresaId, List<String> competencias) {
        competencias.each { comp ->
            int compId = new CompetenciaDAO(conn).inserirOuBuscarCompetencia(comp)
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO empresa_competencia (id_empresa, id_competencia) VALUES (?, ?)"
            )
            stmt.setInt(1, empresaId)
            stmt.setInt(2, compId)
            stmt.executeUpdate()
            stmt.close()
        }
    }

    private void removerVagasRelacionadas(int empresaId) {
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

    private void removerCompetenciasRelacionadas(int empresaId) {
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM empresa_competencia WHERE id_empresa = ?"
        )
        stmt.setInt(1, empresaId)
        stmt.executeUpdate()
        stmt.close()
    }
}
