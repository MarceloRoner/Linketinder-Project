import java.util.Scanner
import domain.Candidato
import domain.Empresa
import domain.Vaga
import dao.LinketinderDAO

class LinketinderApp {

    static List<Candidato> candidatos = []
    static List<Empresa> empresas = []
    static List<Vaga> vagas = []
    static List<Curtida> curtidas = []

    static void main(String[] args) {
        println "Projeto Linketinder - Desenvolvido por Marcelo (exemplo)"

        // Carrega do banco inicialmente
        candidatos = LinketinderDAO.listarCandidatos()
        empresas   = LinketinderDAO.listarEmpresas()
        vagas      = LinketinderDAO.listarVagas()

        exibirMenu()
    }

    static void exibirMenu() {
        def scanner = new Scanner(System.in)
        while(true) {
            println "\n--- LINKETINDER MENU ---"
            println "1) Listar todos os candidatos"
            println "2) Listar todas as empresas"
            println "3) Cadastrar novo candidato"
            println "4) Cadastrar nova empresa"
            println "5) Listar vagas"
            println "6) Candidato curte vaga"
            println "7) Empresa curte candidato"
            println "8) Exibir curtidas"
            println "9) Sair"
            println "9) Cadastrar nova vaga"
            println "10) Sair"
            print "Escolha uma opção: "

            String opcao = scanner.nextLine()

            switch(opcao) {
                case "1":
                    listarCandidatos()
                    break
                case "2":
                    listarEmpresas()
                    break
                case "3":
                    cadastrarCandidato(scanner)
                    break
                case "4":
                    cadastrarEmpresa(scanner)
                    break
                case "5":
                    listarVagas()
                    break
                case "6":
                    candidatoCurteVaga(scanner)
                    break
                case "7":
                    empresaCurteCandidato(scanner)
                    break
                case "8":
                    exibirCurtidas()
                    break
                case "10":
                    println "Saindo do sistema..."
                    System.exit(0)
                    break
                case "9":
                    cadastrarVaga(scanner)
                    break
                default:
                    println "Opção inválida. Tente novamente."
            }
        }
    }

    // -------------------------------------------------------
    // 1) LISTAR CANDIDATOS
    // -------------------------------------------------------
    static void listarCandidatos() {
        println "\n--- LISTA DE CANDIDATOS ---"
        if (candidatos.isEmpty()) {
            println "Não há candidatos cadastrados!"
        } else {
            candidatos.each { println it }
        }
    }

    // -------------------------------------------------------
    // 2) LISTAR EMPRESAS
    // -------------------------------------------------------
    static void listarEmpresas() {
        println "\n--- LISTA DE EMPRESAS ---"
        if (empresas.isEmpty()) {
            println "Não há empresas cadastradas!"
        } else {
            empresas.each { println it }
        }
    }

    // -------------------------------------------------------
    // 3) CADASTRAR CANDIDATO
    // -------------------------------------------------------
    static void cadastrarCandidato(Scanner scanner) {
        println "\n--- CADASTRO DE CANDIDATO ---"

        print "Nome: "
        String nome = scanner.nextLine()

        print "E-mail: "
        String email = scanner.nextLine()

        print "CPF: "
        String cpf = scanner.nextLine()

        print "Idade: "
        Integer idade = scanner.nextLine() as Integer

        print "Estado: "
        String estado = scanner.nextLine()

        print "CEP: "
        String cep = scanner.nextLine()

        print "Descrição pessoal: "
        String descricao = scanner.nextLine()

        print "Competências (separe por vírgula): "
        String comps = scanner.nextLine()
        List<String> competencias = comps.split(",")*.trim()

        Candidato novo = new Candidato(nome, email, cpf, idade, estado, cep, descricao, competencias)
        LinketinderDAO.inserirCandidato(novo)
        candidatos << novo

        println "Candidato cadastrado com sucesso!"
    }

