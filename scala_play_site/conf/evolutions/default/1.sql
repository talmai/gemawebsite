# system schemas
 
# --- !Ups

CREATE TABLE `portfolio_type` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` TINYTEXT NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=MyISAM;

CREATE TABLE `clients` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` TINYTEXT NOT NULL,
	`image` TEXT NOT NULL,
	`enable_main_page_display` TINYINT(1) NOT NULL DEFAULT '0',
	`title` TEXT NULL,
	`description` TEXT NULL,
	`main_page_banner_image` TEXT NULL,
	`main_page_content_line_one` TEXT NULL,
	`main_page_content_line_two` TEXT NULL,
	`main_page_content_line_three` TEXT NULL,
	`main_page_bar_image` TEXT NULL,
	`presentation_order` INT(3) NULL,
	`project_id` INT(3) NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=MyISAM
AUTO_INCREMENT=17;

CREATE TABLE `project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` tinytext NOT NULL,
  `image` text NOT NULL,
  `presentation_order` int(3) NOT NULL,
  `portfolio_type` int(10) NOT NULL,
  `enable_main_page_display` tinyint(1) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `main_page_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `portfolio_type` (`portfolio_type`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE `project_images` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `image` text NOT NULL,
  `project` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `project` (`project`)
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;

CREATE TABLE `user_contact` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` TEXT NOT NULL,
	`email` TEXT NOT NULL,
	`message` TEXT NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `portfolio_type` (`id`, `name`) VALUES
	(1, 'Branding'),
	(2, 'Digital'),
	(3, 'Illustrações'),
	(4, 'Publicidade'),
	(5, 'Todos');

