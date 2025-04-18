import java.time.LocalDate
import java.util.Scanner

import domain.Candidato
import domain.Empresa
import domain.Vaga
import domain.Curtida

import service.CandidatoService
import service.EmpresaService
import service.VagaService
import service.CompetenciaService
import service.CurtidaService

class LinketinderApp {

    static final CandidatoService candidatoService = new CandidatoService()
    static final EmpresaService empresaService = new EmpresaService()
    static final VagaService vagaService = new VagaService()
    static final CompetenciaService competenciaService = new CompetenciaService()
    static final CurtidaService curtidaService = new CurtidaService()

    static void main(String[] args) {
        println "Projeto Linketinder - Desenvolvido por Marcelo (Refatorado)"
        exibirMenu()
    }

    static void exibirMenu() {
        def scanner = new Scanner(System.in)
        while (true) {
            println "\n--- LINKETINDER MENU ---"
            println "1) Listar todos os candidatos"
            println "2) Listar todas as empresas"
            println "3) Cadastrar novo candidato"
            println "4) Cadastrar nova empresa"
            println "5) Listar vagas"
            println "6) Candidato curte vaga"
            println "7) Empresa curte candidato"
            println "8) Exibir curtidas"
            println "9) Cadastrar nova vaga"
            println "10) Listar competências"
            println "11) Excluir competência"
            println "12) Atualizar nome de competência"
            println "13) Excluir candidato"
            println "14) Excluir empresa"
            println "15) Excluir vaga"
            println "16) Atualizar candidato"
            println "17) Atualizar empresa"
            println "18) Atualizar vaga"
            println "19) Sair"
            print "Escolha uma opção: "

            switch (scanner.nextLine()) {
                case "1": listarCandidatos(); break
                case "2": listarEmpresas(); break
                case "3": cadastrarCandidato(scanner); break
                case "4": cadastrarEmpresa(scanner); break
                case "5": listarVagas(); break
                case "6": candidatoCurteVaga(scanner); break
                case "7": empresaCurteCandidato(scanner); break
                case "8": exibirCurtidas(); break
                case "9": cadastrarVaga(scanner); break
                case "10": listarCompetencias(); break
                case "11": excluirCompetencia(scanner); break
                case "12": atualizarCompetencia(scanner); break
                case "13": excluirCandidato(scanner); break
                case "14": excluirEmpresa(scanner); break
                case "15": excluirVaga(scanner); break
                case "16": atualizarCandidato(scanner); break
                case "17": atualizarEmpresa(scanner); break
                case "18": atualizarVaga(scanner); break
                case "19": println "Saindo..."; return
                default: println "Opção inválida."
            }
        }
    }

    static void listarCandidatos() {
        println "\n--- LISTA DE CANDIDATOS ---"
        def candidatos = candidatoService.listarTodos()
        candidatos.each { println it }
    }

    static void listarEmpresas() {
        println "\n--- LISTA DE EMPRESAS ---"
        def empresas = empresaService.listarTodas()
        empresas.each { println it }
    }

    static void listarVagas() {
        println "\n--- LISTA DE VAGAS ---"
        def vagas = vagaService.listarTodas()
        vagas.each { println it }
    }

    static void cadastrarCandidato(Scanner scanner) {
        println "\n--- CADASTRO DE CANDIDATO ---"
        print "Nome: "; String nome = scanner.nextLine()
        print "Sobrenome: "; String sobrenome = scanner.nextLine()
        print "Data de Nascimento (AAAA-MM-DD): "; LocalDate dataNasc = LocalDate.parse(scanner.nextLine())
        print "E-mail: "; String email = scanner.nextLine()
        print "CPF: "; String cpf = scanner.nextLine()
        print "Idade: "; int idade = scanner.nextLine().toInteger()
        print "Estado: "; String estado = scanner.nextLine()
        print "CEP: "; String cep = scanner.nextLine()
        print "Descrição pessoal: "; String descricao = scanner.nextLine()
        print "Competências (separe por vírgula): "; List<String> comps = scanner.nextLine().split(",")*.trim()
        print "Senha: "; String senha = scanner.nextLine()

        def novo = new Candidato(nome, sobrenome, dataNasc, email, cpf, idade, "Brasil", estado, cep, descricao, comps, senha)
        candidatoService.cadastrar(novo)
        println "Candidato cadastrado com sucesso!"
    }