    // -------------------------------------------------------
    // 4) CADASTRAR EMPRESA
    // -------------------------------------------------------
    static void cadastrarEmpresa(Scanner scanner) {
        println "\n--- CADASTRO DE EMPRESA ---"

        print "Nome da Empresa: "
        String nome = scanner.nextLine()

        print "E-mail Corporativo: "
        String email = scanner.nextLine()

        print "CNPJ: "
        String cnpj = scanner.nextLine()

        print "País: "
        String pais = scanner.nextLine()

        print "Estado: "
        String estado = scanner.nextLine()

        print "CEP: "
        String cep = scanner.nextLine()

        print "Descrição da empresa: "
        String descricao = scanner.nextLine()

        print "Competências esperadas (separe por vírgula): "
        String comps = scanner.nextLine()
        List<String> competencias = comps.split(",")*.trim()

        Empresa nova = new Empresa(nome, email, cnpj, pais, estado, cep, descricao, competencias)
        LinketinderDAO.inserirEmpresa(nova)
        empresas << nova

        println "Empresa cadastrada com sucesso!"
    }

    // -------------------------------------------------------
    // 5) LISTAR VAGAS
    // -------------------------------------------------------
    static void listarVagas() {
        println "\n--- LISTA DE VAGAS ---"
        if (vagas.isEmpty()) {
            println "Não há vagas cadastradas!"
        } else {
            vagas.each { println it }
        }
    }

    // -------------------------------------------------------
    // 6) CANDIDATO CURTE VAGA
    // -------------------------------------------------------
    static void candidatoCurteVaga(Scanner scanner) {
        if (candidatos.isEmpty()) {
            println "Não há candidatos cadastrados!"
            return
        }
        if (vagas.isEmpty()) {
            println "Não há vagas cadastradas!"
            return
        }

        println "\nEscolha um candidato (pelo índice):"
        candidatos.eachWithIndex { cand, idx ->
            println "[${idx}] ${cand.nome}"
        }
        int candIdx = scanner.nextLine().toInteger()
        if (candIdx < 0 || candIdx >= candidatos.size()) {
            println "Índice inválido."
            return
        }
        Candidato candidato = candidatos[candIdx]

        println "\nEscolha uma vaga (pelo índice):"
        vagas.eachWithIndex { vg, idx ->
            println "[${idx}] ${vg.titulo} (Empresa: ${vg.empresa?.nome})"
        }
        int vagaIdx = scanner.nextLine().toInteger()
        if (vagaIdx < 0 || vagaIdx >= vagas.size()) {
            println "Índice inválido."
            return
        }
        Vaga vaga = vagas[vagaIdx]

        Curtida curtidaExistente = curtidas.find { it.candidato == candidato && it.vaga == vaga }
        if (!curtidaExistente) {
            Curtida novaCurtida = new Curtida(candidato, vaga)
            curtidas << novaCurtida
            println "Candidato ${candidato.nome} curtiu a vaga '${vaga.titulo}'."
        } else {
            curtidaExistente.candidatoCurtiu = true
            println "Candidato ${candidato.nome} (re)curtiu a vaga '${vaga.titulo}'."
            if (curtidaExistente.isMatch()) {
                println ">>> MATCH REALIZADO! <<<"
            }
        }
    }

