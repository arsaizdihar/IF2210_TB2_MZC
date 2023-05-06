package mzc.app.adapter.sql;

import lombok.Getter;
import lombok.Setter;

public class Schema {
    @Getter
    @Setter
    private static String value =
            """
                    create table if not exists customer
                    (
                        id            bigint auto_increment
                            primary key,
                        isDeactivated bit          null,
                        name          varchar(255) null,
                        phone         varchar(255) null,
                        points        int          null,
                        type          tinyint      null,
                        check (`type` between 0 and 2)
                    );

                    create table if not exists bill
                    (
                        id         bigint auto_increment
                            primary key,
                        customerId bigint null,
                        foreign key (customerId) references customer (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
                    );

                    create table if not exists fixedbill
                    (
                        id         bigint auto_increment
                            primary key,
                        customerId bigint null,
                        foreign key (customerId) references customer (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
                    );

                    create table if not exists product
                    (
                        id        bigint auto_increment
                            primary key,
                        buyPrice  decimal(38, 2) null,
                        category  varchar(255)   null,
                        deleted   bit            null,
                        imagePath varchar(255)   null,
                        name      varchar(255)   null,
                        price     decimal(38, 2) null,
                        stock     int            null
                    );

                    create table if not exists productbill
                    (
                        id        bigint auto_increment
                            primary key,
                        amount    int    null,
                        billId    bigint null,
                        productId bigint null,
                        foreign key (billId) references bill (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE,
                        foreign key (productId) references product (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
                    );

                    create table if not exists producthistory
                    (
                        id       bigint auto_increment
                            primary key,
                        amount   int            null,
                        billId   bigint         null,
                        buyPrice decimal(38, 2) null,
                        category varchar(255)   null,
                        image    varchar(255)   null,
                        name     varchar(255)   null,
                        price    decimal(38, 2) null,
                        foreign key (billId) references fixedbill (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
                    );
                    """;
}
