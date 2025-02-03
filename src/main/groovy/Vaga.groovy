class Vaga {
    Integer id
    String titulo
    Empresa empresa
    List<String> competenciasRequeridas = []

    Vaga(Integer id, String titulo, Empresa empresa, List<String> competenciasRequeridas) {
        this.id = id
        this.titulo = titulo
        this.empresa = empresa
        this.competenciasRequeridas = competenciasRequeridas
    }

    @Override
    String toString() {
        """
        Vaga ID: $id
        Título: $titulo
        Empresa: ${empresa?.nome}
        Competências Requeridas: ${competenciasRequeridas.join(', ')}
        """.stripIndent()
    }
}
