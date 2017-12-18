create database nppEstoque;

use nppEstoque;

create table produto
(
	id_produto int not null auto_increment primary key,
    codigo_radar varchar(100) not null,
    nome_produto varchar(100) not null,
    descricao_produto varchar(1000) not null,
    local_estoque varchar(100),
    situacao_deletado varchar(2)
);

create table inventario
(	
	id_inventario int not null auto_increment primary key,
    data_inventario varchar(20) not null,
    tipo_inventario varchar(2) not null,
    situacao_deletado varchar(2)
);

create table inventario_itens
(
	id_produto int not null,
    id_inventario int not null,
	lote varchar(100),
    quantidade Double,
    constraint fk_inventario_produto foreign key (id_produto) references produto (id_produto),
    constraint fk_inventario_item_inventario foreign key (id_inventario) references inventario (id_inventario)
);

create table produto_estoque
(
	id_produto int not null,
    quantidade_estoque Double not null,
    data_ultimo_inventario varchar(20),
    constraint fk_produto_estoque foreign key (id_produto) references produto (id_produto)
);


-- insert into produto(codigo_radar, nome_produto, descricao_produto, local_estoque, situacao_deletado) values ('123', 'T123', 'PRODUTO ETIQUETA TESTE', 'A1','N');

select *from configuracao_database;

select *from produto;

select *from inventario;

select *from inventario_itens;

select *from produto_estoque;

-- Listagem de Produtos por numero da Entrada / Saida
select p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque, SUM(ii.quantidade) as' quantidade' from inventario_itens ii
inner join inventario i on (i.id_inventario = ii.id_inventario)
inner join produto p on (p.id_produto = ii.id_produto)
where i.id_inventario = '1'
group by (p.nome_produto)
order by (p.codigo_radar);

-- Listagem de Produtos por Período -AGRUPADO
select p.id_produto, p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque, SUM(ii.quantidade) as' quantidade', date_format(str_to_date(i.data_inventario, '%d/%m/%Y'),  '%d/%m/%Y')  as 'data' from inventario_itens ii
inner join inventario i on (i.id_inventario = ii.id_inventario)
inner join produto p on (p.id_produto = ii.id_produto)
WHERE i.data_inventario BETWEEN ('25/10/2017') AND ('28/10/2017')
and i.tipo_inventario = 'E'
group by (p.id_produto)
order by (p.codigo_radar);

-- Listagem de Produtos por Período -Entrada - GERAL
select p.id_produto, p.codigo_radar, p.nome_produto, p.descricao_produto, ii.lote, p.local_estoque, ii.quantidade as' quantidade', date_format(str_to_date(i.data_inventario, '%d/%m/%Y'),  '%d/%m/%Y') as 'data' from inventario_itens ii
inner join inventario i on (i.id_inventario = ii.id_inventario)
inner join produto p on (p.id_produto = ii.id_produto)
WHERE i.data_inventario BETWEEN ('25/10/2017') AND ('28/10/2017')
and i.tipo_inventario = 'E'
order by (p.codigo_radar);


-- Listagem de Produtos por Período -Saida
select p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque, SUM(ii.quantidade) as' quantidade', str_to_date(i.data_inventario, '%d/%m/%Y') as 'data' from inventario_itens ii
inner join inventario i on (i.id_inventario = ii.id_inventario)
inner join produto p on (p.id_produto = ii.id_produto)
WHERE i.data_inventario BETWEEN ('28/10/2017') AND ('28/10/2017')
and i.tipo_inventario = 'S'
group by (p.nome_produto)
order by (p.codigo_radar);

-- Listagem de Produtos por Quantidade em Estoque
select  p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque, pe.quantidade_estoque from produto_estoque pe
inner join produto p on (p.id_produto = pe.id_produto);

GRANT ALL ON nppEstoque.* TO 'root'@'192.168.0.113';