    static void cadastrarEmpresa(Scanner scanner) {
        println "\n--- CADASTRO DE EMPRESA ---"
        print "Nome da Empresa: "; String nome = scanner.nextLine()
        print "E-mail Corporativo: "; String email = scanner.nextLine()
        print "CNPJ: "; String cnpj = scanner.nextLine()
        print "País: "; String pais = scanner.nextLine()
        print "Estado: "; String estado = scanner.nextLine()
        print "CEP: "; String cep = scanner.nextLine()
        print "Descrição da empresa: "; String descricao = scanner.nextLine()
        print "Competências esperadas (separe por vírgula): "; List<String> comps = scanner.nextLine().split(",")*.trim()
        print "Senha: "; String senha = scanner.nextLine()

        def nova = new Empresa(nome, email, cnpj, pais, estado, cep, descricao, senha, comps)
        empresaService.cadastrar(nova)
        println "Empresa cadastrada com sucesso!"
    }

    static void cadastrarVaga(Scanner scanner) {
        println "\n--- CADASTRO DE VAGA ---"
        def empresas = empresaService.listarTodas()
        if (empresas.isEmpty()) {
            println "Nenhuma empresa cadastrada."
            return
        }

        print "Título da vaga: "; String titulo = scanner.nextLine()
        println "Escolha a empresa (índice):"
        empresas.eachWithIndex { e, i -> println "[$i] ${e.nome}" }
        int idx = scanner.nextLine().toInteger()
        def empresa = empresas[idx]
        print "Local da vaga: "; String local = scanner.nextLine()
        print "Competências (separe por vírgula): "; List<String> comps = scanner.nextLine().split(",")*.trim()

        def vaga = new Vaga(null, titulo, empresa, comps, local)
        vagaService.cadastrar(vaga)
        println "Vaga cadastrada com sucesso!"
    }

    static void candidatoCurteVaga(Scanner scanner) {
        def candidatos = candidatoService.listarTodos()
        def vagas = vagaService.listarTodas()

        if (candidatos.isEmpty() || vagas.isEmpty()) {
            println "Candidatos ou vagas não disponíveis."
            return
        }

        println "Escolha um candidato:"
        candidatos.eachWithIndex { c, i -> println "[$i] ${c.nome}" }
        def cand = candidatos[scanner.nextLine().toInteger()]

        println "Escolha uma vaga:"
        vagas.eachWithIndex { v, i -> println "[$i] ${v.titulo}" }
        def vaga = vagas[scanner.nextLine().toInteger()]

        curtidaService.curtirPorCandidato(cand, vaga)
    }

    static void empresaCurteCandidato(Scanner scanner) {
        def empresas = empresaService.listarTodas()
        def vagas = vagaService.listarTodas()
        def candidatos = candidatoService.listarTodos()

        if (empresas.isEmpty() || vagas.isEmpty() || candidatos.isEmpty()) {
            println "Dados insuficientes para curtir."
            return
        }

        println "Escolha uma empresa:"
        empresas.eachWithIndex { e, i -> println "[$i] ${e.nome}" }
        def empresa = empresas[scanner.nextLine().toInteger()]

        def vagasDaEmpresa = vagaService.listarPorEmpresa(empresa.id)
        if (vagasDaEmpresa.isEmpty()) {
            println "Essa empresa não tem vagas."
            return
        }

        println "Escolha uma vaga da empresa:"
        vagasDaEmpresa.eachWithIndex { v, i -> println "[$i] ${v.titulo}" }
        def vaga = vagasDaEmpresa[scanner.nextLine().toInteger()]

        println "Escolha um candidato para curtir:"
        candidatos.eachWithIndex { c, i -> println "[$i] ${c.nome}" }
        def candidato = candidatos[scanner.nextLine().toInteger()]

        curtidaService.curtirPorEmpresa(candidato, vaga)
    }

    static void exibirCurtidas() {
        println "\n--- CURTIDAS ---"
        curtidaService.listarTodas().each { println it }
    }

    static void listarCompetencias() {
        println "\n--- LISTA DE COMPETÊNCIAS ---"
        competenciaService.listarTodas().each { println "- $it" }
    }

