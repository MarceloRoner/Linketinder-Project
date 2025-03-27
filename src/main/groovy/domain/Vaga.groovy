package domain

class Vaga {
    Integer id
    String titulo
    Empresa empresa
    String local
    List<String> competenciasRequeridas = []

    Vaga(Integer id, String titulo, Empresa empresa, List<String> competenciasRequeridas, String local) {
        this.id = id
        this.titulo = titulo
        this.empresa = empresa
        this.competenciasRequeridas = competenciasRequeridas
        this.local = local
    }

    @Override
    String toString() {
        """
        Vaga ID: $id
        Título: $titulo
        Empresa: ${empresa?.nome}
        Local: $local
        Competências Requeridas: ${competenciasRequeridas.join(', ')}
        """.stripIndent()
    }
}
