package app

import domain.*
import service.*
import java.time.LocalDate
import java.util.Scanner

class LinketinderApp {

    private final CandidatoService candidatoService
    private final EmpresaService   empresaService
    private final VagaService      vagaService
    private final CompetenciaService competenciaService
    private final CurtidaService     curtidaService

    LinketinderApp(CandidatoService candidatoService,
                   EmpresaService   empresaService,
                   VagaService      vagaService,
                   CompetenciaService competenciaService,
                   CurtidaService     curtidaService) {
        this.candidatoService   = candidatoService
        this.empresaService     = empresaService
        this.vagaService        = vagaService
        this.competenciaService = competenciaService
        this.curtidaService     = curtidaService
    }

    void iniciar() {
        println "Linketinder – Desenvolvido por Marcelo"
        exibirMenu()
    }

    private void exibirMenu() {
        def sc = new Scanner(System.in)
        while (true) {
            println """
            |--- MENU ---
            |1) Listar candidatos
            |2) Listar empresas
            |3) Cadastrar candidato
            |4) Cadastrar empresa
            |5) Listar vagas
            |6) Candidato curte vaga
            |7) Empresa curte candidato
            |8) Exibir curtidas
            |9) Cadastrar vaga
            |10) Listar competências
            |11) Excluir competência
            |12) Atualizar competência
            |13) Excluir candidato
            |14) Excluir empresa
            |15) Excluir vaga
            |16) Atualizar candidato
            |17) Atualizar empresa
            |18) Atualizar vaga
            |19) Sair
            """.stripMargin()
            print "Escolha: "
            switch (sc.nextLine()) {
                case "1": listarCandidatos(); break
                case "2": listarEmpresas(); break
                case "3": cadastrarCandidato(sc); break
                case "4": cadastrarEmpresa(sc); break
                case "5": listarVagas(); break
                case "6": candidatoCurteVaga(sc); break
                case "7": empresaCurteCandidato(sc); break
                case "8": exibirCurtidas(); break
                case "9": cadastrarVaga(sc); break
                case "10": listarCompetencias(); break
                case "11": excluirCompetencia(sc); break
                case "12": atualizarCompetencia(sc); break
                case "13": excluirCandidato(sc); break
                case "14": excluirEmpresa(sc); break
                case "15": excluirVaga(sc); break
                case "16": atualizarCandidato(sc); break
                case "17": atualizarEmpresa(sc); break
                case "18": atualizarVaga(sc); break
                case "19": return
                default : println "Opção inválida."
            }
        }
    }

    private void listarCandidatos() {
        println "\n--- CANDIDATOS ---"
        candidatoService.listarTodos().each { println it }
    }

    private void listarEmpresas() {
        println "\n--- EMPRESAS ---"
        empresaService.listarTodas().each { println it }
    }

    private void listarVagas() {
        println "\n--- VAGAS ---"
        vagaService.listarTodas().each { println it }
    }

    private void listarCompetencias() {
        println "\n--- COMPETÊNCIAS ---"
        competenciaService.listarTodas().each { println "- $it" }
    }

    private void exibirCurtidas() {
        println "\n--- CURTIDAS ---"
        curtidaService.listarTodas().each { println it }
    }

    private void cadastrarCandidato(Scanner sc) {
        println "\nCadastro de Candidato"
        print "Nome: ";        String nome   = sc.nextLine()
        print "Sobrenome: ";    String sobren = sc.nextLine()
        print "Nascimento (AAAA-MM-DD): "; LocalDate dn = LocalDate.parse(sc.nextLine())
        print "E-mail: ";       String email  = sc.nextLine()
        print "CPF: ";          String cpf    = sc.nextLine()
        print "Idade: ";        int idade     = sc.nextLine().toInteger()
        print "Estado: ";       String estado = sc.nextLine()
        print "CEP: ";          String cep    = sc.nextLine()
        print "Descrição: ";    String desc   = sc.nextLine()
        print "Competências (vírgula): "; List<String> comps = sc.nextLine().split(",")*.trim()
        print "Senha: ";        String senha  = sc.nextLine()
        def cand = new Candidato(nome, sobren, dn, email, cpf, idade, "Brasil", estado, cep, desc, comps, senha)
        candidatoService.cadastrar(cand)
        println "Candidato cadastrado."
    }

    private void cadastrarEmpresa(Scanner sc) {
        println "\nCadastro de Empresa"
        print "Nome: "; String nome = sc.nextLine()
        print "E-mail: "; String email = sc.nextLine()
        print "CNPJ: "; String cnpj = sc.nextLine()
        print "País: "; String pais = sc.nextLine()
        print "Estado: "; String estado = sc.nextLine()
        print "CEP: "; String cep = sc.nextLine()
        print "Descrição: "; String desc = sc.nextLine()
        print "Competências esperadas (vírgula): "; List<String> comps = sc.nextLine().split(",")*.trim()
        print "Senha: "; String senha = sc.nextLine()
        def emp = new Empresa(nome, email, cnpj, pais, estado, cep, desc, senha, comps)
        empresaService.cadastrar(emp)
        println "Empresa cadastrada."
    }

    private void cadastrarVaga(Scanner sc) {
        def empresas = empresaService.listarTodas()
        if (empresas.isEmpty()) { println "Nenhuma empresa cadastrada."; return }
        println "\nCadastro de Vaga"
        print "Título: "; String titulo = sc.nextLine()
        println "Escolha empresa:"
        empresas.eachWithIndex { e,i -> println "[$i] ${e.nome}" }
        int idx = sc.nextLine().toInteger()
        def emp = empresas[idx]
        print "Local: "; String local = sc.nextLine()
        print "Competências (vírgula): "; List<String> comps = sc.nextLine().split(",")*.trim()
        def vaga = new Vaga(null, titulo, emp, comps, local)
        vagaService.cadastrar(vaga)
        println "Vaga cadastrada."
    }

