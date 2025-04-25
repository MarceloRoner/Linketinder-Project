package view

import controller.*
import service.LinketinderFacade
import model.*

import java.time.LocalDate

class LinketinderView {

    private final LinketinderFacade facade
    private final CandidatoController candCtl
    private final EmpresaController   empCtl
    private final VagaController      vagaCtl

    LinketinderView(LinketinderFacade facade,
                    CandidatoController candCtl,
                    EmpresaController   empCtl,
                    VagaController      vagaCtl) {
        this.facade  = facade
        this.candCtl = candCtl
        this.empCtl  = empCtl
        this.vagaCtl = vagaCtl
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


    private void listarCandidatos()   { println "\n--- CANDIDATOS ---"; candCtl.listar().each { println it } }
    private void listarEmpresas()     { println "\n--- EMPRESAS ---";   empCtl.listar().each { println it } }
    private void listarVagas()        { println "\n--- VAGAS ---";      vagaCtl.listar().each { println it } }
    private void listarCompetencias() { println "\n--- COMPETÊNCIAS ---"; facade.listarCompetencias().each { println "- $it" } }
    private void exibirCurtidas()     { println "\n--- CURTIDAS ---";   facade.listarCurtidas().each { println it } }


    private void cadastrarCandidato(Scanner sc) {
        println "\nCadastro de Candidato"
        print "Nome: ";        String nome   = sc.nextLine()
        print "Sobrenome: ";    String sobren = sc.nextLine()
        print "Nascimento (AAAA-MM-DD): "; LocalDate dn = LocalDate.parse(sc.nextLine())
        print "E-mail: ";       String email  = sc.nextLine()
        print "CPF: ";          String cpf    = sc.nextLine()
        int idade  = lerInt(sc, "Idade: ")
        print "Estado: ";       String estado = sc.nextLine()
        print "CEP: ";          String cep    = sc.nextLine()
        print "Descrição: ";    String desc   = sc.nextLine()
        print "Competências (vírgula): "; List<String> comps = sc.nextLine().split(",")*.trim()
        print "Senha: ";        String senha  = sc.nextLine()
        def cand = new Candidato(nome,sobren,dn,email,cpf,idade,"Brasil",estado,cep,desc,comps,senha)
        candCtl.criar(cand)
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
        def emp = new Empresa(nome,email,cnpj,pais,estado,cep,desc,senha,comps)
        empCtl.criar(emp)
        println "Empresa cadastrada."
    }

    private void cadastrarVaga(Scanner sc) {
        def empresas = empCtl.listar()
        if (empresas.isEmpty()) { println "Nenhuma empresa cadastrada."; return }
        println "\nCadastro de Vaga"
        print "Título: "; String titulo = sc.nextLine()
        println "Escolha empresa:"
        empresas.eachWithIndex { e,i -> println "[$i] ${e.nome}" }
        int idx = lerInt(sc, "Número da empresa: ")
        def emp = empresas[idx]
        print "Local: "; String local = sc.nextLine()
        print "Competências (vírgula): "; List<String> comps = sc.nextLine().split(",")*.trim()
        def vaga = new Vaga(null,titulo,emp,comps,local)
        vagaCtl.criar(vaga)
        println "Vaga cadastrada."
    }


    private void candidatoCurteVaga(Scanner sc) {
        def candidatos = candCtl.listar()
        def vagas      = vagaCtl.listar()
        if (candidatos.isEmpty() || vagas.isEmpty()) { println "Sem dados."; return }
        println "Escolha candidato:"
        candidatos.eachWithIndex { c,i -> println "[$i] ${c.nome}" }
        def cand = candidatos[lerInt(sc,"Número: ")]
        println "Escolha vaga:"
        vagas.eachWithIndex { v,i -> println "[$i] ${v.titulo}" }
        def vaga = vagas[lerInt(sc,"Número: ")]
        facade.curtirPorCandidato(cand,vaga)
    }

    private void empresaCurteCandidato(Scanner sc) {
        def empresas   = empCtl.listar()
        def candidatos = candCtl.listar()
        if (empresas.isEmpty() || candidatos.isEmpty()) { println "Sem dados."; return }
        println "Escolha empresa:"
        empresas.eachWithIndex { e,i -> println "[$i] ${e.nome}" }
        def emp = empresas[lerInt(sc,"Número: ")]
        def vagasEmp = vagaCtl.listar().findAll { it.empresa?.id == emp.id }
        if (vagasEmp.isEmpty()) { println "Empresa sem vagas."; return }
        println "Escolha vaga:"
        vagasEmp.eachWithIndex { v,i -> println "[$i] ${v.titulo}" }
        def vaga = vagasEmp[lerInt(sc,"Número: ")]
        println "Escolha candidato:"
        candidatos.eachWithIndex { c,i -> println "[$i] ${c.nome}" }
        def cand = candidatos[lerInt(sc,"Número: ")]
        facade.curtirPorEmpresa(cand,vaga)
    }


    private void excluirCompetencia(Scanner sc) {
        print "Nome da competência: "
        facade.excluirCompetencia(sc.nextLine())
        println "Competência excluída."
    }

    private void atualizarCompetencia(Scanner sc) {
        print "Nome atual: "; String atual = sc.nextLine()
        print "Novo nome: ";  String novo  = sc.nextLine()
        facade.atualizarCompetencia(atual,novo)
        println "Competência atualizada."
    }


    private void excluirCandidato(Scanner sc) {
        listarCandidatos()
        candCtl.excluir(lerInt(sc,"ID candidato: "))
        println "Candidato excluído."
    }

    private void excluirEmpresa(Scanner sc) {
        listarEmpresas()
        empCtl.excluir(lerInt(sc,"ID empresa: "))
        println "Empresa excluída."
    }

    private void excluirVaga(Scanner sc) {
        listarVagas()
        vagaCtl.excluir(lerInt(sc,"ID vaga: "))
        println "Vaga excluída."
    }


    private void atualizarCandidato(Scanner sc) {
        def candidatos = candCtl.listar()
        listarCandidatos()
        def cand = candidatos.find { it.id == lerInt(sc,"ID candidato: ") }
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
        candCtl.atualizar(cand)
        println "Candidato atualizado."
    }

    private void atualizarEmpresa(Scanner sc) {
        def empresas = empCtl.listar()
        listarEmpresas()
        def emp = empresas.find { it.id == lerInt(sc,"ID empresa: ") }
        if (!emp) { println "Não encontrada."; return }
        print "Novo nome (${emp.nome}): "; emp.nome = sc.nextLine()
        print "Novo email (${emp.email}): "; emp.email = sc.nextLine()
        print "Novo CNPJ (${emp.cnpj}): "; emp.cnpj = sc.nextLine()
        print "Novo país (${emp.pais}): "; emp.pais = sc.nextLine()
        print "Novo estado (${emp.estado}): "; emp.estado = sc.nextLine()
        print "Novo CEP (${emp.cep}): "; emp.cep = sc.nextLine()
        print "Nova descrição: "; emp.descricao = sc.nextLine()
        print "Nova senha: "; emp.senha = sc.nextLine()
        empCtl.atualizar(emp)
        println "Empresa atualizada."
    }

    private void atualizarVaga(Scanner sc) {
        def vagas = vagaCtl.listar()
        listarVagas()
        def vaga = vagas.find { it.id == lerInt(sc,"ID vaga: ") }
        if (!vaga) { println "Não encontrada."; return }
        print "Novo título (${vaga.titulo}): "; vaga.titulo = sc.nextLine()
        print "Novo local (${vaga.local}): ";   vaga.local  = sc.nextLine()
        print "Competências (vírgula): ";        vaga.competenciasRequeridas =
                sc.nextLine().split(",")*.trim()
        vagaCtl.atualizar(vaga)
        println "Vaga atualizada."
    }


    private Integer lerInt(Scanner sc, String label) {
        while (true) {
            print label
            def txt = sc.nextLine().trim()
            if (txt.isInteger()) return txt.toInteger()
            println "Valor inválido, digite um número."
        }
    }
}