    static void excluirCompetencia(Scanner scanner) {
        print "Digite o nome da competência a excluir: "
        competenciaService.excluir(scanner.nextLine())
        println "Competência excluída com sucesso!"
    }

    static void atualizarCompetencia(Scanner scanner) {
        print "Nome atual: "; String atual = scanner.nextLine()
        print "Novo nome: "; String novo = scanner.nextLine()
        competenciaService.atualizar(atual, novo)
        println "Competência atualizada com sucesso!"
    }

    static void excluirCandidato(Scanner scanner) {
        listarCandidatos()
        print "Digite o ID do candidato: "
        int id = scanner.nextLine().toInteger()
        candidatoService.excluir(id)
        println "Candidato excluído com sucesso!"
    }

    static void excluirEmpresa(Scanner scanner) {
        listarEmpresas()
        print "Digite o ID da empresa: "
        int id = scanner.nextLine().toInteger()
        empresaService.excluir(id)
        println "Empresa excluída com sucesso!"
    }

    static void excluirVaga(Scanner scanner) {
        listarVagas()
        print "Digite o ID da vaga: "
        int id = scanner.nextLine().toInteger()
        vagaService.excluir(id)
        println "Vaga excluída com sucesso!"
    }

    static void atualizarCandidato(Scanner scanner) {
        listarCandidatos()
        print "Digite o ID do candidato: "
        int id = scanner.nextLine().toInteger()
        def candidato = candidatoService.buscarPorId(id)
        if (!candidato) {
            println "Candidato não encontrado."
            return
        }

        print "Novo nome (${candidato.nome}): "; candidato.nome = scanner.nextLine()
        print "Novo sobrenome (${candidato.sobrenome}): "; candidato.sobrenome = scanner.nextLine()
        print "Novo e-mail (${candidato.email}): "; candidato.email = scanner.nextLine()
        print "Novo CPF (${candidato.cpf}): "; candidato.cpf = scanner.nextLine()
        print "Novo país (${candidato.pais}): "; candidato.pais = scanner.nextLine()
        print "Novo estado (${candidato.estado}): "; candidato.estado = scanner.nextLine()
        print "Novo CEP (${candidato.cep}): "; candidato.cep = scanner.nextLine()
        print "Nova descrição: "; candidato.descricao = scanner.nextLine()
        print "Nova senha: "; candidato.senha = scanner.nextLine()

        candidatoService.atualizar(candidato)
        println "Candidato atualizado com sucesso!"
    }

    static void atualizarEmpresa(Scanner scanner) {
        listarEmpresas()
        print "Digite o ID da empresa: "
        int id = scanner.nextLine().toInteger()
        def empresa = empresaService.buscarPorId(id)
        if (!empresa) {
            println "Empresa não encontrada."
            return
        }

        print "Novo nome (${empresa.nome}): "; empresa.nome = scanner.nextLine()
        print "Novo e-mail (${empresa.email}): "; empresa.email = scanner.nextLine()
        print "Novo CNPJ (${empresa.cnpj}): "; empresa.cnpj = scanner.nextLine()
        print "Novo país (${empresa.pais}): "; empresa.pais = scanner.nextLine()
        print "Novo estado (${empresa.estado}): "; empresa.estado = scanner.nextLine()
        print "Novo CEP (${empresa.cep}): "; empresa.cep = scanner.nextLine()
        print "Nova descrição: "; empresa.descricao = scanner.nextLine()
        print "Nova senha: "; empresa.senha = scanner.nextLine()

        empresaService.atualizar(empresa)
        println "Empresa atualizada com sucesso!"
    }

    static void atualizarVaga(Scanner scanner) {
        listarVagas()
        print "Digite o ID da vaga: "
        int id = scanner.nextLine().toInteger()
        def vaga = vagaService.buscarPorId(id)
        if (!vaga) {
            println "Vaga não encontrada."
            return
        }

        print "Novo título (${vaga.titulo}): "; vaga.titulo = scanner.nextLine()
        print "Novo local (${vaga.local}): "; vaga.local = scanner.nextLine()
        print "Competências (separe por vírgula): "; vaga.competenciasRequeridas = scanner.nextLine().split(",")*.trim()

        vagaService.atualizar(vaga)
        println "Vaga atualizada com sucesso!"
    }
}