    private void candidatoCurteVaga(Scanner sc) {
        def candidatos = candidatoService.listarTodos()
        def vagas = vagaService.listarTodas()
        if (candidatos.isEmpty() || vagas.isEmpty()) { println "Sem dados."; return }
        println "Escolha candidato:"
        candidatos.eachWithIndex { c,i -> println "[$i] ${c.nome}" }
        def cand = candidatos[sc.nextLine().toInteger()]
        println "Escolha vaga:"
        vagas.eachWithIndex { v,i -> println "[$i] ${v.titulo}" }
        def vaga = vagas[sc.nextLine().toInteger()]
        curtidaService.curtirPorCandidato(cand, vaga)
    }

    private void empresaCurteCandidato(Scanner sc) {
        def empresas = empresaService.listarTodas()
        def candidatos = candidatoService.listarTodos()
        if (empresas.isEmpty() || candidatos.isEmpty()) { println "Sem dados."; return }
        println "Escolha empresa:"
        empresas.eachWithIndex { e,i -> println "[$i] ${e.nome}" }
        def emp = empresas[sc.nextLine().toInteger()]
        def vagasEmp = vagaService.listarPorEmpresa(emp.id)
        if (vagasEmp.isEmpty()) { println "Empresa sem vagas."; return }
        println "Escolha vaga:"
        vagasEmp.eachWithIndex { v,i -> println "[$i] ${v.titulo}" }
        def vaga = vagasEmp[sc.nextLine().toInteger()]
        println "Escolha candidato:"
        candidatos.eachWithIndex { c,i -> println "[$i] ${c.nome}" }
        def cand = candidatos[sc.nextLine().toInteger()]
        curtidaService.curtirPorEmpresa(cand, vaga)
    }

    private void excluirCompetencia(Scanner sc) {
        print "Nome da competência: "
        competenciaService.excluir(sc.nextLine())
        println "Competência excluída."
    }

    private void atualizarCompetencia(Scanner sc) {
        print "Nome atual: "; String atual = sc.nextLine()
        print "Novo nome: ";  String novo  = sc.nextLine()
        competenciaService.atualizar(atual, novo)
        println "Competência atualizada."
    }

    private void excluirCandidato(Scanner sc) {
        listarCandidatos()
        print "ID candidato: "
        candidatoService.excluir(sc.nextLine().toInteger())
        println "Candidato excluído."
    }

    private void excluirEmpresa(Scanner sc) {
        listarEmpresas()
        print "ID empresa: "
        empresaService.excluir(sc.nextLine().toInteger())
        println "Empresa excluída."
    }

    private void excluirVaga(Scanner sc) {
        listarVagas()
        print "ID vaga: "
        vagaService.excluir(sc.nextLine().toInteger())
        println "Vaga excluída."
    }

    private void atualizarCandidato(Scanner sc) {
        listarCandidatos()
        print "ID candidato: "
        def cand = candidatoService.buscarPorId(sc.nextLine().toInteger())
        if (!cand) { println "Não encontrado."; return }
        print "Novo nome (${cand.nome}): "; cand.nome = sc.nextLine()
        print "Novo sobrenome (${cand.sobrenome}): "; cand.sobrenome = sc.nextLine()
        print "Novo email (${cand.email}): "; cand.email = sc.nextLine()
        print "Novo CPF (${cand.cpf}): "; cand.cpf = sc.nextLine()
        print "Novo país (${cand.pais}): "; cand.pais = sc.nextLine()
        print "Novo estado (${cand.estado}): "; cand.estado = sc.nextLine()
        print "Novo CEP (${cand.cep}): "; cand.cep = sc.nextLine()
        print "Nova descrição: "; cand.descricao = sc.nextLine()
        print "Nova senha: "; cand.senha = sc.nextLine()
        candidatoService.atualizar(cand)
        println "Candidato atualizado."
    }

    private void atualizarEmpresa(Scanner sc) {
        listarEmpresas()
        print "ID empresa: "
        def emp = empresaService.buscarPorId(sc.nextLine().toInteger())
        if (!emp) { println "Não encontrada."; return }
        print "Novo nome (${emp.nome}): "; emp.nome = sc.nextLine()
        print "Novo email (${emp.email}): "; emp.email = sc.nextLine()
        print "Novo CNPJ (${emp.cnpj}): "; emp.cnpj = sc.nextLine()
        print "Novo país (${emp.pais}): "; emp.pais = sc.nextLine()
        print "Novo estado (${emp.estado}): "; emp.estado = sc.nextLine()
        print "Novo CEP (${emp.cep}): "; emp.cep = sc.nextLine()
        print "Nova descrição: "; emp.descricao = sc.nextLine()
        print "Nova senha: "; emp.senha = sc.nextLine()
        empresaService.atualizar(emp)
        println "Empresa atualizada."
    }

    private void atualizarVaga(Scanner sc) {
        listarVagas()
        print "ID vaga: "
        def vaga = vagaService.buscarPorId(sc.nextLine().toInteger())
        if (!vaga) { println "Não encontrada."; return }
        print "Novo título (${vaga.titulo}): "; vaga.titulo = sc.nextLine()
        print "Novo local (${vaga.local}): "; vaga.local = sc.nextLine()
        print "Competências (vírgula): "; vaga.competenciasRequeridas = sc.nextLine().split(",")*.trim()
        vagaService.atualizar(vaga)
        println "Vaga atualizada."
    }
}
