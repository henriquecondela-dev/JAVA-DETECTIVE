use javaDetective;
insert into pessoa (nome, idade,genero, descricao) 
values 
('Belcio Nexus', 48,'M','Vitima do caso'),
('Junior Guambe',50,'M','Socio do Belcio'),
('Antonio Brabus', 25,'M','Assistente Pessoal Do Belcio'),
('Eric Brabus', 30,'M','irmao de antonio(MORTO)'),
('Clarson Felix',35,'M','Seguranca'),
('Ivany Nexus' ,45,'F','Esposa da vitima'),
('Andrea Nexus',22,'F','Filha do Belcio'),
('Gercia Quive',27,'F', 'Jornalista'),
('Ana Joice',43,'F','Governanta');

insert into caso (titulo,descricao,vitima_id)
values 
('Caso Nexus','O caso NEXUS é uma investigação criminal envolvendo a morte de Belcio Nexus , fundador da Nexus Systems, encontrado morto no seu escritório durante um jantar privado na sua mansão em Maputo. 
Sem sinais evidentes de arrombamento, com o cofre aberto e indícios de manipulação no computador da vítima, o caso apresenta diversos suspeitos com motivos, segredos e versões diferentes sobre os acontecimentos daquela noite.
O investigador deverá analisar evidências, consultar registos, comparar horários, verificar álibis e reconstruir a sequência dos acontecimentos para descobrir quem é responsável pelo crime, como ele foi cometido e qual foi o verdadeiro motivo.
',1);

insert into etapa (caso_id,numero,titulo,descricao, minimo_evidencias)
values
(1,1,'A NOITE DO CRIME','Situação inicial: Belcio é encontrado morto às 22h47 no escritório da mansão. O jogador recebe a descrição da cena, informações básicas dos envolvidos e os primeiros registros de horário.
Investigação: o jogador deve organizar os acontecimentos cronologicamente e determinar que a morte ocorreu aproximadamente entre 22h15 e 22h30.', 2),
(1,2,'AS MENTIRAS','Depoimentos iniciais: Ivany afirma que foi para o quarto às 22h00; Andrea afirma que estava no jardim; Junior afirma que ficou trabalhando no escritório; Clarson afirma que estava na sala de segurança; Gercia afirma que estava na biblioteca; Antonio afirma que estava na cozinha.
Descoberta: a câmera do corredor apresenta uma interrupção entre 22h17 e 22h19. Pouco depois, uma pessoa aparece saindo da direção do escritório, mas a imagem não permite identificar claramente o rosto.', 2),
(1,3,'O ARQUIVO OCULTO','Descoberta: o jogador encontra um arquivo protegido relacionado a operações financeiras da empresa. Os dados revelam transferências suspeitas e uma sequência de operações associadas a Junior Guambe.
Junior passa a ser o principal suspeito. Porém, a investigação deve levantar a pergunta: Junior cometeu um crime, mas isso significa que ele matou Belcio?', 2),
(1,4,'O PASSADO DE ERIC','Descoberta: o sistema registra um acesso ao escritório às 22h18, mas não existe uma entrada correspondente pelo corredor principal.
O jogador descobre uma porta de serviço que conecta a área da cozinha ao corredor secundário. Antonio possui acesso legítimo a essa área.
Nova contradição: Antonio afirmou que permaneceu na cozinha, mas os registros mostram que ela deixou a área durante a janela relevante.
Uma operação do sistema de segurança aparece associada a credenciais administrativas de Clarson. A investigação revela que Clarson realizava uma manutenção programada, evitando que ele seja tratado automaticamente como culpado.', 1),
(1,5,'A ULTIMA ASSOCIACAO','A descoberta final: o jogador desbloqueia um arquivo relacionado a Eric, irmão de Antonio.
Eric trabalhava na Nexus Systems. Ele descobriu irregularidades, tentou denunciá-las e posteriormente foi acusado de roubar informações da empresa. Sua carreira foi destruída.
Antonio acreditava que Belcio era responsável pelo que aconteceu com o irmão.
PLOT: a investigação revela que Belcio não era responsável pela destruição de Eric.  Junior Guambe estava por trás das irregularidades financeiras. Belcio havia descoberto a verdade e pretendia entregar provas contra Junior.
Antonio, porém, não sabia disso. Convencido de que Belcio havia destruído a vida do irmão, foi confrontá-lo. Durante a discussão, Antonio matou Belcio.
Depois percebeu o que havia feito e tentou encobrir o crime, manipulando registros e construindo um álibi.', 1);

insert into objetivo(etapa_id, descricao) values
(1, 'Descobrir o que aconteceu naquela noite e determinar a janela provável do assassinato.'),
(2, 'Investigar os depoimentos dos suspeitos e encontrar contradições.'),
(3, 'Investigar o computador de Belcio e descobrir o que ele investigava antes de morrer.'),
(4, 'Cruzar câmeras, cartões, localização, computadores e horários para descobrir quem teve oportunidade real de chegar ao escritório.'),
(5, 'Descobrir o motivo do crime, reconstruir a sequência final e apresentar a acusação definitiva.');

insert into suspeito(pessoa_id,caso_id,motivo) values
(2,1,'E responsável por uma fraude financeira através de empresas fictícias.'),
(3,1,'Seu irmao foi preso trabalhando na Nexus Systems'),
(5,1,'Afirma que a interrupção das câmaras foi uma falha automática.'),
(6,1,'Herdaria uma grande parte da fortuna, descobriu que belcio pretendia alterar o testamento'),
(7,1,'Possui dividas e roubou dinheiro do Pai'),
(8,1,'Envio uma menssagem ameacadara a vitima');

/*insert into suspeito(pessoa_id,caso_id,motivo) values
(8,1,'Envio uma menssagem ameacadara a vitima');
*/

insert into testemunho(testemunha_id, descricao,data_hora) values
(9,'A governanta informa que Antonio deixou a cosinha por volta das 22:10','2026-09-26 08:13:00');

insert into caso_testemunha(caso_id,testemunho_id)values
(1,1);

insert into localizacao(nome) values
('Escritorio da nexus'),
('Escritorio de Henrique'),
('Cozinha'),
('Biblioteca'),
('Sala Principal'),
('Jardim'),
('Corredor Principal'),
('Corredor de Servico'),
('Sala de Seguranca'),
('Garagem'),
('Entrada Principal'),
('Area de Servico'),
('Quarto');

insert into evento(descricao,data_hora,local_id)values
('O computador de Junior e desligado durante a noite','2026-09-25 22:03:00',2),
('Antonio deixa a cozinha','2026-09-25 22:10:00',3),
('o sistem de seguranca sofre uma interrupcao','2026-09-25 22:17:00',9),
('E regstrado um acesso ao escritorio sem entrada pelo corredor principal','2026-09-25 22:18:00',1),
('Comeca o confronto entre o Asssasino e Belcio','2026-09-25 22:20:00',1),
('Belcio E morto','2026-09-25 22:25:00',1),
('O Assasino comeca a manipular os registrso para encobrir o crime','2026-09-25 22:28:00',1),
('o antonio chega a cozinha','2026-09-25 22:40:00',3),
('Corpo do Belcio e encontrado','2026-09-25 23:00:50',1),
('Clarson realiza uma manutencao programada do sistema de seguranca','2026-09-24 08:00:00',9);

insert into evento_pessoa(evento_id, pessoa_id) values
(1, 2),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(5, 1),
(6, 1),
(6, 3),
(7, 3),
(8, 3),
(9, 9),
(10, 5);

insert into registroDeAcesso(descricao, data_hora, tipo_acesso, local_id, pessoa_id) values
('Entrada ao escritorio sem uso do corredor principal', '2026-09-25 22:17:00', 'ENTRADA', 1, 3),
('Entrada ao escritorio', '2026-09-25 21:00:00', 'ENTRADA', 2, 2),
('Saida ao escritorio', '2026-09-25 22:10:00', 'SAIDA', 2, 2),
('Entrada a cozinha', '2026-09-25 21:00:00', 'ENTRADA', 3, 3),
('Entrada na biblioteca', '2026-09-25 21:45:50', 'ENTRADA', 4, 8),
('Aparicao no jardim', '2026-09-25 22:12:14', 'ENTRADA', 6, 7),
('Saida da casa', '2026-09-25 22:12:11', 'SAIDA', 5, 7),
('Entrada no quarto', '2026-09-25 21:45:00', 'ENTRADA', 13, 6),
('Entrada no escritorio', '2026-09-25 23:00:50', 'ENTRADA', 1, 9),
('Entrada cozinha','2026-09-25 22:40:00', 'ENTRADA', 3, 3);

insert into documento(nome, tipo, conteudo, data_documento) values
('Documentos financeiros', 'FISICO', 'Depois de uma auditoria houve uma confirmacao de desvio de fundos da Nexus Systems da parte do Junior Gambue para uso pessoal.
Durante anos, grandes quantias de dinheiro saíram da Nexus Systems através de empresas aparentemente legítimas.
Mas algumas dessas empresas praticamente não possuem atividade real.
Os documentos apontam para uma operação organizada.
O nome que aparece repetidamente nos registos é:
Junior Guambe.
Junior tinha acesso privilegiado às operações financeiras da empresa.', '2026-09-24 15:03:17'),
('Relatorio da Gercia', 'DIGITAL', 'O Relatorio da Jornalista indicou que o Eric Brabus preso no ano de 2019 trabalhou na Nexus Systems descobriu indicius de fraude. Depois de tentar descobrir o problema foi acusado de tentar roubar informacoes confidenciais e teve a carreira destruida. Tendo sido registado a sua morte de forma misteriosa pouco tempo depois de ter entrado na prisao.', '2021-05-26 13:15:48');

insert into evidencia(titulo, descricao, documento_id, importancia) values
-- Etapa 1
('Cena do crime','Fotografia e relatório do escritório. Revela: Corpo de Moreau; Cofre aberto; Computador; Ausência de arrombamento evidente.', null, 2),
('Relatório preliminar da morte', 'Fornece uma estimativa inicial do horário da morte (22:14:00 - 22:50:00).', null, 3),
('Lista de pessoas presentes', 'Apresenta as pessoas presentes [Ana, Clarson, Belcio, Gercia, Ivany, Junior, Antonio, Andrea].', null, 2),
('Primeiros depoimentos', 'Cada suspeito apresenta a sua primeira versão sobre onde estava (Disponivel em alibes e testemunho).', null, 1),

-- Etapa 2
('Registos de acesso', 'Mostram entradas e saídas de determinadas áreas.(Disponivel em registro de acesso)', null, 3),
('Depoimento da governanta', 'A governanta informa que: Antonio deixou a cozinha por volta das 22h10. Isso contradiz parcialmente o depoimento de Antonio.', null, 2),
('Registo do jardim', 'Mostra Andrea no jardim a partir das 22h12.', null, 3),
('Computador de Junior', 'O computador de Junior foi desligado às: 22h03 Isso também contradiz a afirmação de que ele permaneceu trabalhando durante todo o período.', null, 1),

-- Etapa 3
('Documentos financeiros', 'Demonstram a fraude de Junior.', 1, 3),
('Empresas fictícias', 'Relacionam empresas suspeitas às movimentações financeiras.', 1, 2),
('Anotações de Belcio', 'Demonstram que Belcio estava investigando uma fraude.', null, 2),
('Relatório interno', 'Mostra que Belcio pretendia confrontar ou denunciar o responsável.', null, 2),
('Registo de antigos funcionários', 'Apresenta: Eric Brabus E a sua antiga ligação com a empresa(Nexus Systems).', 2, 3),

-- Etapa 4
('Credenciais de Antonio', 'Mostram que ele possuía acesso privilegiado.', null, 2),
('Logs secundarios', 'Mostram que alguem tentou apagar ou modificar informações.', null, 1),
('Comparacao dos alibis', 'Demonstra que Antonio não esteve na cozinha durante todo o período que declarou.', null, 3),

-- Etapa 5
('Historico de Eric', 'Explica: Trabalho na Nexus Systems; Descoberta de irregularidades; Acusacao; Destruição da carreira.', 2, 3);


insert into etapa_evidencia (etapa_id, evidencia_id) values 
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(4, 14),
(4, 15),
(4, 16),
(5, 17);

insert into alibi (suspeito_id, descricao, inicio, fim) values
(1, 'Afirma que passou a noite trabalhando no escritório.', '2026-09-25 21:00:00', '2026-09-25 23:00:00'),
(2, ' Afirma que ficou na cozinha ajudando a governanta.', '2026-09-25 21:00:00', '2026-09-25 23:02:00'),
(3, 'Afirma que esteve na garragem controlando os carros durante toda a festa.', '2026-09-25 19:00:00', '2026-09-25 23:01:43'),
(4, 'Afirma que foi para o quarto depois do jantar.', '2026-09-25 21:45:00', '2026-09-25 23:01:10'),
(5, 'Diz que esteve no jardim fazendo uma chamada.', '2026-09-25 22:12:14', '2026-09-25 23:01:10'),
(6, 'Afirma que foi ao jantar para conversar com Moreau.', '2026-09-25 21:45:50', '2026-09-25 23:05:13');

insert into mensagem(conteudo) values
('(Maputo, 25 de Dezembro 2026 - 23h00)

A noite começou como um simples jantar privado na mansão de Belcio Nexus.
Entre os convidados estavam familiares, funcionários, um sócio empresarial e uma jornalista. Todos tinham algum tipo de relação com Nexus, embora nem todos parecessem gostar dele.
Durante o jantar, Belcio mostrou-se inquieto. Evitou falar diretamente sobre os negócios da empresa, mas deixou uma frase que chamou a atenção:

>>Antes desta noite terminar, algumas pessoas vão ter de explicar certas coisas.<<
Depois do jantar, os convidados começaram a dispersar pela casa.
Às 23h00, uma funcionária encontrou Belcio Nexus morto no seu escritório.
A porta não apresenta sinais evidentes de arrombamento.
O cofre está aberto.
O computador de Nexus apresenta sinais de que os dados foram apagados ou alterados.
Ninguém sabe ainda exatamente quando Nexus morreu.
O investigador é chamado para analisar a cena e determinar o que aconteceu.
'),
('A investigação inicial revelou que o crime não pode ser explicado apenas pela cena encontrada no escritório.
As primeiras declarações começam a apresentar problemas.(Declaracoes apresentadas na tabela alibi).
Quando os registos de acesso são comparados com os depoimentos, aparecem pequenas diferenças.(Registros apresentados na tabela registroDeAcesso).
'),
('Análise dos computadores e documentos financeiros. Veja os documentos'),
('Analise os alibis abaixo'),
('Anos antes, Eric trabalhava na Nexus Systems.
Durante o seu trabalho, descobriu irregularidades dentro da empresa e tentou denunciar o problema.
Pouco depois, Eric foi acusado de roubar informações confidenciais.
A acusação destruiu a sua carreira.
Ele deixou a empresa e desapareceu da vida profissional.
Para a família Brabus, entretanto, a história era diferente.
Antonio nunca acreditou que o irmão fosse culpado.
Durante anos, ele acreditou que Belcio Nexus tinha sido responsável pela destruição da vida de Eric.
Agora surge uma informação perturbadora.
Os documentos recuperados mostram que Nexus não estava por trás da fraude.
Na verdade, ele estava tentando descobrir quem realmente a cometia.
Mais detalhes podes ver no Relatório abaixo.');

insert into etapa_mensagem (etapa_id, mensagem_id) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);



INSERT INTO desafioDeProgramacao (titulo, caso_id, descricao, etapa_id, classe_esperada, metodo_esperado)VALUES

('Reconstrução da Noite',1,'O sistema de segurança registou diferentes horarios durante a noite do crime. A investigacao determinou que o assassinato ocorreu entre as 22h15 e as 22h30.Crie uma classe ReconstrucaoNoite com um metodo que receba uma hora e os minutos correspondentes. O metodo deve verificar se o horario recebido pertence ao intervalo entre 22h15 e 22h30, incluindo os limites.',1,'ReconstrucaoNoite','boolean estaNaJanelaDoCrime(int hora, int minuto)'),

('Necessidade de organizacao dos depoimentos dos suspeitos',1,'. Crie uma classe AnaliseDepoimentos com os atributos nomeSuspeito, localDeclarado e horarioDeclarado. Implemente um metodo que receba o horario declarado por um suspeito e o horario registado pelas evidencias. O metodo deve verificar se os dois horarios sao diferentes, indicando uma possivel contradicao. A comparacao deve ignorar diferencas entre maiusculas e minusculas e espacos desnecessarios.',2,'AnaliseDepoimentos','boolean verificarContradicao(String horarioDeclarado, String horarioRegistado)'),

('As Transferências Suspeitas',1,'Foram encontradas varias transferencias financeiras suspeitas no computador de Belcio. Crie uma classe InvestigacaoFinanceira e implemente um metodo que receba um array de valores monetarios e calcule quantas transferencias possuem valor superior a 100.000.', 3,'InvestigacaoFinanceira','int contarTransferenciasSuspeitas(double[] transferencias)'),

('O Acesso ao Escritorio',1,'O sistema de seguranca possui uma lista de horarios de acesso ao escritorio. Crie uma classe AnaliseAcessos que receba uma lista de horarios e permita verificar se determinado horario esta registado. Utilize ArrayList e implemente a pesquisa ignorando diferencas de formatacao entre horas com e sem zero inicial.',4, 'AnaliseAcessos','boolean existeRegisto(ArrayList<String> horarios, String horarioProcurado)'),

('A Acusação Definitiva',1,'Crie uma classe AcusacaoFinal com um atributo privado para guardar o nome do suspeito acusado. Implemente um construtor para inicializar esse atributo e um metodo que verifique se o primeiro nome do suspeito orresponde ao nome do verdadeiro culpado, ignorando diferencas entre maiusculas e minusculas. ',5,'AcusacaoFinal','boolean verificarCulpado()');