INSERT INTO `clients` (`id`, `name`, `image`, `enable_main_page_display`, `title`, `description`, `main_page_banner_image`, `main_page_content_line_one`, `main_page_content_line_two`, `main_page_content_line_three`, `main_page_bar_image`, `presentation_order`, `project_id`) VALUES
	(1, 'AlumVest', 'clientes__0000_Alum-Vest.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(2, 'Konsep Seni', 'clientes__0002_Konsep-Seni.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(3, 'Raquel Grapiuna', 'clientes__0003_Raquel-Grapiuna.png', 1, '#5cc4b7 || Raquel Grapiuna', '#8c8c8c || Ortodontista', 'raquelgrapiuna_03.png', '#5cc4b7 || Cria\\347\\343o da identidade visual para o', '#00ac96 || consult\\363rio da ortodontista', '#00d0b5 || Raquel Grapiuna', 'raquel.png', 3, 7),
	(4, 'RP Tecnologia', 'clientes__0004_Rptecnologia.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(5, 'GraphShop', 'clientes__0005_Graphshop.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(6, 'FerroPronto', 'clientes__0006_Ferropronto.png', 1, '#ea6b00 || Ferropronto', '#8c8c8c || Corte e dobra de a\\347o', 'ferropronto_03.png', '#ea6b00 || Cri\\347\\343o do mascote e personagens para', '#f48222 || cartilha ilustrada da empresa l\\355der em', '#ff9943 || corte e dobra de a\\347o na Bahia', 'ferropronto.png', 4, 12),
	(7, 'Physioserv', 'clientes__0007_Physioserv.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(12, 'Aline Forjaz', 'clientes__0008_aline_forjaz.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(13, 'Fuzzy', 'clientes__0009_fuzzy.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(14, 'Super Norte', 'clientes__0010_supernorte.png', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(15, 'Delinner', 'clientes__0011_Delinner.png', 1, '#c92474 || D\\351lin\\351er', '#8c8c8c || Fisioterapia e Est\\351tica', 'delineer_03.png', '#a3185b || Projeto de Identidade Visual e', '#c92474 || comunica\\347\\343o para a mais nova empresa de', '#ee0e7b || fisioterapia est\\351tica de Salvador', 'delineer.png', 1, 3),
	(16, 'Vovo Lurdinha', 'clientes__0016_vovoLurdinha.png', 1, '#7c5b35 || Vov\\363 Lurdinha', '#8c8c8c || Culin\\341ria artesanal', 'vovolurdinha_03.png', '#7c5b35 || Cria\\347\\343o da identidade visual para a', '#a87841 || loja de p\\343o de queijo artesanal', '#b6946e || em Curitiba', 'vovolurdinha.png', 2, 10);

INSERT INTO `project` (`id`, `name`, `image`, `presentation_order`, `portfolio_type`, `enable_main_page_display`, `description`, `main_page_description`) VALUES
	(1, 'Ai Meu Santo', 'thumbs_aimeusanto.jpg', 1, 5, 0, 'Marca criada para a confecção de roupas femininas da designer Juliana Grapiuna para atender o público jovem e descolado Os produtos utilizam sempre cores atuais e produtos diferenciados. Para para alinhar a imagem da marca com os produtos, criamos uma tipografia moderna e diferenciada', NULL),
	(2, 'Aline Forjaz', 'thumbs_alineforjaz.jpg', 2, 5, 1, 'Desenvolvemos para a designer de interiores Aline Forjaz toda a sua identidade visual e o blog para a divulgação do seu trabalho.', 'Marca e blog institucional desenvolvido para a designer de interiores Aline Forjaz em São Paulo-SP'),
	(3, 'Délinéer', 'thumbs_delineer.jpg', 3, 5, 0, 'Criamos a identidade visual e toda a comunicação para a mais nova clínica de fisioterapia estética localizada em Salvador-BA. O nosso desafio foi representar a beleza e a transformação oferecida pela clínica sem utilizar elementos visuais comuns. Por isso, escolhemos a tulipa. Onde uma simples semente transforma-se em uma bela e elegante flor.', NULL),
	(4, 'Elemento Básico', 'thumbs_elemento.jpg', 4, 5, 0, 'Fomos convidados para criar a marca e alguns produtos para a confecção brasileira Elemento Básico, que tem como objetivo inspirar-se nos elementos da natureza para criar os seus produtos.', NULL),
	(5, 'Fuzzy', 'thumbs_fuzzy.jpg', 5, 5, 1, 'Durante a pesquisa para criar a identidade visual da Fuzzy, chegamos ao ícone que representa a teoria da lógica fuzzy, onde cada extremidade representa uma resposta. Por também ser utilizada no desenvolvimento de inteligência artificial, o ícone inspira-se na forma orgânica do neurônio, onde foi estilizado até chegar à um resultado visualmente equilibrado.', 'Criação da marca e identidade visual para a empresa Fuzzy engenharia e consultoria em Salvador-BA'),
	(6, 'Graphshop', 'thumbs_graphshop.jpg', 6, 5, 0, 'A empresa surgiu de uma oportunidade e necessidade das gráficas em obter material de qualidade. A Graphshop procupa oferecer o que há de melhor e mais novo para os produtores gráficos além de estar sempre surpreendendo os seus cliente com o atendimento e pós venda.', NULL),
	(7, 'Raquel Grapiuna', 'thumbs_raquelgrapiuna.jpg', 7, 5, 0, 'Criamos a identidade visual para o consultório odontológico da profissional Raquel Grapiuna em Ribeirão Preto-SP. Desenvolvemos a tipografia baseando-se na curvatura do sorriso de seus clientes, representando a satisfação com o serviço.', NULL),
	(8, 'Supernorte', 'thumbs_supernorte.jpg', 8, 5, 1, 'Um atêlier de arte familiar onde são desenvolvidos projetos artesanais para comercialização nacional e internacional. As artistas trabalham com biscuit, velas artesanais, sabonetes entre entre outros produtos artesanais. Para fortalecer os seus produtos, foi desenvolvido a marca e toda identidade do atêlier.', 'Identidade e comunicação visual criada para o supermercado localizado em Mata de São João-BA'),
	(9, 'Viverarte', 'thumbs_viverarte.jpg', 9, 5, 1, 'Um atêlier de arte familiar onde são desenvolvidos projetos artesanais para comercialização nacional e internacional. As artistas trabalham com biscuit, velas artesanais, sabonetes entre entre outros produtos artesanais. Para fortalecer os seus produtos, foi desenvolvido a marca e toda identidade do atêlier.', 'Criação da marca e papelaria institucional para o atêlier de artes em Salvador-BA'),
	(10, 'Vovó Lurdinha', 'thumbs_vovolurdinha.jpg', 10, 5, 0, 'Tivemos uma grande experiência gastronômica criando a marca para ateliêr de culinária artesanal Vovó Lurdinha em Curitiba-PR. Utilizamos tipografias serifadas e um selo retrô, com intuito de representar um estilo clássico já que as receitas são passadas de geração em geração.', NULL),
	(11, 'Physioserv', 'thumbs_physioserv.jpg', 11, -1, 0, 'Criação do material gráfico para a divulgação do curso de Pilates oferecido pela Physioserv em Salvador-BA e Recife-PE.', 'Criação do material gráfico para a divulgação do curso de Pilates oferecido pela Physioserv em Salvador-BA e Recife-PE.'),
	(12, 'Cartilha Ferropronto', 'thumbs_ferroprontoCartilha.jpg', 12, 5, 0, 'Criação do mascote e da cartilha para a empresa de corte e dobra de aço em Lauro de Freitas-BA. O Material foi desenvolvido para explicar aos funcionários os processos de RH, segurança e comportamento dentro da empresa de forma mais simples e divertida.', NULL),
	(13, 'Folder Ferropronto', 'thumbs_ferroprontoFolder.jpg', 13, 5, 0, 'Folder institucional criado para apresentar a empresa, os serviços oferecidos e os trabalhos já desenvolvidos.', NULL);

INSERT INTO `project_images` (`id`, `image`, `project`) VALUES
	(1, 'aimeusanto__0000_marca01.jpg', 1),
	(2, 'aimeusanto__0001_marca02.jpg', 1),
	(3, 'aimeusanto__0002_cores.jpg', 1),
	(4, 'alineForjaz_0000_marca01.jpg', 2),
	(5, 'alineForjaz_0001_marca02.jpg', 2),
	(6, 'alineForjaz_0002_textura.jpg', 2),
	(7, 'alineForjaz_0003_cores.jpg', 2),
	(8, 'alineForjaz_0004_cartao_visita.jpg', 2),
	(9, 'delineer__0000_estudos.jpg', 3),
	(10, 'delineer__0001_const_tipografia.jpg', 3),
	(11, 'delineer__0002_tipografia.jpg', 3),
	(12, 'delineer__0003_icone.jpg', 3),
	(13, 'delineer__0004_estudos.jpg', 3),
	(14, 'delineer__0005_icone.jpg', 3),
	(15, 'delineer__0006_malha_juncao.jpg', 3),
	(16, 'delineer__0007_marca.jpg', 3),
	(17, 'delineer__0008_marca2.jpg', 3),
	(18, 'delineer__0009_cartao_visita.jpg', 3),
	(19, 'delineer__0010_textura.jpg', 3),
	(20, 'delineer__0011_uniforme.jpg', 3),
	(21, 'elemento__0000_marca01.jpg', 4),
	(22, 'elemento__0001_marca02.jpg', 4),
	(23, 'elemento__0002_cores.jpg', 4),
	(24, 'elemento__0003_textura.jpg', 4),
	(25, 'elemento__0004_almofada01.jpg', 4),
	(26, 'elemento__0005_almofada02.jpg', 4),
	(27, 'elemento__0006_almofada03.jpg', 4),
	(28, 'fuzzy_0000_estudo.jpg', 5),
	(29, 'fuzzy_0001_marca01.jpg', 5),
	(30, 'fuzzy_0002_marca02.jpg', 5),
	(31, 'fuzzy_0003_cores.jpg', 5),
	(32, 'fuzzy_0004_cartao_visita.jpg', 5),
	(33, 'graphshop__0000_estudo.jpg', 6),
	(34, 'graphshop__0001_marca01.jpg', 6),
	(35, 'graphshop__0002_marca02.jpg', 6),
	(36, 'graphshop__0003_cartao_visita.jpg', 6),
	(37, 'raquel__0000_apresentacao.jpg', 7),
	(38, 'raquel__0001_marca01.jpg', 7),
	(39, 'raquel__0002_marca02.jpg', 7),
	(40, 'raquel__0003_marca03.jpg', 7),
	(41, 'raquel__0004_marca04.jpg', 7),
	(42, 'raquel__0005_cores.jpg', 7),
	(43, 'raquel__0006_cartao_visita.jpg', 7),
	(44, 'supernorte__0000_marca01.jpg', 8),
	(45, 'supernorte__0001_marca02.jpg', 8),
	(46, 'supernorte__0002_malha.jpg', 8),
	(47, 'supernorte__0003_cartao_visita.jpg', 8),
	(48, 'supernorte__0004_sacola.jpg', 8),
	(49, 'supernorte__0005_painel.jpg', 8),
	(50, 'viverarte__0000_marca01.jpg', 9),
	(51, 'viverarte__0001_marca02.jpg', 9),
	(52, 'viverarte__0002_simbolo.jpg', 9),
	(53, 'viverarte__0003_texturas.jpg', 9),
	(54, 'viverarte__0004_cartao_visita.jpg', 9),
	(55, 'vovolurdinha__0000_marca.jpg', 10),
	(56, 'vovolurdinha__0001_cartao_visita.jpg', 10),
	(57, 'vovolurdinha__0002_flyer.jpg', 10),
	(58, 'vovolurdinha__0003_tag.jpg', 10),
	(59, '01_outdoor.jpg', 11),
	(60, '02_folheto.jpg', 11),
	(61, '03_folheto2.jpg', 11),
	(62, 'ferropronto__0000_cartilha01.jpg', 12),
	(63, 'ferropronto__0001_Cartilha02.jpg', 12),
	(64, 'ferropronto__0002_Cartilha03.jpg', 12),
	(65, 'ferropronto__0003_Folder01.jpg', 13),
	(66, 'ferropronto__0004_Folder02.jpg', 13),
	(67, 'ferropronto__0005_Folder03.jpg', 13);

# --- !Downs
 
DROP TABLE 'portfolio_type';
DROP TABLE 'clients';
DROP TABLE 'project';
DROP TABLE 'project_images';
