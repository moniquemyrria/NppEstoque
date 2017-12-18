create table produto
(   id_produto INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    codigo_radar varchar(100) not null,
    nome_produto varchar(100) not null,
    descricao_produto varchar(1000),
    local_estoque varchar(100),
    situacao_deletado varchar(2)
);

create table inventario
(	
    id_inventario INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
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