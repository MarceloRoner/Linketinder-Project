class LinketinderApp {

    static List<Candidato> candidatos = []
    static List<Empresa> empresas = []

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
            println "5) Sair"
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
}
