package mzc.plugin_system2.adapter.sql;

import lombok.Getter;

public class Schema {

    @Getter
    private static final String value = """
            create table if not exists charge (
                id bigint auto_increment primary key,
                value     decimal(38, 2) null,
                key varchar(255) null,
                name varchar(255) null,
                type          tinyint      null,
                check (`type` between 0 and 1)
            );
            """;
}
