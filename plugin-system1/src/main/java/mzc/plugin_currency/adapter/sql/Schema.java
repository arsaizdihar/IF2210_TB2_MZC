package mzc.plugin_currency.adapter.sql;

import lombok.Getter;

public class Schema {
    @Getter
    //language=SQL
    private static final String value = """
            create table if not exists currency
            (
                id bigint auto_increment primary key,
                name varchar(255) null,
                symbol varchar(255) null,
                conversion     decimal(38, 2) null,
                isDefaultCurrency bit null
            );
            """;
}
