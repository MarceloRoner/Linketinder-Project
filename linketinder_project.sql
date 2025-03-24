CREATE TABLE candidato (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  sobrenome VARCHAR(100),
  data_nascimento DATE,
  email VARCHAR(150) NOT NULL,
  cpf VARCHAR(14),
  pais VARCHAR(60),
  estado VARCHAR(60),
  cep VARCHAR(10),
  descricao TEXT,
  senha VARCHAR(100) NOT NULL
);

CREATE TABLE empresa (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cnpj VARCHAR(18),
  email VARCHAR(150) NOT NULL,
  pais VARCHAR(60),
  estado VARCHAR(60),
  cep VARCHAR(10),
  descricao TEXT,
  senha VARCHAR(100) NOT NULL
);

CREATE TABLE competencia (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL
);

CREATE TABLE vaga (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  descricao TEXT,
  local VARCHAR(100),
  id_empresa INT NOT NULL
);

CREATE TABLE candidato_competencia (
  id_candidato INT NOT NULL,
  id_competencia INT NOT NULL,
  PRIMARY KEY (id_candidato, id_competencia)
);

CREATE TABLE vaga_competencia (
  id_vaga INT NOT NULL,
  id_competencia INT NOT NULL,
  PRIMARY KEY (id_vaga, id_competencia)
);

CREATE TABLE empresa_competencia (
  id_empresa INT NOT NULL,
  id_competencia INT NOT NULL,
  PRIMARY KEY (id_empresa, id_competencia)
);

CREATE TABLE curtida (
  id_candidato INT NOT NULL,
  id_vaga INT NOT NULL,
  candidato_curtiu BOOLEAN DEFAULT false,
  empresa_curtiu BOOLEAN DEFAULT false,
  PRIMARY KEY (id_candidato, id_vaga)
);

ALTER TABLE vaga
  ADD FOREIGN KEY (id_empresa) REFERENCES empresa (id);

ALTER TABLE candidato_competencia
  ADD FOREIGN KEY (id_candidato) REFERENCES candidato (id);

ALTER TABLE candidato_competencia
  ADD FOREIGN KEY (id_competencia) REFERENCES competencia (id);

ALTER TABLE vaga_competencia
  ADD FOREIGN KEY (id_vaga) REFERENCES vaga (id);

ALTER TABLE vaga_competencia
  ADD FOREIGN KEY (id_competencia) REFERENCES competencia (id);

ALTER TABLE empresa_competencia
  ADD FOREIGN KEY (id_empresa) REFERENCES empresa (id);

ALTER TABLE empresa_competencia
  ADD FOREIGN KEY (id_competencia) REFERENCES competencia (id);

ALTER TABLE curtida
  ADD FOREIGN KEY (id_candidato) REFERENCES candidato (id);

ALTER TABLE curtida
  ADD FOREIGN KEY (id_vaga) REFERENCES vaga (id);

INSERT INTO candidato 
(nome, sobrenome, data_nascimento, email, cpf, pais, estado, cep, descricao, senha)
VALUES
('João', 'Silva', '1990-05-10', 'joao@example.com', '12345678900', 'Brasil', 'SP', '01000-000', 'Desenvolvedor Full Stack', 'abcd1234'),
('Maria', 'Souza', '1995-07-22', 'maria@example.com', '98765432100', 'Brasil', 'RJ', '20000-111', 'QA Engineer', 'maria123'),
('Ana', 'Oliveira', '1992-03-15', 'ana@example.com', '11122233344', 'Brasil', 'MG', '30000-222', 'DevOps Specialist', 'ana321'),
('Carlos', 'Pereira', '1988-01-30', 'carlos@example.com', '55566677788', 'Brasil', 'BA', '40000-333', 'Front-end Developer', 'carl456'),
('Mariana', 'Torres', '1998-09-18', 'mariana@example.com', '99988877766', 'Brasil', 'RS', '90000-444', 'Back-end Jr', 'mary789');

INSERT INTO empresa
(nome, cnpj, email, pais, estado, cep, descricao, senha)
VALUES
('Arroz-Gostoso', '12345678000100', 'rh@arrozgostoso.com', 'Brasil', 'SP', '01000-555', 'Empresa do ramo alimentício', 'arroz123'),
('Império do Boliche', '98765432000111', 'contato@imperiodoboliche.com', 'Brasil', 'RJ', '20000-666', 'Empresa de entretenimento e boliches', 'boliche456'),
('Tech Solutions', '11111111000122', 'careers@techsolutions.com', 'Brasil', 'MG', '30000-777', 'Consultoria em TI', 'tech789'),
('Digital Vision', '22222222000133', 'jobs@digitalvision.com', 'Brasil', 'BA', '40000-888', 'Marketing Digital', 'digi101'),
('SoftHouse', '33333333000144', 'hr@softhouse.com', 'Brasil', 'RS', '90000-999', 'Desenvolvimento de Sistemas', 'soft202');
