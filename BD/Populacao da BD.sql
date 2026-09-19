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

insert into etapa (caso_id,numero,titulo,descricao)
values
(1,1,'A NOITE DO CRIME','Objetivo: descobrir o que aconteceu naquela noite e determinar a janela provável do assassinato.
Situação inicial: Belcio é encontrado morto às 22h47 no escritório da mansão. O jogador recebe a descrição da cena, informações básicas dos envolvidos e os primeiros registros de horário.
Investigação: o jogador deve organizar os acontecimentos cronologicamente e determinar que a morte ocorreu aproximadamente entre 22h15 e 22h30.'),
(1,2,'AS MENTIRAS','Objetivo: investigar os depoimentos dos suspeitos e encontrar contradições.
Depoimentos iniciais: Ivany afirma que foi para o quarto às 22h00; Andrea afirma que estava no jardim; Junior afirma que ficou trabalhando no escritório; Clarson afirma que estava na sala de segurança; Gercia afirma que estava na biblioteca; Antonio afirma que estava na cozinha.
Descoberta: a câmera do corredor apresenta uma interrupção entre 22h17 e 22h19. Pouco depois, uma pessoa aparece saindo da direção do escritório, mas a imagem não permite identificar claramente o rosto.'),
(1,3,'O ARQUIVO OCULTO','Objetivo: investigar o computador de Belcio e descobrir o que ele investigava antes de morrer.
Descoberta: o jogador encontra um arquivo protegido relacionado a operações financeiras da empresa. Os dados revelam transferências suspeitas e uma sequência de operações associadas a Junior Guambe.
Junior passa a ser o principal suspeito. Porém, a investigação deve levantar a pergunta: Junior cometeu um crime, mas isso significa que ele matou Belcio?'),
(1,4,'O PASSADO DE ERIC','Objetivo: cruzar câmeras, cartões, localização, computadores e horários para descobrir quem teve oportunidade real de chegar ao escritório.
Descoberta: o sistema registra um acesso ao escritório às 22h18, mas não existe uma entrada correspondente pelo corredor principal.
O jogador descobre uma porta de serviço que conecta a área da cozinha ao corredor secundário. Antonio possui acesso legítimo a essa área.
Nova contradição: Antonio afirmou que permaneceu na cozinha, mas os registros mostram que ela deixou a área durante a janela relevante.
Uma operação do sistema de segurança aparece associada a credenciais administrativas de Clarson. A investigação revela que Clarson realizava uma manutenção programada, evitando que ele seja tratado automaticamente como culpado.'),
(1,5,'A ULTIMA ASSOCIACAO','Objetivo: descobrir o motivo do crime, reconstruir a sequência final e apresentar a acusação definitiva.
A descoberta final: o jogador desbloqueia um arquivo relacionado a Eric, irmão de Antonio.
Eric trabalhava na Nexus Systems. Ele descobriu irregularidades, tentou denunciá-las e posteriormente foi acusado de roubar informações da empresa. Sua carreira foi destruída.
Antonio acreditava que Belcio era responsável pelo que aconteceu com o irmão.
PLOT: a investigação revela que Belcio não era responsável pela destruição de Eric.  Junior Guambe estava por trás das irregularidades financeiras. Belcio havia descoberto a verdade e pretendia entregar provas contra Junior.
Antonio, porém, não sabia disso. Convencido de que Belcio havia destruído a vida do irmão, foi confrontá-lo. Durante a discussão, Antonio matou Belcio.
Depois percebeu o que havia feito e tentou encobrir o crime, manipulando registros e construindo um álibi.');

insert into suspeito(pessoa_id,caso_id,motivo) values
(2,1,'E responsável por uma fraude financeira através de empresas fictícias.'),
(3,1,'Seu irmao foi preso trabalhando na Nexus Systems'),
(5,1,null),
(6,1,'Herdaria uma grande parte da fortuna, descobriu que belcio pretendia alterar o testamento'),
(7,1,'Possui dividas e roubou dinheiro do Pai');



