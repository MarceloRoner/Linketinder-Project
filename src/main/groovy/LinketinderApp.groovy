import java.util.Scanner

class LinketinderApp {

    static List<Candidato> candidatos = []
    static List<Empresa> empresas = []

    static List<Vaga> vagas = []
    static List<Curtida> curtidas = []

    static void main(String[] args) {
        println "Projeto Linketinder - Desenvolvido por Marcelo (exemplo)"

        candidatos << new Candidato("João Silva", "joao@example.com", "123.456.789-00", 30, "SP", "01000-000", "Desenvolvedor FullStack", ["Java", "Groovy", "Angular"])
        candidatos << new Candidato("Maria Souza", "maria@example.com", "987.654.321-00", 25, "RJ", "20000-000", "QA Engineer", ["Testes Automatizados", "Selenium", "Java"])
        candidatos << new Candidato("Ana Paula", "ana@example.com", "111.222.333-44", 29, "MG", "30000-000", "DevOps", ["Docker", "Kubernetes", "Jenkins"])
        candidatos << new Candidato("Carlos Oliveira", "carlos@example.com", "555.666.777-88", 35, "BA", "40000-000", "Front-end Developer", ["HTML", "CSS", "JavaScript"])
        candidatos << new Candidato("Mariana Torres", "mariana@example.com", "999.888.777-66", 22, "RS", "90000-000", "Back-end Jr", ["Java", "Spring", "MySQL"])

        empresas << new Empresa("Arroz-Gostoso", "rh@arrozgostoso.com", "12.345.678/0001-00", "Brasil", "SP", "01000-111", "Empresa do ramo alimentício", ["Java", "Groovy", "Banco de Dados"])
        empresas << new Empresa("Império do Boliche", "contato@imperiodoboliche.com", "98.765.432/0001-00", "Brasil", "RJ", "20000-222", "Empresa de entretenimento", ["Atendimento", "Marketing"])
        empresas << new Empresa("Tech Solutions", "careers@techsolutions.com", "11.111.111/0001-11", "Brasil", "MG", "30000-333", "Consultoria em TI", ["DevOps", "Cloud", "CI/CD"])
        empresas << new Empresa("Digital Vision", "jobs@digitalvision.com", "22.222.222/0001-22", "Brasil", "BA", "40000-444", "Marketing Digital", ["Design", "SEO", "HTML/CSS"])
        empresas << new Empresa("SoftHouse", "hr@softhouse.com", "33.333.333/0001-33", "Brasil", "RS", "90000-555", "Desenvolvimento de Sistemas", ["Java", "Spring", "Arquitetura de Software"])

        def arrozGostoso = empresas[0]  // "Arroz-Gostoso"
        def imperioBoliche = empresas[1] // "Império do Boliche"
        vagas << new Vaga(1, "Desenvolvedor Java", arrozGostoso, ["Java", "Groovy"])
        vagas << new Vaga(2, "Atendente de Pista", imperioBoliche, ["Atendimento", "Marketing"])

        exibirMenu()
    }

    static void exibirMenu() {
        def scanner = new Scanner(System.in)
        while(true) {
            println "\n--- LINKETINDER MENU ---"
            println "1) Listar todos os candidatos"
            println "2) Listar todas as empresas"
            println "3) Cadastrar novo candidato (opcional)"
            println "4) Cadastrar nova empresa (opcional)"
            println "5) Listar vagas"
            println "6) Candidato curte vaga"
            println "7) Empresa curte candidato"
            println "8) Exibir curtidas"
            println "9) Sair"
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
                case "9":
                    println "Saindo do sistema..."
                    System.exit(0)
                    break
                default:
                    println "Opção inválida. Tente novamente."
            }
        }
    }


    static void listarCandidatos() {
        println "\n--- LISTA DE CANDIDATOS ---"
        candidatos.each { println it }
    }

    static void listarEmpresas() {
        println "\n--- LISTA DE EMPRESAS ---"
        empresas.each { println it }
    }

    static void listarVagas() {
        println "\n--- LISTA DE VAGAS ---"
        vagas.each { println it }
    }

    static void adicionarCandidato(Candidato candidato) {
        candidatos << candidato
    }

    static void adicionarEmpresa(Empresa empresa) {
        empresas << empresa
    }
    static void exibirCurtidas() {
        println "\n--- LISTA DE CURTIDAS ---"
        if (curtidas.isEmpty()) {
            println "Nenhuma curtida registrada ainda."
        } else {
            curtidas.each { println it }
        }
    }


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
        candidatos << novo

        println "Candidato cadastrado com sucesso!"
    }

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
        empresas << nova

        println "Empresa cadastrada com sucesso!"
    }


    static void candidatoCurteVaga(Scanner scanner) {
        println "\nEscolha um candidato (pelo índice):"
        candidatos.eachWithIndex { cand, idx ->
            println "[${idx}] ${cand.nome}"
        }
        int candIdx = scanner.nextLine().toInteger()
        Candidato candidato = candidatos[candIdx]

        println "\nEscolha uma vaga (pelo índice):"
        vagas.eachWithIndex { vg, idx ->
            println "[${idx}] ${vg.titulo} (Empresa: ${vg.empresa.nome})"
        }
        int vagaIdx = scanner.nextLine().toInteger()
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

    static void empresaCurteCandidato(Scanner scanner) {
        println "\nEscolha uma empresa (pelo índice):"
        empresas.eachWithIndex { emp, idx ->
            println "[${idx}] ${emp.nome}"
        }
        int empIdx = scanner.nextLine().toInteger()
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
        Vaga vagaEscolhida = vagasDaEmpresa[vagaIdx]

        println "\nEscolha um candidato para curtir (pelo índice):"
        candidatos.eachWithIndex { cand, idx ->
            println "[${idx}] ${cand.nome}"
        }
        int candIdx = scanner.nextLine().toInteger()
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
}