    // -------------------------------------------------------
    // 7) EMPRESA CURTE CANDIDATO
    // -------------------------------------------------------
    static void empresaCurteCandidato(Scanner scanner) {
        if (empresas.isEmpty()) {
            println "Não há empresas cadastradas!"
            return
        }
        if (vagas.isEmpty()) {
            println "Não há vagas cadastradas!"
            return
        }
        if (candidatos.isEmpty()) {
            println "Não há candidatos cadastrados!"
            return
        }

        println "\nEscolha uma empresa (pelo índice):"
        empresas.eachWithIndex { emp, idx ->
            println "[${idx}] ${emp.nome}"
        }
        int empIdx = scanner.nextLine().toInteger()
        if (empIdx < 0 || empIdx >= empresas.size()) {
            println "Índice inválido."
            return
        }
        Empresa empresa = empresas[empIdx]

        List<Vaga> vagasDaEmpresa = vagas.findAll { it.empresa == empresa }
        if (vagasDaEmpresa.isEmpty()) {
            println "Essa empresa não possui vagas cadastradas."
            return
        }

        println "\nEscolha uma vaga que pertence a ${empresa.nome} (pelo índice):"
        vagasDaEmpresa.eachWithIndex { vg, idx ->
            println "[${idx}] ${vg.titulo} (ID: ${vg.id})"
        }
        int vagaIdx = scanner.nextLine().toInteger()
        if (vagaIdx < 0 || vagaIdx >= vagasDaEmpresa.size()) {
            println "Índice inválido."
            return
        }
        Vaga vagaEscolhida = vagasDaEmpresa[vagaIdx]

        println "\nEscolha um candidato para curtir (pelo índice):"
        candidatos.eachWithIndex { cand, idx ->
            println "[${idx}] ${cand.nome}"
        }
        int candIdx = scanner.nextLine().toInteger()
        if (candIdx < 0 || candIdx >= candidatos.size()) {
            println "Índice inválido."
            return
        }
        Candidato candidato = candidatos[candIdx]

        Curtida curtidaExistente = curtidas.find { it.candidato == candidato && it.vaga == vagaEscolhida }
        if (!curtidaExistente) {
            Curtida novaCurtida = new Curtida(candidato, vagaEscolhida)
            novaCurtida.empresaCurtiu = true
            curtidas << novaCurtida

            println "Empresa ${empresa.nome} curtiu o candidato ${candidato.nome} na vaga '${vagaEscolhida.titulo}'."
        } else {
            curtidaExistente.empresaCurtiu = true
            println "Empresa ${empresa.nome} curtiu o candidato ${candidato.nome} (vaga: '${vagaEscolhida.titulo}')."
            if (curtidaExistente.isMatch()) {
                println ">>> MATCH REALIZADO! <<<"
            }
        }
    }

    // -------------------------------------------------------
    // 8) EXIBIR CURTIDAS
    // -------------------------------------------------------
    static void exibirCurtidas() {
        println "\n--- LISTA DE CURTIDAS ---"
        if (curtidas.isEmpty()) {
            println "Nenhuma curtida registrada ainda."
        } else {
            curtidas.each { println it }
        }
    }

    // -------------------------------------------------------
    // 10) CADASTRAR VAGA
    // -------------------------------------------------------
    static void cadastrarVaga(Scanner scanner) {
        println "\n--- CADASTRO DE VAGA ---"
        if (empresas.isEmpty()) {
            println "Não há empresas cadastradas, impossível criar vaga."
            return
        }

        print "Título da vaga: "
        String titulo = scanner.nextLine()

        println "Escolha a empresa proprietária desta vaga (pelo índice):"
        empresas.eachWithIndex { emp, idx ->
            println "[${idx}] ${emp.nome}"
        }
        int empIdx = scanner.nextLine().toInteger()
        if (empIdx < 0 || empIdx >= empresas.size()) {
            println "Índice inválido."
            return
        }
        Empresa empresaDona = empresas[empIdx]

        print "Competências requeridas (separe por vírgula): "
        String comps = scanner.nextLine()
        List<String> competencias = comps.split(",")*.trim()

        // ID da vaga será gerado pelo banco. Então a princípio = null ou 0
        Vaga novaVaga = new Vaga(null, titulo, empresaDona, competencias)

        // Salva no banco
        LinketinderDAO.inserirVaga(novaVaga)


        // Adiciona na lista local
        vagas << novaVaga

        println "Vaga cadastrada com sucesso!"
    }
}
